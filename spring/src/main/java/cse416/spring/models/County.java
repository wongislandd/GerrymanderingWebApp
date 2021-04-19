package cse416.spring.models;

import cse416.spring.helperclasses.FeatureCollectionJSON;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class County {
    int id;
    String name;

    FeatureCollectionJSON geometry;

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
    @OneToOne(cascade = CascadeType.ALL)
    public FeatureCollectionJSON getGeometry() {
        return geometry;
    }

    public void setGeometry(FeatureCollectionJSON geometry) {
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

    public County(int id, String name, Collection<Precinct> precincts) {
        this.id = id;
        this.name = name;
        this.precincts = precincts;
    }


}
