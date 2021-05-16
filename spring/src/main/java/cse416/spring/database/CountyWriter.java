package cse416.spring.database;

import cse416.spring.enums.StateName;
import cse416.spring.models.county.County;
import cse416.spring.models.precinct.Precinct;
import cse416.spring.singletons.EmfSingleton;
import cse416.spring.singletons.PrecinctHashSingleton;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import static cse416.spring.helperclasses.FileReader.readJsonFile;
import static cse416.spring.database.DistrictingWriter.getPrecinctsFromKeys;

public class CountyWriter {
    public static void persistCounties() throws IOException {
        EntityManagerFactory emf = EmfSingleton.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        String countiesFilePath = "/LA/counties/CountiesPrecinctsMapping.json";
        StateName stateName = StateName.LOUISIANA;

        JSONObject jo = readJsonFile(countiesFilePath);
        HashMap<Integer, Precinct> allPrecincts = PrecinctHashSingleton.getPrecinctHash(stateName);
        Iterator<String> countyKeys = jo.keys();

        int counter = 0;
        while (countyKeys.hasNext()) {
            String countyKey = countyKeys.next();
            JSONObject county = jo.getJSONObject(countyKey);

            // Build the county object
            String name = county.getString("name");
            JSONArray precinctKeys = county.getJSONArray("precincts");
            Set<Precinct> precincts = getPrecinctsFromKeys(precinctKeys, allPrecincts);

            County c = new County(stateName, name, precincts);
            System.out.println("PERSISTING COUNTY " + counter++);
            em.persist(c);
        }

        em.getTransaction().commit();
        em.close();
    }
}
