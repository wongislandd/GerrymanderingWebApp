package cse416.spring.service;

import com.github.javafaker.Faker;
import com.vividsolutions.jts.geom.Geometry;
import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.FeatureCollectionJSON;
import cse416.spring.models.county.County;
import cse416.spring.models.job.Job;
import cse416.spring.models.precinct.Incumbent;
import cse416.spring.models.precinct.Precinct;
import org.json.JSONObject;
import org.springframework.util.ResourceUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StateService {
    public static ArrayList<Incumbent> getIncumbents(StateName state) {
        int numIncumbents = 5;
        Faker faker = new Faker();
        ArrayList<Incumbent> fakePeople = new ArrayList<Incumbent>();
        for (int i=0; i<numIncumbents;i++) {
            fakePeople.add(new Incumbent(faker.gameOfThrones().character(), faker.gameOfThrones().city()));
        }
        return fakePeople;
    }

    public static ArrayList<Job> getJobs(StateName state) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("orioles_db");
        EntityManager em = emf.createEntityManager();
        FeatureCollectionJSON geoJson = new FeatureCollectionJSON();
        Query query = em.createQuery("SELECT j FROM Job j WHERE j.state = :state");
        query.setParameter("state", state);
        ArrayList<Job> allJobs = new ArrayList<Job>(query.getResultList());
        return allJobs;
    }

    public static String getCounties(StateName state) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("orioles_db");
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT c FROM County c where c.state=:state");
        query.setParameter("state", state);
        ArrayList<County> allCounties = new ArrayList<County>(query.getResultList());
        ArrayList<Geometry> geometries = new ArrayList<>();
        for (County county : allCounties) {
            geometries.add(county.getGeometry());
        }
        FeatureCollectionJSON geoJson = new FeatureCollectionJSON(geometries);
        return geoJson.toString();
    }

    public static String getPrecincts(StateName state) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("orioles_db");
        EntityManager em = emf.createEntityManager();
        FeatureCollectionJSON geoJson = new FeatureCollectionJSON();
        Query query = em.createQuery("SELECT p FROM Precinct p WHERE p.state = :state");
        query.setParameter("state", state);
        ArrayList<Precinct> allPrecincts = new ArrayList<Precinct>(query.getResultList());
        int counter = 0;
        for (Precinct precinct : allPrecincts) {
            System.out.println("Compiled " + ++counter + " precincts.");
            geoJson.put(new JSONObject(precinct.getGeoJson()));
        }
        return geoJson.toString();
    }

    public static Map<String, String> getAllStatesCountyGeometry() {
        Map<String, String> countiesByState = new HashMap<String, String>();
        try {
            File NC = ResourceUtils.getFile("src/main/resources/static/json/NC/NCBoundary.json");
            File LA = ResourceUtils.getFile("src/main/resources/static/json/LA/LABoundary.json");
            File TX = ResourceUtils.getFile("src/main/resources/static/json/TX/TXBoundary.json");
            countiesByState.put("NC", new String(Files.readAllBytes(NC.toPath())).replaceAll("\\n",""));
            countiesByState.put("LA", new String(Files.readAllBytes(LA.toPath())).replaceAll("\\n",""));
            countiesByState.put("TX", new String(Files.readAllBytes(TX.toPath())).replaceAll("\\n",""));
            return countiesByState;
        } catch (Exception ex) {
            return countiesByState;
        }
    }
}
