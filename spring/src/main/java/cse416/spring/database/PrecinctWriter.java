package cse416.spring.database;

import cse416.spring.enums.StateName;
import cse416.spring.models.precinct.Demographics;
import cse416.spring.models.precinct.Precinct;
import cse416.spring.service.PrecinctService;
import cse416.spring.service.PrecinctServiceImpl;
import cse416.spring.singletons.EmfSingleton;
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

    public static void persistPrecincts() throws IOException {
        // Initialize entity manager
        EntityManagerFactory emf = EmfSingleton.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();


        /* Customization */
        String precinctsFilePath = "/LA/precincts_output.json";
        StateName stateName = StateName.LOUISIANA;

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
    }
}
