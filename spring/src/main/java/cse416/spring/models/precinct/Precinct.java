package cse416.spring.models.precinct;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.JSONObjectConverter;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;


@Entity
public class Precinct {
    String name;
    StateName state;
    JSONObject geoJson;
    Demographics demographics;
    private int id;

    public Precinct(int id, StateName state, String name, JSONObject geoJson, Demographics demographics) {
        this.name = name;
        this.state = state;
        this.geoJson = geoJson;
        this.demographics = demographics;
        this.id = id;
    }


    public Precinct() {

    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(columnDefinition = "TEXT")
    @Convert(converter = JSONObjectConverter.class)
    public JSONObject getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(JSONObject geometry) {
        this.geoJson = geometry;
    }

    public JSONArray retrieveCoordinates() {
        return geoJson.getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(0);
    }

    @OneToOne(cascade = CascadeType.ALL)
    public Demographics getDemographics() {
        return demographics;
    }

    public void setDemographics(Demographics demographics) {
        this.demographics = demographics;
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
