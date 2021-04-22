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

public class DatabaseWriterThread extends Thread {
    String name;
    EntityManager em;
    JSONArray districtings;
    HashMap<Integer, Precinct> precinctHash;
    int rangeStart;
    int rangeEndExclusive;
    AtomicBoolean availableRef;

    public DatabaseWriterThread(String name, HashMap<Integer,Precinct> precinctHash, JSONArray districtings, int rangeStart, int rangeEndExclusive, AtomicBoolean availableRef) {
        this.name = name;
        /* Create the entity manager */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("orioles_db");
        this.em = emf.createEntityManager();
        /* Create the precinct hash, each thread must have their own version
        * or else Hibernate will consider the precinct objects within them to be detached*/
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
                District d = constructDistrictFromJSONArray(districtNumber, precinctKeysInDistrict);
                /* Add the district to the list to be used for calculating measures later */
                districtsInDistricting.add(d);
                /* Persist the districting, seems like the ID is set after the persist, but I feel like this can go weird with the threads */
                em.persist(d);
            }
            Districting newDistricting = new Districting(districtsInDistricting);
            em.persist(newDistricting);
            //final long endTime = System.currentTimeMillis();
            //System.out.println("[THREAD " + name + "] Created Districting " + (i + 1) + " in: " + (endTime - startTime) + "ms");
        }

        boolean first = true;
        while (true) {
            if (availableRef.compareAndSet(true, false)) {
                //System.out.println("[THREAD " + name + "] Starting commit");
                //final long startTime = System.currentTimeMillis();
                em.getTransaction().commit();
                //final long endTime = System.currentTimeMillis();
                //System.out.println("[THREAD " + name + "] Committed in: " + (endTime - startTime) + "ms");
                availableRef.set(true);
                break;
            } else {
                if (first) {
                    //System.out.println("[THREAD " + name + "] Ready and waiting to commit.");
                    first = false;
                }
            }
        }
        em.close();
    }

    public static ArrayList<Precinct> getPrecinctsFromKeys(JSONArray precinctKeys, HashMap<Integer, Precinct> precinctHash) {
        ArrayList<Precinct> results = new ArrayList<>();
        for (int i = 0; i < precinctKeys.length(); i++) {
            results.add(precinctHash.get(precinctKeys.getInt(i)));
        }
        return results;
    }

    public static int calculateSplitCounties(ArrayList<Precinct> precincts) {
        return 5;
    }

    public static int calculatePopulationEquality(Demographics d) {
        return 5;
    }

    public static int calculatePoliticalFairness(Demographics d) {
        return 5;
    }

    public static int calculateDeviationFromEnacted(Geometry hull, Demographics d) {
        return 5;
    }

    public static int calculateDeviationFromAverage(Geometry hull, Demographics d) {
        return 5;
    }

    public static Demographics compileDemographics(ArrayList<Precinct> precincts) {
        int total_democrats = 0;
        int total_republicans = 0;
        int total_otherParty = 0;
        int total_asian = 0;
        int total_black = 0;
        int total_natives = 0;
        int total_pacific = 0;
        int total_whiteHispanic = 0;
        int total_whiteNonHispanic = 0;
        int total_otherRace = 0;
        int total_TP = 0;
        int total_VAP = 0;
        int total_CVAP = 0;
        for (int i = 0; i < precincts.size(); i++) {
            Demographics currentPrecinctDemographics = precincts.get(i).getDemographics();
            total_democrats += currentPrecinctDemographics.getDemocrats();
            total_republicans += currentPrecinctDemographics.getRepublicans();
            total_otherParty += currentPrecinctDemographics.getOtherParty();
            total_asian += currentPrecinctDemographics.getAsian();
            total_black += currentPrecinctDemographics.getBlack();
            total_natives += currentPrecinctDemographics.getNatives();
            total_pacific += currentPrecinctDemographics.getPacific();
            total_whiteHispanic += currentPrecinctDemographics.getWhiteHispanic();
            total_whiteNonHispanic += currentPrecinctDemographics.getWhiteNonHispanic();
            total_otherRace += currentPrecinctDemographics.getOtherRace();
            total_TP += currentPrecinctDemographics.getTP();
            total_VAP += currentPrecinctDemographics.getVAP();
            total_CVAP += currentPrecinctDemographics.getCVAP();
        }
        return new Demographics(total_democrats, total_republicans, total_otherParty, total_asian, total_black, total_natives, total_pacific, total_whiteHispanic, total_whiteNonHispanic, total_otherRace, total_TP, total_VAP, total_CVAP);
    }

    public District constructDistrictFromJSONArray(int districtNumber, JSONArray precinctKeysInDistrict) {
        ArrayList<Precinct> precincts = getPrecinctsFromKeys(precinctKeysInDistrict, precinctHash);
        Demographics demographics = compileDemographics(precincts);
        /* Calculate some stats to be attached to the district */
        MajorityMinorityInfo minorityInfo = new MajorityMinorityInfo(
                demographics.isMajorityMinorityDistrict(MinorityPopulation.BLACK),
                demographics.isMajorityMinorityDistrict(MinorityPopulation.HISPANIC),
                demographics.isMajorityMinorityDistrict(MinorityPopulation.ASIAN),
                demographics.isMajorityMinorityDistrict(MinorityPopulation.NATIVE_AMERICAN));
        Compactness compactness = new Compactness(.5, .6, .7);

        int splitCounties = calculateSplitCounties(precincts);
        double populationEquality = calculatePopulationEquality(demographics);
        double politicalFairness = calculatePoliticalFairness(demographics);
//        double deviationFromEnacted = calculateDeviationFromEnacted(hull, demographics);
//        double deviationFromAverage = calculateDeviationFromAverage(hull, demographics);
        DistrictMeasures dm = new DistrictMeasures(populationEquality, minorityInfo, compactness, politicalFairness, splitCounties);
        /* Create the newDistrict */
        return new District(districtNumber, demographics, dm, precinctKeysInDistrict);
    }



}
