package cse416.spring.service;

import com.vividsolutions.jts.geom.Geometry;
import cse416.spring.helperclasses.builders.ConcaveHullBuilder;
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

    public static JSONObject readFile(String filePath) throws IOException {
        File file = ResourceUtils.getFile("src/main/resources/static/" + filePath);
        String content = new String(Files.readAllBytes(file.toPath()));
        return new JSONObject(content);
    }

    public static String[] getFilesInFolder(String directoryPath) throws IOException {
        File file = ResourceUtils.getFile("src/main/resources/static/" + directoryPath);
        String[] contents = file.list();
        return contents;
    }


    public static void persistPrecincts() throws IOException {
        EntityManager em = EntityManagerSingleton.getInstance().getEntityManager();
        em.getTransaction().begin();
        JSONObject jo = readFile("/json/NC/PrecinctGeoDataSimplified.json");
        JSONArray features = jo.getJSONArray("features");
        for (int i = 0; i < features.length(); i++) {
            JSONObject feature = features.getJSONObject(i);
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
            Precinct p = new Precinct(id, precinctName, feature.toString(), demographics);
            em.persist(p);
        }

        /* Commit and close */
        em.getTransaction().commit();
    }


    public static void persistCounties() throws IOException {
        /* Get entity manager */
        EntityManager em = EntityManagerSingleton.getInstance().getEntityManager();
        em.getTransaction().begin();
        JSONObject jo = readFile("/json/NC/CountiesPrecinctsMapping.json");
        HashMap<Integer, Precinct> precinctHash = getPrecinctHash(em);
        Iterator<String> keys = jo.keys();
        /* For each county */
        while (keys.hasNext()) {
            // Key is the county ID
            String key = keys.next();
            JSONObject county = jo.getJSONObject(key);
            String name = county.getString("name");
            JSONArray precinctKeys = county.getJSONArray("precincts");
            ArrayList<Precinct> precincts = getPrecinctsFromKeys(precinctKeys, precinctHash);
            /* Get the precinct keys */
            County c = new County(Integer.parseInt(key), name, precincts);
            em.persist(c);
        }
        /* Commit and close */
        em.getTransaction().commit();
    }

    public static boolean areThreadsAlive(ArrayList<DistrictingWriterThread> threads) {
        for (int i=0;i<threads.size();i++) {
            if (threads.get(i).isAlive()) {
                return true;
            }
        }
        return false;
    }

    public static void persistDistrictings() throws IOException, InterruptedException {
        /* Create threads to do work */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("orioles_db");
        EntityManager em = emf.createEntityManager();
        HashMap<Integer, Precinct> precinctHash = getPrecinctHash(em);
        String[] files = getFilesInFolder("/json/NC/districtings");
        // For every file in . . .
        for (int i=0;i<files.length;i++) {
            final long startTime = System.currentTimeMillis();
            System.out.println("Starting file " + files[i]);
            JSONObject jo = readFile("/json/NC/districtings/" + files[i]);
            JSONArray districtings = jo.getJSONArray("districtings");
            int numThreads = 5;
            int workForEachThread = 10;
            ArrayList<DistrictingWriterThread> threads = new ArrayList<>();
            AtomicBoolean availableRef = new AtomicBoolean(true);
            for (int j=1; j<numThreads+1;j++) {
                DistrictingWriterThread newThread = new DistrictingWriterThread("T" +j, precinctHash, districtings, (j-1)*workForEachThread, workForEachThread*j, availableRef);
                threads.add(newThread);
            }
            /* Start Multithreading */
            for (int j=0;j<threads.size();j++) {
                threads.get(j).start();
            }
            while(areThreadsAlive(threads)) {

            }
            final long endTime = System.currentTimeMillis();
            System.out.println("[MAIN] Persisted /" + files[i] + " in " + (endTime - startTime) + "ms");
        }
        em.close();
        emf.close();
    }

// Have a districting store it's JSON but not a reference to a collection of districts
// Districts
    public static HashMap<Integer, Precinct> getPrecinctHash(EntityManager em) {
        Query query = em.createQuery("SELECT p FROM Precinct p");
        ArrayList<Precinct> allPrecincts = new ArrayList<Precinct>(query.getResultList());
        HashMap<Integer, Precinct> precinctHash = new HashMap<>();
        /* Initialize the precinct hash map, containing all precincts before the loop*/
        for (int i = 0; i < allPrecincts.size(); i++) {
            precinctHash.put(allPrecincts.get(i).getId(), allPrecincts.get(i));
        }
        return precinctHash;
    }

    public static ArrayList<Precinct> getPrecinctsFromKeys(JSONArray precinctKeys, HashMap<Integer, Precinct> precinctHash) {
        ArrayList<Precinct> results = new ArrayList<>();
        for (int i = 0; i < precinctKeys.length(); i++) {
            results.add(precinctHash.get(precinctKeys.getInt(i)));
        }
        return results;
    }

}
