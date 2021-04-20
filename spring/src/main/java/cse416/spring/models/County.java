package cse416.spring.models;

import cse416.spring.helperclasses.FeatureCollectionJSON;
import cse416.spring.helperclasses.JSONObjectConverter;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class County {
    int id;
    String name;

    JSONObject geometry;

    // Have county store an array of precinct IDs? Shouldn't store whole object,
    // or should it?
    Collection<Precinct> precincts;

    public County() {

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
    public JSONObject getGeometry() {
        return geometry;
    }

    public void setGeometry(JSONObject geometry) {
        this.geometry = geometry;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public Collection<Precinct> getPrecincts() {
        return precincts;
    }

    public void setPrecincts(Collection<Precinct> precincts) {
        this.precincts = precincts;
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public County(int id, String name, Collection<Precinct> precincts, JSONObject geometry) {
        this.id = id;
        this.name = name;
        this.precincts = precincts;
        this.geometry = geometry;
    }


}
