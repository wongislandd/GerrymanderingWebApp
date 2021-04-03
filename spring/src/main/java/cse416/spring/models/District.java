package cse416.spring.models;

import cse416.spring.helperclasses.GeometryJSON;

import java.util.Collection;

public class District {
    public int id;
    GeometryJSON geometry;
    Collection<Precinct> precincts;
    DistrictMeasures measures;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public DistrictMeasures getMeasures() {
        return measures;
    }

    public void setMeasures(DistrictMeasures measures) {
        this.measures = measures;
    }

    public District(int id, GeometryJSON geometry, Collection<Precinct> precincts, DistrictMeasures measures) {
        this.id = id;
        this.geometry = geometry;
        this.precincts = precincts;
        this.measures = measures;
    }
}
