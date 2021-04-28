package cse416.spring.service.database;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.EntityManagerSingleton;
import cse416.spring.helperclasses.MGGGParams;
import cse416.spring.models.county.County;
import cse416.spring.models.districting.Districting;
import cse416.spring.models.job.Job;
import cse416.spring.models.job.JobSummary;
import cse416.spring.models.precinct.Demographics;
import cse416.spring.models.precinct.Precinct;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.ResourceUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A class that provides methods for persisting precincts, counties and
 * districtings into the database.
 */
public class DatabaseWritingService {
    private static JSONObject readFile(String filePath) throws IOException {
        File file = ResourceUtils.getFile("src/main/resources/static/" + filePath);
        String content = new String(Files.readAllBytes(file.toPath()));
        return new JSONObject(content);
    }

    private static String[] getFilesInFolder(String directoryPath) throws IOException {
        File dir = ResourceUtils.getFile("src/main/resources/static/" + directoryPath);
        return dir.list();
    }

    private static Precinct buildPrecinctFromJSON(StateName state, JSONObject feature) {
        JSONObject properties = feature.getJSONObject("properties");

        int id = properties.getInt("id");
        String precinctName = properties.getString("name");

        int asian = properties.getInt("asian");
        int black = properties.getInt("black");
        int natives = properties.getInt("native_american");
        int pacific = properties.getInt("pacific_islander");
        int white = properties.getInt("white");
        int hispanic = properties.getInt("hispanic");
        int otherRace = properties.getInt("other");

        int TP = properties.getInt("population");
        int VAP = -1;
        int CVAP = -1;

        Demographics demographics = new Demographics(asian, black, natives,
                pacific, white, hispanic, otherRace, TP, VAP, CVAP);

        return new Precinct(state, id, precinctName, feature.toString(), demographics);
    }

    private static ArrayList<Precinct> getPrecinctsFromKeys(JSONArray precinctKeys,
                                                            HashMap<Integer, Precinct> allPrecincts) {
        ArrayList<Precinct> results = new ArrayList<>();
        for (int i = 0; i < precinctKeys.length(); i++) {
            results.add(allPrecincts.get(precinctKeys.getInt(i)));
        }

        return results;
    }

    private static HashMap<Integer, Precinct> getAllPrecincts(EntityManager em) {
        Query query = em.createQuery("SELECT p FROM Precinct p");
        ArrayList<Precinct> allPrecincts = new ArrayList<Precinct>(query.getResultList());

        // Convert the allPrecincts list into a hashmap of (id, precinct)
        HashMap<Integer, Precinct> precinctHash = new HashMap<>();

        for (Precinct precinct : allPrecincts) {
            precinctHash.put(precinct.getId(), precinct);
        }

        return precinctHash;
    }

    public static void persistPrecincts() throws IOException {
        // Initialize entity manager
        EntityManager em = EntityManagerSingleton.getInstance().getEntityManager();
        em.getTransaction().begin();

        /* Customization */
        String precinctsFilePath = "/json/NC/precincts_output.json";
        StateName stateName = StateName.NORTH_CAROLINA;

        JSONObject jo = readFile(precinctsFilePath);
        JSONArray features = jo.getJSONArray("features");

        for (int i = 0; i < features.length(); i++) {
            JSONObject feature = features.getJSONObject(i);
            Precinct p = buildPrecinctFromJSON(stateName, feature);
            System.out.println("Persisting Precinct " + i);
            em.persist(p);
        }

        System.out.println("Committing precincts.");
        em.getTransaction().commit();
    }

    public static void persistCounties() throws IOException {
        // Get entity manager
        EntityManager em = EntityManagerSingleton.getInstance().getEntityManager();
        em.getTransaction().begin();

        /* Customization */
        String countiesFilePath = "/json/NC/CountiesPrecinctsMapping.json";
        StateName stateName = StateName.NORTH_CAROLINA;

        JSONObject jo = readFile(countiesFilePath);
        HashMap<Integer, Precinct> allPrecincts = getAllPrecincts(em);
        Iterator<String> keys = jo.keys();

        /* For each county */
        int counter = 0;
        while (keys.hasNext()) {
            // Key is the county ID
            String countyID = keys.next();
            JSONObject county = jo.getJSONObject(countyID);

            // Build the county object
            String name = county.getString("name");
            JSONArray precinctKeys = county.getJSONArray("precincts");
            ArrayList<Precinct> precincts = getPrecinctsFromKeys(precinctKeys, allPrecincts);

            County c = new County(stateName, Integer.parseInt(countyID), name, precincts);
            System.out.println("PERSISTING COUNTY " + counter++);
            em.persist(c);
        }

        em.getTransaction().commit();
    }

