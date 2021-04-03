package cse416.spring.models;

import cse416.spring.helperclasses.GeometryJSON;

public class Precinct {
    int id;
    String name;
    GeometryJSON geometry;
    County county;
    Demographics demographics;

    public Precinct(int id, String name, GeometryJSON geometry, County county, Demographics demographics) {
        this.id = id;
        this.name = name;
        this.geometry = geometry;
        this.county = county;
        this.demographics = demographics;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
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

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public Demographics getDemographics() {
        return demographics;
    }

    public void setDemographics(Demographics demographics) {
        this.demographics = demographics;
    }
}
