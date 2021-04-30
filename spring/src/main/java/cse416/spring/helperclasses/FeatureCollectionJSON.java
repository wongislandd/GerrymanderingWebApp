package cse416.spring.helperclasses;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;

public class FeatureCollectionJSON {
    public long id;
    public JSONObject geoJson;

    public FeatureCollectionJSON() {
        geoJson = new JSONObject();
        geoJson.put("type", "FeatureCollection");
        geoJson.put("features", new JSONArray());
    }

    public FeatureCollectionJSON(ArrayList<Geometry> geometries) {
        geoJson = new JSONObject();
        geoJson.put("type", "FeatureCollection");
        geoJson.put("features", new JSONArray());
        // For each geometry
        for (int i=0;i<geometries.size();i++) {
            Geometry g = geometries.get(i);
            JSONObject feature = new JSONObject();
            Coordinate[] coords = g.getCoordinates();
            // GeoJSON needs this
            JSONArray outerCoordinates = new JSONArray();
            JSONArray coordinates = new JSONArray();
            for (Coordinate c : coords) {
                JSONArray entry = new JSONArray();
                entry.put(c.x);
                entry.put(c.y);
                coordinates.put(entry);
            }
            feature.put("type", "Feature");
            feature.put("properties", new JSONObject());
            JSONObject geometry = new JSONObject();
            geometry.put("type", "Polygon");
            outerCoordinates.put(coordinates);
            geometry.put("coordinates", outerCoordinates);
            feature.put("geometry", geometry);
            geoJson.getJSONArray("features").put(feature);
        }
    }


/* We put strings in of this JSON format */
//        "type": "Feature",
//        "geometry": {
//        "type": "Polygon",
//        "coordinates": [...],
//        "properties": {...}
    public void put(JSONObject featureJSON) {
        geoJson.append("features", featureJSON);
    }

    public String toString() {
        return geoJson.toString();
    }

    public JSONObject getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(JSONObject geoJson) {
        this.geoJson = geoJson;
    }
}
