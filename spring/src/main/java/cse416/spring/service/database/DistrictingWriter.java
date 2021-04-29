package cse416.spring.service.database;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.MGGGParams;
import cse416.spring.models.district.District;
import cse416.spring.models.district.DistrictReference;
import cse416.spring.models.districting.EnactedDistricting;
import cse416.spring.models.job.JobSummary;
import cse416.spring.models.precinct.Precinct;
import cse416.spring.service.DistrictingServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

import static cse416.spring.helperclasses.FileReader.getFilesInFolder;
import static cse416.spring.helperclasses.FileReader.readJsonFile;
import static cse416.spring.service.database.PrecinctWriter.getAllPrecincts;

/**
 * A class that provides methods for persisting precincts, counties and
 * districtings into the database.
 */

@Service
public class DistrictingWriter {

    // TODO Turn this into an SQL query within PrecinctService
    public static ArrayList<Precinct> getPrecinctsFromKeys(JSONArray precinctKeys,
                                                           HashMap<Integer, Precinct> allPrecincts) {
        ArrayList<Precinct> results = new ArrayList<>();
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
        ArrayList<District> districtsInDistricting = new ArrayList<>();


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("orioles_db");
        HashMap<Integer, Precinct> precinctHash = getAllPrecincts();

        /* For each district in the districting */
        while (keys.hasNext()) {
            String districtID = keys.next();
            int districtIndex = Integer.parseInt(districtID);
            JSONArray precinctKeysInDistrict = districting.getJSONArray(districtID);
            ArrayList<Precinct> precincts = getPrecinctsFromKeys(precinctKeysInDistrict, precinctHash);
            // TODO: Change the null to the enacted districting
            DistrictReference districtReference = new DistrictReference(enactedFilePath, 0, districtIndex);
            District d = new District(precincts, stateName, null, districtReference);
            districtsInDistricting.add(d);
        }
        EntityManager em = emf.createEntityManager();
        EnactedDistricting enactedDistricting = new EnactedDistricting(stateName, districtsInDistricting);

        // Commit
        em.getTransaction().begin();
        em.persist(enactedDistricting);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }


    public static void persistDistrictings() throws IOException {
        final long startTime = System.currentTimeMillis();
        HashMap<Integer, Precinct> precinctHash = getAllPrecincts();

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

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("orioles_db");
        ArrayList<EntityManager> ems = new ArrayList<>();
        for (int j = 0; j < numThreads; j++) {
            ems.add(emf.createEntityManager());
        }

        EntityManager em = emf.createEntityManager();
        EnactedDistricting enactedDistricting = new DistrictingServiceImpl(em).findEnactedByState(state);
        em.close();

        // For every file in the folder . . .
        for (int i = 0; i < 1; i++) {
            // Read districtings from the file
            final long fileStartTime = System.currentTimeMillis();
            System.out.println("Starting file " + files[i]);
            JSONObject jo = readJsonFile("/NC/districtings/" + files[i]);
            JSONArray districtings = jo.getJSONArray("districtings");

            ArrayList<DistrictingWriterThread> threads = new ArrayList<>();
            AtomicBoolean availableRef = new AtomicBoolean(true);

            // Create threads
            for (int j = 0; j < numThreads; j++) {
                DistrictingWriterThread newThread = new DistrictingWriterThread(state, "/NC/districtings/" + files[i], jobId,
                        "T" + j, ems.get(j), precinctHash, enactedDistricting, districtings, (j) * workForEachThread,
                        workForEachThread * (j + 1), availableRef);
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
        em = emf.createEntityManager();
        JobWriter.persistJob(state, jobId, js, em);
        em.close();
        emf.close();

        final long endTime = System.currentTimeMillis();
        System.out.println("[MAIN] Persisted a job (ID=" + jobId + ") of " + js.getSize() +
                " districtings in: " + (endTime - startTime) + "ms");
    }
}
