package cse416.spring.models;

import cse416.spring.helperclasses.FeatureCollectionJSON;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class County {
    long id;
    String name;
    FeatureCollectionJSON geometry;
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
    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public County(String name, FeatureCollectionJSON geometry, Collection<Precinct> precincts) {
        this.name = name;
        this.geometry = geometry;
        this.precincts = precincts;
    }


}
