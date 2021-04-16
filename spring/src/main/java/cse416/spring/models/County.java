package cse416.spring.models;

import cse416.spring.helperclasses.GeometryJSON;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class County {
    long id;
    String name;
    GeometryJSON geometry;
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
    @OneToOne
    public GeometryJSON getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryJSON geometry) {
        this.geometry = geometry;
    }

    @OneToMany
    public Collection<Precinct> getPrecincts() {
        return precincts;
    }

    public void setPrecincts(Collection<Precinct> precincts) {
        this.precincts = precincts;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public County(String name, GeometryJSON geometry, Collection<Precinct> precincts) {
        this.name = name;
        this.geometry = geometry;
        this.precincts = precincts;
    }


}
