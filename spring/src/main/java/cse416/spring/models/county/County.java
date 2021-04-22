package cse416.spring.models.county;

import com.vividsolutions.jts.geom.Geometry;
import cse416.spring.helperclasses.builders.ConcaveHullBuilder;
import cse416.spring.models.precinct.Precinct;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;


@Entity
public class County {
    int id;
    String name;
    Geometry geometry;
    String precinctKeys;

    public County() {

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

    @Column(columnDefinition = "TEXT")
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

    public County(int id, String name, ArrayList<Precinct> precincts) {
        this.id = id;
        this.name = name;
        JSONArray precinctKeysArr = new JSONArray();
        for (int i=0;i<precincts.size();i++) {
            precinctKeysArr.put(precincts.get(i).getId());
        }
        this.precinctKeys = new JSONObject().put("precincts", precinctKeysArr).toString();
        this.geometry = new ConcaveHullBuilder(precincts).getConcaveGeometryOfPrecincts();
    }
}
