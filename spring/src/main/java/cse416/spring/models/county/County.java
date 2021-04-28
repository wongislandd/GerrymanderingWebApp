package cse416.spring.models.county;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.builders.UnionBuilder;
import cse416.spring.models.precinct.Precinct;
import org.json.JSONArray;
import org.json.JSONObject;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.ArrayList;


@Entity(name = "Counties")
public class County {
    int id;
    String name;
    Geometry geometry;
    String precinctKeys;
    StateName state;


    public County() {

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
    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    @Lob
    @Column(name = "precinct_keys")
    public String getPrecinctKeys() {
        return precinctKeys;
    }

    public void setPrecinctKeys(String precinctKeys) {
        this.precinctKeys = precinctKeys;
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public County(StateName state, int id, String name, ArrayList<Precinct> precincts) {
        this.state = state;
        this.id = id;
        this.name = name;

        JSONArray precinctKeys = new JSONArray();
        for (Precinct precinct : precincts) {
            precinctKeys.put(precinct.getId());
        }

        this.precinctKeys = new JSONObject().put("precincts", precinctKeys).toString();
        this.geometry = UnionBuilder.getUnion(precincts);
    }
}
