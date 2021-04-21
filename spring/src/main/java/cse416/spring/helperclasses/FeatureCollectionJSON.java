package cse416.spring.helperclasses;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;

@Entity
public class FeatureCollectionJSON {
    public long id;
    public JSONObject json = new JSONObject();

    public FeatureCollectionJSON() {
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
        json.append("features", featureJSON);
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
    @Convert(converter = JSONObjectConverter.class)
    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }
}
