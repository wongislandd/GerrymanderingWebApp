package cse416.spring.models;

import cse416.spring.helperclasses.GeometryJSON;

import javax.persistence.*;

@Entity
public class Precinct {
    String name;
    GeometryJSON geometry;
    County county;
    Demographics demographics;
    District district;
    private Long id;

    public Precinct(String name, GeometryJSON geometry, County county, Demographics demographics) {
        this.name = name;
        this.geometry = geometry;
        this.county = county;
        this.demographics = demographics;
    }

    public Precinct() {

    }

    @ManyToOne
    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
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

    @ManyToOne
    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    @OneToOne
    public Demographics getDemographics() {
        return demographics;
    }

    public void setDemographics(Demographics demographics) {
        this.demographics = demographics;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
