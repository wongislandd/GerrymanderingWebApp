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
import javax.persistence.Query;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
        EntityManager em = EntityManagerSingleton.getInstance().getEntityManager();
        em.getTransaction().begin();
        JSONObject jo = readFile("/json/NC/districtingsExample.json");
        JSONArray districtings = jo.getJSONArray("districtings");
        Query query = em.createQuery("SELECT p FROM Precinct p");
        ArrayList<Precinct> allPrecincts = new ArrayList<Precinct>(query.getResultList());
        HashMap<Integer, Precinct> precinctHash = new HashMap<>();
        /* Initialize the precinct hash map, containing all precincts before the loop*/
        for (int i=0;i<allPrecincts.size();i++) {
            precinctHash.put(allPrecincts.get(i).getId(), allPrecincts.get(i));
        }

        // i think the commiting might take a long time because
        // i think something to do with the Cascading makes it so
        // every precinct is also being updated somehow EVERYTIME we update a district
        // which is a lot of times

        /* For each districting */
        for (int i = 0; i < 50; i++) {
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
            System.out.println("Created Districting " +(i+1) + " in: " + (endTime - startTime) + "ms");
        }
        final long startTime = System.currentTimeMillis();
        em.getTransaction().commit();
        final long endTime = System.currentTimeMillis();
        System.out.println("Committed in : " + (endTime - startTime) + "ms");
        // IF U WANNA LOOK AND SEE HOW U CAN SPEED THIS UP TOO IDK
        // true lets see what it does
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

    public static ArrayList<Precinct> getPrecinctsFromKeys(JSONArray precinctKeys, HashMap<Integer, Precinct> precinctHash) {
        ArrayList<Precinct> results = new ArrayList<>();
        for (int i = 0; i < precinctKeys.length(); i++) {
            results.add(precinctHash.get(precinctKeys.getInt(i)));
        }
        return results;
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
