package cse416.spring.helperclasses.builders;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import cse416.spring.models.precinct.Precinct;
import org.json.JSONArray;
import org.opensphere.geometry.algorithm.ConcaveHull;

import java.util.ArrayList;

/***
 * Construct this helper class using an array list of precincts, then access
 * then get the geoJson of it.
 */
public class ConcaveHullBuilder {
    ArrayList<Precinct> precincts;


    public ConcaveHullBuilder(ArrayList<Precinct> precincts){
        this.precincts = precincts;
    }

    /***
     * Used to take a collection of precincts and find the "rubber-band" geometry
     * of the set of precinct geometries.
     *
     * @return A JSONObject which of a "feature" containing a single polygon geometry
     */
    public Geometry getConcaveGeometryOfPrecincts() {
        GeometryFactory gf = new GeometryFactory();
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
        return hull;
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
        ConcaveHull ch = new ConcaveHull(gc, .2);
        Geometry hull = ch.getConcaveHull();
        return hull;
    }
}
