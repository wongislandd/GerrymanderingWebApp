package cse416.spring.models;

import cse416.spring.helperclasses.GeometryJSON;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class County {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public int id;

    @Column
    String name;

    @OneToOne
    GeometryJSON geometry;

    @OneToMany
    Collection<Precinct> precincts;

    public County() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeometryJSON getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryJSON geometry) {
        this.geometry = geometry;
    }

    public Collection<Precinct> getPrecincts() {
        return precincts;
    }

    public void setPrecincts(Collection<Precinct> precincts) {
        this.precincts = precincts;
    }



    public County(String name, GeometryJSON geometry, Collection<Precinct> precincts) {
        this.name = name;
        this.geometry = geometry;
        this.precincts = precincts;
    }
}
