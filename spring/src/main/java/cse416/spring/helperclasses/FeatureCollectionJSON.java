package cse416.spring.helperclasses;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;

public class FeatureCollectionJSON {
    public long id;
    public JSONObject geoJson;

    public FeatureCollectionJSON() {
        geoJson = new JSONObject();
        geoJson.put("type", "FeatureCollection");
        geoJson.put("features", new JSONArray());
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
