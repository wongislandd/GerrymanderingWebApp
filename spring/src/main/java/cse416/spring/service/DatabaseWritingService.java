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
import cse416.spring.models.precinct.Demographics;
import cse416.spring.models.precinct.Precinct;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.ResourceUtils;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;

public class DatabaseWritingService {

    public static JSONObject readFile(String filePath) throws IOException {
        File file = ResourceUtils.getFile("src/main/resources/static/" + filePath);
        String content = new String(Files.readAllBytes(file.toPath()));
        return new JSONObject(content);
    }

    public static void persistPrecinctsAndCounties() throws IOException {
        persistPrecincts();
        persistCounties();
        EntityManagerSingleton.getInstance().shutdown();
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

        JSONObject jo = readFile("/json/NC/districtingExample.json");
        JSONArray districtings = jo.getJSONArray("districtings");
        /* For each districting */
        for (int i = 0; i < districtings.length(); i++) {
            // FeatureCollectionJSON collectionJSON = new FeatureCollectionJSON();
            JSONObject districting = districtings.getJSONObject(i);
            Iterator<String> keys = districting.keys();
            ArrayList<District> districtsInDistricting = new ArrayList<>();
            /* For each district in the districting */
            while (keys.hasNext()) {
                String districtID = keys.next();
                JSONArray precinctKeysInDistrict = districting.getJSONArray(districtID);
                /* For each precinct ID in the district */
                ArrayList<Precinct> precincts = getPrecinctObjectsFromKeys(precinctKeysInDistrict, em);
                Geometry hull = new ConcaveHullBuilder(precincts).getConcaveGeometryOfPrecincts();
                // collectionJSON.put(new SingleFeatureGeoJson(hull.getCoordinates()).getJSON());
                Demographics demographics = compileDemographics(precincts);

                /* Calculate some stats to be attached to the district */
                MajorityMinorityInfo minorityInfo = new MajorityMinorityInfo(
                        demographics.isMajorityMinorityDistrict(MinorityPopulation.BLACK),
                        demographics.isMajorityMinorityDistrict(MinorityPopulation.HISPANIC),
                        demographics.isMajorityMinorityDistrict(MinorityPopulation.ASIAN),
                        demographics.isMajorityMinorityDistrict(MinorityPopulation.NATIVE_AMERICAN));
                Compactness compactness = new Compactness(.5,.6,.7);

                int splitCounties = calculateSplitCounties(precincts);
                double populationEquality = calculatePopulationEquality(demographics);
                double politicalFairness = calculatePoliticalFairness(demographics);
                double deviationFromEnacted = calculateDeviationFromEnacted(hull, demographics);
                double deviationFromAverage = calculateDeviationFromAverage(hull, demographics);
                DistrictMeasures dm = new DistrictMeasures(populationEquality, minorityInfo, compactness, politicalFairness, splitCounties, deviationFromEnacted, deviationFromAverage);
                /* Create the newDistrict */
                districtsInDistricting.add(new District(demographics, hull, precincts, dm));
            }

        }

        em.getTransaction().commit();
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




    public static ArrayList<Precinct> getPrecinctObjectsFromKeys(JSONArray precinctKeys, EntityManager em) {
        ArrayList<Precinct> precincts = new ArrayList<>();
        /* Access the pre-existing precinct objects and associate them */
        for (int i = 0; i < precinctKeys.length(); i++) {
            int precinctKey = precinctKeys.getInt(i);
            Precinct p = em.find(Precinct.class, precinctKey);
            precincts.add(p);
        }
        return precincts;
    }





}
