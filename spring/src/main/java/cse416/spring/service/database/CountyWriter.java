package cse416.spring.service.database;

import cse416.spring.enums.StateName;
import cse416.spring.models.county.County;
import cse416.spring.models.precinct.Precinct;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static cse416.spring.helperclasses.FileReader.readJsonFile;
import static cse416.spring.service.database.DistrictingWriter.getPrecinctsFromKeys;
import static cse416.spring.service.database.PrecinctWriter.getAllPrecincts;

public class CountyWriter {
    public static void persistCounties() throws IOException {
        // Get entity manager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("orioles_db");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        /* Customization */
        String countiesFilePath = "/NC/counties/CountiesPrecinctsMapping.json";
        StateName stateName = StateName.NORTH_CAROLINA;

        JSONObject jo = readJsonFile(countiesFilePath);
        HashMap<Integer, Precinct> allPrecincts = getAllPrecincts();
        Iterator<String> keys = jo.keys();

        /* For each county */
        int counter = 0;
        while (keys.hasNext()) {
            // Key is the county ID
            String countyID = keys.next();
            JSONObject county = jo.getJSONObject(countyID);

            // Build the county object
            String name = county.getString("name");
            JSONArray precinctKeys = county.getJSONArray("precincts");
            ArrayList<Precinct> precincts = getPrecinctsFromKeys(precinctKeys, allPrecincts);

            County c = new County(stateName, Integer.parseInt(countyID), name, precincts);
            System.out.println("PERSISTING COUNTY " + counter++);
            em.persist(c);
        }

        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
