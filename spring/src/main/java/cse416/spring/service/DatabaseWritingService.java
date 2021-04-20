package cse416.spring.service;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import cse416.spring.helperclasses.ConcaveHullBuilder;
import cse416.spring.helperclasses.EntityManagerSingleton;
import cse416.spring.helperclasses.SinglePolygonGeoJSON;
import cse416.spring.models.County;
import cse416.spring.models.Demographics;
import cse416.spring.models.Precinct;
import org.json.JSONArray;
import org.json.JSONObject;
import org.locationtech.jts.geom.Coordinates;
import org.opensphere.geometry.algorithm.ConcaveHull;
import org.springframework.util.ResourceUtils;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;

public class DatabaseWritingService {

    public static String readFile(String filePath) throws IOException {
        File file = ResourceUtils.getFile("src/main/resources/static/" + filePath);
        String content = new String(Files.readAllBytes(file.toPath()));
        return content;
    }

    public static void persistPrecinctsAndCounties() throws IOException {
        persistPrecincts();
        persistCounties();
        EntityManagerSingleton.getInstance().shutdown();
    }

    public static void persistPrecincts() throws IOException {
        EntityManager em = EntityManagerSingleton.getInstance().getEntityManager();
        em.getTransaction().begin();
        String content = readFile("/json/NC/PrecinctGeoDataSimplified.json");
        JSONObject j = new JSONObject(content);
        JSONArray features = j.getJSONArray("features");
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
            Demographics d = new Demographics(democrats, republicans, otherParty, asian, black, natives,
                    pacific, whiteHispanic, whiteNonHispanic, otherRace, TP, VAP, CVAP);
            Precinct p = new Precinct(id, precinctName, feature, d);
            em.persist(p);
        }

        /* Commit and close */
        em.getTransaction().commit();
    }

    public static void persistCounties() throws IOException {
        /* Get entity manager */
        EntityManager em = EntityManagerSingleton.getInstance().getEntityManager();
        em.getTransaction().begin();
        GeometryFactory gf = new GeometryFactory();
        String mapping = readFile("/json/NC/CountiesPrecinctsMapping.json");
        JSONObject jo = new JSONObject(mapping);
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
            JSONObject hullGeoJson = new SinglePolygonGeoJSON(hull.getCoordinates()).getJSON();
            County c = new County(Integer.parseInt(key), name, precincts, hullGeoJson);
            em.persist(c);
        }
        /* Commit and close */
        em.getTransaction().commit();
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
