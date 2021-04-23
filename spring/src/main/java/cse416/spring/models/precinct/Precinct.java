package cse416.spring.models.precinct;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;


@Entity
public class Precinct {
    String name;

    String geoJson;

    Demographics demographics;
    private int id;

    public Precinct(int id, String name, String geoJson, Demographics demographics) {
        this.name = name;
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

    @Lob
    public String getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(String geometry) {
        this.geoJson = geometry;
    }

    public JSONArray retrieveCoordinates() {
        return new JSONObject(geoJson).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(0);
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