    private static Job createJob(StateName state, int jobId, JobSummary js, EntityManager em) {
        Query query = em.createQuery("SELECT d FROM Districting d WHERE d.jobID = :jobId");
        query.setParameter("jobId", jobId);

        ArrayList<Districting> districtingsInJob = new ArrayList<Districting>(query.getResultList());
        JSONArray districtingKeysArr = new JSONArray();
        for (Districting districting : districtingsInJob) {
            districtingKeysArr.put(districting.getId());
        }

        js.setSize(districtingsInJob.size());
        return new Job(state, new JSONObject().put("districtings", districtingKeysArr).toString(), js);
    }

    private static boolean areThreadsAlive(ArrayList<DistrictingWriterThread> threads) {
        for (DistrictingWriterThread thread : threads) {
            if (thread.isAlive()) {
                return true;
            }
        }

        return false;
    }

    private static void persistJob(StateName state, int jobId, JobSummary js,
                                   EntityManager em) {

        Job newJob = createJob(state, jobId, js, em);
        em.getTransaction().begin();
        em.persist(newJob);
        em.getTransaction().commit();
    }

    public static void persistDistrictings() throws IOException {
        final long startTime = System.currentTimeMillis();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("orioles_db");

        // Get all precincts
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        HashMap<Integer, Precinct> precinctHash = getAllPrecincts(em);
        em.getTransaction().commit();
        em.close();

        // Adjust job parameters here
        StateName state = StateName.NORTH_CAROLINA;
        int jobId = 1;
        MGGGParams params = new MGGGParams(10000, .1);

        // Size will be set adaptively later
        JobSummary js = new JobSummary("North Carolina 10% max population difference.", params, -1);
        String jobFolderPath = "/json/NC/districtings";
        // ************************************ /

        // Create entity managers for the threads
        int numThreads = 5;
        int workForEachThread = 10;
        String[] files = getFilesInFolder(jobFolderPath);

        ArrayList<EntityManager> ems = new ArrayList<>();
        for (int j = 0; j < numThreads; j++) {
            ems.add(emf.createEntityManager());
        }

        // For every file in the folder . . .
        for (int i = 0; i < 20; i++) {
            // Read districtings from the file
            final long fileStartTime = System.currentTimeMillis();
            System.out.println("Starting file " + files[i]);
            JSONObject jo = readFile("/json/NC/districtings/" + files[i]);
            JSONArray districtings = jo.getJSONArray("districtings");

            ArrayList<DistrictingWriterThread> threads = new ArrayList<>();
            AtomicBoolean availableRef = new AtomicBoolean(true);

            // Create threads
            for (int j = 1; j < numThreads + 1; j++) {
                DistrictingWriterThread newThread = new DistrictingWriterThread(state, jobId,
                        "T" + j, ems.get(j - 1), precinctHash, districtings, (j - 1) * workForEachThread,
                        workForEachThread * j, availableRef);
                threads.add(newThread);
            }

            // Start Multithreading
            for (DistrictingWriterThread thread : threads) {
                thread.start();
            }
            while (areThreadsAlive(threads)) {
            }

            final long fileEndTime = System.currentTimeMillis();
            System.out.println("[MAIN] Persisted " + files[i] + " in " + (fileEndTime - fileStartTime) + "ms");
        }

        /* Close entity managers */
        for (EntityManager entityManager : ems) {
            entityManager.close();
        }

        // Persist the job
        EntityManager em2 = emf.createEntityManager();
        persistJob(state, jobId, js, em2);
        em2.close();
        emf.close();

        final long endTime = System.currentTimeMillis();
        System.out.println("[MAIN] Persisted a job (ID=" + jobId + ") of " + js.getSize() +
                " districtings in: " + (endTime - startTime) + "ms");
    }
}
