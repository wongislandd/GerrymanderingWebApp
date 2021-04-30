package cse416.spring.database;

import cse416.spring.enums.StateName;
import cse416.spring.models.precinct.Demographics;
import cse416.spring.models.precinct.Precinct;
import cse416.spring.service.PrecinctService;
import cse416.spring.service.PrecinctServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static cse416.spring.helperclasses.FileReader.readJsonFile;

public class PrecinctWriter {
    public static Precinct buildPrecinctFromJSON(StateName state, JSONObject feature) {
        JSONObject properties = feature.getJSONObject("properties");

        int id = properties.getInt("id");
        String precinctName = properties.getString("name");

        int asian = properties.getInt("asian");
        int black = properties.getInt("black");
        int natives = properties.getInt("native_american");
        int pacific = properties.getInt("pacific_islander");
        int white = properties.getInt("white");
        int hispanic = properties.getInt("hispanic");
        int otherRace = properties.getInt("other");

        int TP = properties.getInt("population");
        int VAP = -1;
        int CVAP = -1;

        Demographics demographics = new Demographics(asian, black, natives,
                pacific, white, hispanic, otherRace, TP, VAP, CVAP);

        return new Precinct(state, id, precinctName, feature.toString(), demographics);
    }

    // TODO Utilize PrecinctService to get all precincts
    public static HashMap<Long, Precinct> getAllPrecincts() {
        // Get all precincts
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("orioles_db");
        EntityManager em = emf.createEntityManager();
        PrecinctService precinctService = new PrecinctServiceImpl(em);
        ArrayList<Precinct> allPrecincts = new ArrayList<>(precinctService.findAllPrecincts());

        // Convert the allPrecincts list into a hashmap of (id, precinct)
        HashMap<Long, Precinct> precinctHash = new HashMap<>();

        for (Precinct precinct : allPrecincts) {
            precinctHash.put(precinct.getId(), precinct);
        }
        em.close();
        emf.close();
        return precinctHash;
    }

    public static void persistPrecincts() throws IOException {
        // Initialize entity manager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("orioles_db");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();


        /* Customization */
        String precinctsFilePath = "/NC/precincts_output.json";
        StateName stateName = StateName.NORTH_CAROLINA;

        JSONObject jo = readJsonFile(precinctsFilePath);
        JSONArray features = jo.getJSONArray("features");

        for (int i = 0; i < features.length(); i++) {
            JSONObject feature = features.getJSONObject(i);
            Precinct p = buildPrecinctFromJSON(stateName, feature);
            System.out.println("Persisting Precinct " + i);
            em.persist(p);
        }

        System.out.println("Committing precincts.");
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
