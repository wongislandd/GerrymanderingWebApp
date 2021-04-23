package cse416.spring.service;

import com.github.javafaker.Faker;
import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.FeatureCollectionJSON;
import cse416.spring.models.job.Job;
import cse416.spring.models.precinct.Incumbent;
import cse416.spring.models.precinct.Precinct;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;

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

    public static String getPrecincts(StateName state) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("orioles_db");
        EntityManager em = emf.createEntityManager();
        FeatureCollectionJSON geoJson = new FeatureCollectionJSON();
        Query query = em.createQuery("SELECT p FROM Precinct p");
        ArrayList<Precinct> allPrecincts = new ArrayList<Precinct>(query.getResultList());
        int counter = 0;
        for (Precinct precinct : allPrecincts) {
            System.out.println("Compiled " + ++counter + " precincts.");
            geoJson.put(new JSONObject(precinct.getGeoJson()));
        }
        return geoJson.toString();
    }
}
