package cse416.spring.helperclasses.builders;

import cse416.spring.models.precinct.Precinct;
import org.json.JSONArray;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.operation.union.UnaryUnionOp;

import java.util.Collection;
import java.util.HashSet;

/***
 * A helper class to find the union of the geometry of a list of precincts.
 */
public class UnionBuilder {
    private static boolean isMultiPolygon(JSONArray coordinates) {
        return coordinates.length() == 1;
    }

    private static Coordinate[] getCoords(JSONArray coordinates) {
        Coordinate[] coords = new Coordinate[coordinates.length()];

        for (int j = 0; j < coordinates.length(); j++) {
            JSONArray currentCoords = coordinates.getJSONArray(j);
            coords[j] = new Coordinate(currentCoords.getDouble(0),
                    currentCoords.getDouble(1));
        }
        return coords;
    }

    /***
     * Take a collection of precincts and find the "rubber-band" geometry
     * of the set of precinct geometries.
     *
     * @return A JSONObject which of a "feature" containing a single polygon geometry
     */
    public static Geometry getUnion(Collection<Precinct> precincts) {
        GeometryFactory gf = new GeometryFactory();
        Collection<Geometry> geometries = new HashSet<>();

        // TODO: Try treating multipolygons as 1 object and see if it improves the union
        for (Precinct precinct : precincts) {
            JSONArray coordinates = precinct.retrieveCoordinates();
            if (isMultiPolygon(coordinates)) {
                /* Each entry in coordinates in a separate polygon, the separate
                polygon's will be treated as normal ones */
                for (int k = 0; k < coordinates.length(); k++) {
                    JSONArray currentPolygonCoords = coordinates.getJSONArray(k);
                    geometries.add(gf.createPolygon(getCoords(currentPolygonCoords)));
                }
            } else {
                geometries.add(gf.createPolygon(getCoords(coordinates)));
            }
        }

        Geometry[] geometryArr = new Geometry[geometries.size()];
        geometryArr = geometries.toArray(geometryArr);

        GeometryCollection gc = gf.createGeometryCollection(geometryArr);
        return UnaryUnionOp.union(gc);
    }
}
