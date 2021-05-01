package cse416.spring.helperclasses;

import cse416.spring.models.district.District;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FeatureCollectionJSON {
    public long id;
    public JSONObject geoJson;

    public FeatureCollectionJSON() {
        geoJson = new JSONObject();
        geoJson.put("type", "FeatureCollection");
        geoJson.put("features", new JSONArray());
    }

    public FeatureCollectionJSON(Collection<District> districts) throws IOException {
        ArrayList<District> districtsArr = new ArrayList<>(districts);
        geoJson = new JSONObject();
        geoJson.put("type", "FeatureCollection");
        geoJson.put("features", new JSONArray());
        // For each geometry
        for (int i=0;i<districts.size();i++) {
            District d = districtsArr.get(i);
            JSONObject feature = new JSONObject();
            feature.put("type", "Feature");
            feature.put("properties", createPropertiesJsonObject(d));
            feature.put("geometry", createGeometryJsonObject(d));
            geoJson.getJSONArray("features").put(feature);
        }
    }

    private JSONObject createGeometryJsonObject(District d) throws IOException {
        Geometry g = d.getGeometry();
        Coordinate[] coords = g.getCoordinates();
        JSONArray innerCoordinates = new JSONArray();
        for (Coordinate c : coords) {
            JSONArray entry = new JSONArray();
            entry.put(c.x);
            entry.put(c.y);
            innerCoordinates.put(entry);
        }
        JSONObject geometry = new JSONObject();
        JSONArray outerCoordinates = new JSONArray();
        geometry.put("type", "Polygon");
        outerCoordinates.put(innerCoordinates);
        geometry.put("coordinates", outerCoordinates);
        return geometry;
    }

    private JSONObject createPropertiesJsonObject(District d) {
        JSONObject properties = new JSONObject();
        return properties;
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
//        "coordinates": [[...]],
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
