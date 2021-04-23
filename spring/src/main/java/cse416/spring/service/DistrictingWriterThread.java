package cse416.spring.service;

import com.vividsolutions.jts.geom.Geometry;
import cse416.spring.enums.MinorityPopulation;
import cse416.spring.helperclasses.EntityManagerSingleton;
import cse416.spring.models.district.Compactness;
import cse416.spring.models.district.District;
import cse416.spring.models.district.DistrictMeasures;
import cse416.spring.models.district.MajorityMinorityInfo;
import cse416.spring.models.districting.Districting;
import cse416.spring.models.precinct.Demographics;
import cse416.spring.models.precinct.Precinct;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class DistrictingWriterThread extends Thread {
    int jobID;
    String name;
    EntityManager em;
    JSONArray districtings;
    HashMap<Integer, Precinct> precinctHash;
    int rangeStart;
    int rangeEndExclusive;
    AtomicBoolean availableRef;

    public DistrictingWriterThread(int jobID, String name, EntityManager em, HashMap<Integer,Precinct> precinctHash, JSONArray districtings, int rangeStart, int rangeEndExclusive, AtomicBoolean availableRef) {
        this.jobID = jobID;
        this.name = name;
        this.em = em;
        this.precinctHash = precinctHash;
        this.districtings = districtings;
        this.rangeStart = rangeStart;
        this.rangeEndExclusive = rangeEndExclusive;
        this.availableRef = availableRef;
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {
        em.getTransaction().begin();
        /* For each districting */
        for (int i = rangeStart; i < rangeEndExclusive; i++) {
            // FeatureCollectionJSON collectionJSON = new FeatureCollectionJSON();
            //final long startTime = System.currentTimeMillis();
            JSONObject districting = districtings.getJSONObject(i);
            Iterator<String> keys = districting.keys();
            ArrayList<District> districtsInDistricting = new ArrayList<>();
            /* For each district in the districting */
            while (keys.hasNext()) {
                String districtID = keys.next();
                int districtNumber = Integer.parseInt(districtID);
                JSONArray precinctKeysInDistrict = districting.getJSONArray(districtID);
                ArrayList<Precinct> precincts = getPrecinctsFromKeys(precinctKeysInDistrict, precinctHash);
                District d = new District(districtNumber, precincts);
                /* Add the district to the list to be used for calculating measures later */
                districtsInDistricting.add(d);
                /* Persist the districting, seems like the ID is set after the persist, but I feel like this can go weird with the threads */
                em.persist(d);
            }
            Districting newDistricting = new Districting(jobID, districtsInDistricting);
            em.persist(newDistricting);
            //final long endTime = System.currentTimeMillis();
            //System.out.println("[THREAD " + name + "] Created Districting " + (i + 1) + " in: " + (endTime - startTime) + "ms");
        }

        boolean first = true;
        while (true) {
            if (availableRef.compareAndSet(true, false)) {
                System.out.println("[THREAD " + name + "] Starting commit");
                final long startTime = System.currentTimeMillis();
                em.getTransaction().commit();
                final long endTime = System.currentTimeMillis();
                System.out.println("[THREAD " + name + "] Committed in: " + (endTime - startTime) + "ms");
                availableRef.set(true);
                break;
            } else {
                if (first) {
                    //System.out.println("[THREAD " + name + "] Ready and waiting to commit.");
                    first = false;
                }
            }
        }
    }

    public static ArrayList<Precinct> getPrecinctsFromKeys(JSONArray precinctKeys, HashMap<Integer, Precinct> precinctHash) {
        ArrayList<Precinct> results = new ArrayList<>();
        for (int i = 0; i < precinctKeys.length(); i++) {
            results.add(precinctHash.get(precinctKeys.getInt(i)));
        }
        return results;
    }


}
