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

public class DatabaseWriterThread extends Thread{
    String name;
    EntityManager em;
    JSONArray districtings;
    HashMap<Integer, Precinct> precinctHash;
    int rangeStart;
    int rangeEndExclusive;

    public DatabaseWriterThread(String name, JSONArray districtings, int rangeStart, int rangeEndExclusive) {
        this.name = name;
        /* Create the entity manager */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "orioles_db" );
        this.em = emf.createEntityManager();
        /* Create the precinct hash */
        System.out.println("Creating Precinct Hash");
        Query query = em.createQuery("SELECT p FROM Precinct p");
        ArrayList<Precinct> allPrecincts = new ArrayList<Precinct>(query.getResultList());
        precinctHash = new HashMap<>();
        /* Initialize the precinct hash map, containing all precincts before the loop*/
        for (int i=0;i<allPrecincts.size();i++) {
            precinctHash.put(allPrecincts.get(i).getId(), allPrecincts.get(i));
        }
        this.districtings = districtings;
        this.rangeStart = rangeStart;
        this.rangeEndExclusive = rangeEndExclusive;
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
            final long startTime = System.currentTimeMillis();
            JSONObject districting = districtings.getJSONObject(i);
            Iterator<String> keys = districting.keys();
            ArrayList<District> districtsInDistricting = new ArrayList<>();
            /* For each district in the districting */
            while (keys.hasNext()) {
                String districtID = keys.next();
                JSONArray precinctKeysInDistrict = districting.getJSONArray(districtID);
                /* For each precinct ID in the district */
                ArrayList<Precinct> precinctsInDistrict = getPrecinctsFromKeys(precinctKeysInDistrict, precinctHash);
                districtsInDistricting.add(constructDistrictFromPrecincts(precinctsInDistrict));
            }
            Districting newDistricting = new Districting(districtsInDistricting);
            em.persist(newDistricting);
            final long endTime = System.currentTimeMillis();
            System.out.println("[THREAD " + name + "] Created Districting " +(i+1) + " in: " + (endTime - startTime) + "ms");
        }
        final long startTime = System.currentTimeMillis();
        em.getTransaction().commit();
        final long endTime = System.currentTimeMillis();
        System.out.println("[THREAD " + name + "] Committed in : " + (endTime - startTime) + "ms");
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

    public static District constructDistrictFromPrecincts(ArrayList<Precinct> precincts) {
        //Geometry hull = new ConcaveHullBuilder(precincts).getConcaveGeometryOfPrecincts();
        // lets try it

        Demographics demographics = compileDemographics(precincts);
        /* Calculate some stats to be attached to the district */
        MajorityMinorityInfo minorityInfo = new MajorityMinorityInfo(
                demographics.isMajorityMinorityDistrict(MinorityPopulation.BLACK),
                demographics.isMajorityMinorityDistrict(MinorityPopulation.HISPANIC),
                demographics.isMajorityMinorityDistrict(MinorityPopulation.ASIAN),
                demographics.isMajorityMinorityDistrict(MinorityPopulation.NATIVE_AMERICAN));
        Compactness compactness = new Compactness(.5,.6,.7);

        // Theres gonna be even more work once we actually put the formulas here -vv
        int splitCounties = calculateSplitCounties(precincts);
        double populationEquality = calculatePopulationEquality(demographics);
        double politicalFairness = calculatePoliticalFairness(demographics);
//        double deviationFromEnacted = calculateDeviationFromEnacted(hull, demographics);
//        double deviationFromAverage = calculateDeviationFromAverage(hull, demographics);
        DistrictMeasures dm = new DistrictMeasures(populationEquality, minorityInfo, compactness, politicalFairness, splitCounties);
        /* Create the newDistrict */
        return new District(demographics, precincts, dm);
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
        for (int i=0;i<precincts.size();i++) {
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
        return new Demographics(total_democrats, total_republicans, total_otherParty, total_asian,total_black,total_natives,total_pacific, total_whiteHispanic, total_whiteNonHispanic, total_otherRace, total_TP, total_VAP, total_CVAP);
    }

}
