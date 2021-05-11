package cse416.spring.database;

import cse416.spring.enums.StateName;
import cse416.spring.models.district.Deviation;
import cse416.spring.models.job.MGGGParams;
import cse416.spring.models.district.District;
import cse416.spring.models.district.DistrictReference;
import cse416.spring.models.districting.EnactedDistricting;
import cse416.spring.models.job.Job;
import cse416.spring.models.job.JobSummary;
import cse416.spring.models.precinct.Precinct;
import cse416.spring.service.DistrictingServiceImpl;
import cse416.spring.singletons.EmfSingleton;
import cse416.spring.singletons.PrecinctHashSingleton;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static cse416.spring.helperclasses.FileReader.getFilesInFolder;
import static cse416.spring.helperclasses.FileReader.readJsonFile;

/**
 * A class that provides methods for persisting precincts, counties and
 * districtings into the database.
 */
public class DistrictingWriter {
    public static Set<Precinct> getPrecinctsFromKeys(JSONArray precinctKeys,
                                                           HashMap<Integer, Precinct> allPrecincts) {
        Set<Precinct> results = new HashSet<>();
        for (int i = 0; i < precinctKeys.length(); i++) {
            results.add(allPrecincts.get(precinctKeys.getInt(i)));
        }
        return results;
    }

    private static boolean areThreadsAlive(ArrayList<DistrictingWriterThread> threads) {
        for (DistrictingWriterThread thread : threads) {
            if (thread.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public static void persistEnactedDistrictings() throws IOException {
        StateName stateName = StateName.NORTH_CAROLINA;
        String enactedFilePath = "/NC/nc_enacted.json";
        JSONObject enactedJson = readJsonFile(enactedFilePath);
        JSONObject districting = enactedJson.getJSONArray("districtings").getJSONObject(0);
        Iterator<String> keys = districting.keys();
        Collection<District> districtsInDistricting = new HashSet<>();

        EntityManagerFactory emf = EmfSingleton.getEntityManagerFactory();
        HashMap<Integer, Precinct> precinctHash = PrecinctHashSingleton.getPrecinctHash(stateName);

        /* For each district in the districting */
        while (keys.hasNext()) {
            String districtKey = keys.next();
            JSONArray precinctKeysInDistrict = districting.getJSONArray(districtKey);
            Set<Precinct> precincts = getPrecinctsFromKeys(precinctKeysInDistrict, precinctHash);

            // TODO: Change the null to the enacted districting
            DistrictReference districtReference = new DistrictReference(stateName, enactedFilePath, 0, districtKey);
            District d = new District(precincts, stateName,districtReference);
            d.getMeasures().setDeviationFromEnacted(new Deviation(0,0));
            districtsInDistricting.add(d);
        }
        EntityManager em = emf.createEntityManager();
        EnactedDistricting enactedDistricting = new EnactedDistricting(stateName, districtsInDistricting);

        // Commit
        em.getTransaction().begin();
        em.persist(enactedDistricting);
        em.getTransaction().commit();
        em.close();
    }


    public static void persistDistrictings() throws IOException {
        final long startTime = System.currentTimeMillis();

        // Adjust job parameters here
        StateName state = StateName.NORTH_CAROLINA;
        int jobId = 1;
        MGGGParams params = new MGGGParams(10000, .1);
        int jobSize = 100000;

        // Size will be set adaptively later
        JobSummary js = new JobSummary("North Carolina 10% max population difference.", params, jobSize);
        String jobFolderPath = "/json/NC/districtings";

        Job job = new Job(state, js);
        // ************************************ /

        HashMap<Integer, Precinct> precinctHash = PrecinctHashSingleton.getPrecinctHash(state);

        // Create entity managers for the threads
        int numThreads = 5;
        int workForEachThread = 10;
        String[] files = getFilesInFolder(jobFolderPath);

        EntityManagerFactory emf = EmfSingleton.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        EnactedDistricting enactedDistricting = new DistrictingServiceImpl(em).findEnactedByState(state);

        JobWriter.persistJob(job, em);
        em.close();

        // For every file in the folder . . .
        for (int i = 0; i < 1; i++) {
            ArrayList<EntityManager> ems = new ArrayList<>();
            for (int j = 0; j < numThreads; j++) {
                ems.add(emf.createEntityManager());
            }
            // Read districtings from the file
            final long fileStartTime = System.currentTimeMillis();
            System.out.println("Starting file " + files[i]);
            JSONObject jo = readJsonFile("/NC/districtings/" + files[i]);
            JSONArray districtings = jo.getJSONArray("districtings");

            ArrayList<DistrictingWriterThread> threads = new ArrayList<>();
            AtomicBoolean availableRef = new AtomicBoolean(true);

            // Create threads
            for (int j = 0; j < numThreads; j++) {
                DistrictingWriterThread newThread = new DistrictingWriterThread(state, job, "T" + j,
                        ems.get(j), enactedDistricting, districtings, "/NC/districtings/" + files[i],
                        precinctHash, workForEachThread * (j), (j+1) * workForEachThread,
                        availableRef);
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
            /* Close entity managers */
            for (EntityManager entityManager : ems) {
                entityManager.close();
            }
        }

        final long endTime = System.currentTimeMillis();
        System.out.println("[MAIN] Persisted a job (ID=" + jobId + ") of " + js.getSize() +
                " districtings in: " + (endTime - startTime) + "ms");
    }
}
