package cse416.spring.models.precinct;

import cse416.spring.enums.StateName;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;

@Entity
public class Precinct {
    String name;
    String geoJson;
    Demographics demographics;
    StateName state;
    private int id;

    public Precinct(StateName state, int id, String name, String geoJson,
                    Demographics demographics) {

        this.state = state;
        this.name = name;
        this.geoJson = geoJson;
        this.demographics = demographics;
        this.id = id;
    }

    public Precinct() {
    }

    @Column
    public StateName getState() {
        return state;
    }

    public void setState(StateName state) {
        this.state = state;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Lob
    public String getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(String geometry) {
        this.geoJson = geometry;
    }

    public JSONArray retrieveCoordinates() {
        return new JSONObject(geoJson).getJSONObject("geometry")
                .getJSONArray("coordinates").getJSONArray(0);
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
