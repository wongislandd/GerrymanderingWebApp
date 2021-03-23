package cse416.spring.databaseclasses;

import cse416.spring.helperclasses.GeometryJSON;

import java.util.Collection;

public class County {
    String name;

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

    GeometryJSON geometry;
    Collection<Precinct> precincts;

    public County(String name, GeometryJSON geometry, Collection<Precinct> precincts) {
        this.name = name;
        this.geometry = geometry;
        this.precincts = precincts;
    }
}
