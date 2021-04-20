package cse416.spring.service;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
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
            JSONObject geometry = getConcaveGeometryOfPrecincts(precincts, gf, em);
            County c = new County(Integer.parseInt(key), name, precincts, geometry);
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


    public static boolean isMultiPolygon(JSONArray coordinates) {
        return coordinates.length() == 1;
    }

    /***
     * Returns an array of coordinate objects
     * @param coordinates
     * @return A coordinate array from the entries in coordinates
     */
    public static Coordinate[] getCoordsFromJSONArray(JSONArray coordinates) {
        Coordinate[] coords = new Coordinate[coordinates.length()];
        /* For each coordinate that comprises the precinct*/
        for (int j = 0; j < coordinates.length(); j++) {
            JSONArray currentCoords = coordinates.getJSONArray(j);
            coords[j] = new Coordinate(currentCoords.getDouble(0), currentCoords.getDouble(1));
        }
        return coords;
    }


    public static Geometry getConcaveHull(GeometryCollection gc) {
        /* The threshhold may need to be dynamic? */
        ConcaveHull ch = new ConcaveHull(gc, .12);
        Geometry hull = ch.getConcaveHull();
        return hull;
    }

    /***
     * Used to take a collection of precincts and find the "rubber-band" geometry
     * of the set of precinct geometries.
     *
     * @param precincts An array of precinct objects that belong to a county
     * @param gf The geometry factory to create geometry with
     * @param em The entity manager for access to the database
     * @return A JSONObject which of a "feature" containing a single polygon geometry
     */
    public static JSONObject getConcaveGeometryOfPrecincts(ArrayList<Precinct> precincts, GeometryFactory gf, EntityManager em) {
        /* Build a geometry collection in order to construct a concave hull object */
        ArrayList<Geometry> geometries = new ArrayList<>();
        /* For each precinct in the county */
        for (int i = 0; i < precincts.size(); i++) {
            JSONArray coordinates = precincts.get(i).retrieveCoordinates();
            if (isMultiPolygon(coordinates)) {
                /* Each entry in coordinates in a separate polygon, the separate polygon's will be treated as normal ones */
                for (int k = 0; k < coordinates.length(); k++) {
                    JSONArray currentPolygonCoords = coordinates.getJSONArray(k);
                    geometries.add(gf.createPolygon(getCoordsFromJSONArray(currentPolygonCoords)));
                }
            } else {
                geometries.add(gf.createPolygon(getCoordsFromJSONArray(coordinates)));
            }
        }
        /* Create an array version to be funneled into a geometry collection */
        Geometry[] geometryArr = new Geometry[geometries.size()];
        geometryArr = geometries.toArray(geometryArr);
        /* Using the geometry collection, construct the concave hull */
        GeometryCollection gc = gf.createGeometryCollection(geometryArr);
        Geometry hull = getConcaveHull(gc);
        SinglePolygonGeoJSON geojson = new SinglePolygonGeoJSON(hull.getCoordinates());

        return geojson.getJSON();
    }

}
