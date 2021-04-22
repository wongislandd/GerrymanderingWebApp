package cse416.spring.helperclasses;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;

@Entity
public class FeatureCollectionJSON {
    public long id;
    public String geoJson;

    public FeatureCollectionJSON() {
        JSONObject json = new JSONObject();
        json.put("type", "FeatureCollection");
        json.put("features", new JSONArray());
    }

/* We put strings in of this JSON format */
//        "type": "Feature",
//        "geometry": {
//        "type": "Polygon",
//        "coordinates": [...],
//        "properties": {...}
    public void put(JSONObject featureJSON) {
        geoJson = new JSONObject(geoJson).append("features", featureJSON).toString();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(columnDefinition = "TEXT")
    public String getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(String geoJson) {
        this.geoJson = geoJson;
    }
}
