package cse416.spring.service;

import cse416.spring.helperclasses.EntityManagerSingleton;
import cse416.spring.models.county.County;
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

public class DatabaseWritingService {

    private static JSONObject readFile(String filePath) throws IOException {
        File file = ResourceUtils.getFile("src/main/resources/static/" + filePath);
        String content = new String(Files.readAllBytes(file.toPath()));
        return new JSONObject(content);
    }

    private static String[] getFilesInFolder(String directoryPath) throws IOException {
        File file = ResourceUtils.getFile("src/main/resources/static/" + directoryPath);
        return file.list();
    }

    private static Precinct buildPrecinctFromJSON(JSONObject feature) {
        JSONObject properties = feature.getJSONObject("properties");

        String precinctName = properties.getString("PREC_NAME");
        int democrats = properties.getInt("DEM");
        int republicans = properties.getInt("REP");
        int otherParty = properties.getInt("OTHER");
        int asian = properties.getInt("A");
        int black = properties.getInt("B");
        int natives = properties.getInt("I");
        int pacific = properties.getInt("P");
        int whiteHispanic = properties.getInt("WHL");
        int whiteNonHispanic = properties.getInt("WNL");
        int otherRace = properties.getInt("O");

        int TP = -1;
        int VAP = democrats + republicans + otherParty;
        int CVAP = -1;
        int id = properties.getInt("id");

        Demographics demographics = new Demographics(democrats, republicans, otherParty, asian, black, natives,
                pacific, whiteHispanic, whiteNonHispanic, otherRace, TP, VAP, CVAP);

        return new Precinct(id, precinctName, feature.toString(), demographics);
    }

    private static ArrayList<Precinct> getPrecinctsFromKeys(JSONArray precinctKeys, HashMap<Integer, Precinct> precinctHash) {
        ArrayList<Precinct> results = new ArrayList<>();

        for (int i = 0; i < precinctKeys.length(); i++) {
            results.add(precinctHash.get(precinctKeys.getInt(i)));
        }

        return results;
    }

    private static HashMap<Integer, Precinct> getAllPrecincts(EntityManager em) {
        Query query = em.createQuery("SELECT p FROM Precinct p");
        ArrayList<Precinct> allPrecincts = new ArrayList<Precinct>(query.getResultList());
        HashMap<Integer, Precinct> precinctHash = new HashMap<>();

        /* Initialize the precinct hash map, containing all precincts before the loop*/
        for (Precinct precinct : allPrecincts) {
            precinctHash.put(precinct.getId(), precinct);
        }

        return precinctHash;
    }

    public static void persistPrecincts() throws IOException {
        // Initialize entity manager
        EntityManager em = EntityManagerSingleton.getInstance().getEntityManager();
        em.getTransaction().begin();

        String precinctsFilePath = "/json/NC/PrecinctGeoDataSimplified.json";
        JSONObject jo = readFile(precinctsFilePath);
        JSONArray features = jo.getJSONArray("features");

        for (int i = 0; i < features.length(); i++) {
            JSONObject feature = features.getJSONObject(i);
            Precinct p = buildPrecinctFromJSON(feature);
            em.persist(p);
        }

        em.getTransaction().commit();
    }


    public static void persistCounties() throws IOException {
        // Get entity manager
        EntityManager em = EntityManagerSingleton.getInstance().getEntityManager();
        em.getTransaction().begin();

        String countiesFilePath = "/json/NC/CountiesPrecinctsMapping.json";
        JSONObject jo = readFile(countiesFilePath);
        HashMap<Integer, Precinct> allPrecincts = getAllPrecincts(em);
        Iterator<String> keys = jo.keys();

        // For each county
        while (keys.hasNext()) {
            // Key is the county ID
            String key = keys.next();
            JSONObject county = jo.getJSONObject(key);

            // Build the county object
            String name = county.getString("name");
            JSONArray precinctKeys = county.getJSONArray("precincts");
            ArrayList<Precinct> precincts = getPrecinctsFromKeys(precinctKeys, allPrecincts);

            // Get the precinct keys
            County c = new County(Integer.parseInt(key), name, precincts);
            em.persist(c);
        }

        em.getTransaction().commit();
    }

    public static boolean areThreadsAlive(ArrayList<DistrictingWriterThread> threads) {
        for (DistrictingWriterThread thread : threads) {
            if (thread.isAlive()) {
                return true;
            }
        }

        return false;
    }

    public static void persistDistrictings() throws IOException {
        // Create entity manager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("orioles_db");
        EntityManager em = emf.createEntityManager();

        HashMap<Integer, Precinct> allPrecincts = getAllPrecincts(em);
        String districtingsPath = "/json/NC/districtings";
        String[] files = getFilesInFolder(districtingsPath);

        // For every file of districtings...
        for (String file : files) {
            final long startTime = System.currentTimeMillis();
            System.out.println("Starting file " + file);

            JSONObject jo = readFile(districtingsPath + file);
            JSONArray districtings = jo.getJSONArray("districtings");

            int numThreads = 5;
            int workForEachThread = 10;
            ArrayList<DistrictingWriterThread> threads = new ArrayList<>();
            AtomicBoolean availableRef = new AtomicBoolean(true);

            for (int j = 1; j < numThreads + 1; j++) {
                DistrictingWriterThread newThread = new DistrictingWriterThread("T" + j, allPrecincts, districtings, (j - 1) * workForEachThread, workForEachThread * j, availableRef);
                threads.add(newThread);
            }

            // Start Multithreading
            for (DistrictingWriterThread thread : threads) {
                thread.start();
            }

            while (areThreadsAlive(threads));

            final long endTime = System.currentTimeMillis();
            System.out.println("[MAIN] Persisted /" + file + " in " + (endTime - startTime) + "ms");
        }

        em.close();
        emf.close();
    }
}
