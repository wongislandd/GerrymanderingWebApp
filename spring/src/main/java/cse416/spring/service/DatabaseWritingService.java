package cse416.spring.service;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import cse416.spring.enums.MinorityPopulation;
import cse416.spring.helperclasses.FeatureCollectionJSON;
import cse416.spring.helperclasses.builders.ConcaveHullBuilder;
import cse416.spring.helperclasses.EntityManagerSingleton;
import cse416.spring.helperclasses.SingleFeatureGeoJson;
import cse416.spring.models.county.County;
import cse416.spring.models.district.Compactness;
import cse416.spring.models.district.District;
import cse416.spring.models.district.DistrictMeasures;
import cse416.spring.models.district.MajorityMinorityInfo;
import cse416.spring.models.districting.Districting;
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
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DatabaseWritingService {

    public static JSONObject readFile(String filePath) throws IOException {
        File file = ResourceUtils.getFile("src/main/resources/static/" + filePath);
        String content = new String(Files.readAllBytes(file.toPath()));
        return new JSONObject(content);
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
            Precinct p = new Precinct(id, precinctName, feature, demographics);
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
        Iterator<String> keys = jo.keys();
        /* For each county */
        while (keys.hasNext()) {
            // Key is the county ID
            String key = keys.next();
            JSONObject county = jo.getJSONObject(key);
            String name = county.getString("name");
            JSONArray precinctKeys = county.getJSONArray("precincts");
            ArrayList<Precinct> precincts = getPrecinctObjectsFromKeys(precinctKeys, em);
            Geometry hull = new ConcaveHullBuilder(precincts).getConcaveGeometryOfPrecincts();
            County c = new County(Integer.parseInt(key), name, precincts, hull);
            em.persist(c);
        }
        /* Commit and close */
        em.getTransaction().commit();
    }

    public static void persistDistrictings() throws IOException {
        JSONObject jo = readFile("/json/NC/districtingsExample.json");
        JSONArray districtings = jo.getJSONArray("districtings");
        /* Create threads to do work */
        ArrayList<DatabaseWriterThread> threads = new ArrayList<>();
        AtomicBoolean availableRef = new AtomicBoolean(true);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("orioles_db");
        EntityManager em = emf.createEntityManager();
        HashMap<Integer, Precinct> precinctHash = getPrecinctHash(em);

        int numThreads = 4;
        int workForEachThread = 50;
        for (int i=1; i<numThreads+1;i++) {
            DatabaseWriterThread newThread = new DatabaseWriterThread("T" +i, precinctHash, districtings, (i-1)*workForEachThread, workForEachThread*i, availableRef);
            threads.add(newThread);
        }
        /* Start Multithreading */
        for (int i=0;i<threads.size();i++) {
            threads.get(i).start();
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

    public static ArrayList<Precinct> getPrecinctObjectsFromKeys(JSONArray precinctKeys, EntityManager em) {
        ArrayList<Integer> queryKeys = new ArrayList<>();
        /* Access the pre-existing precinct objects and associate them */
        for (int i = 0; i < precinctKeys.length(); i++) {
            queryKeys.add(precinctKeys.getInt(i));
        }
        Query query = em.createQuery("SELECT p FROM Precinct p");
        ArrayList<Precinct> resultList = new ArrayList<Precinct>(query.getResultList());
        return resultList;
    }

}
