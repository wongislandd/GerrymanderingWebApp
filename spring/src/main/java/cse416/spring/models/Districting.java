package cse416.spring.models;

import cse416.spring.helperclasses.GeometryJSON;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Districting {
    private long id;
    Collection<District> districts;
    DistrictingMeasures measures;
    double ObjectiveFunctionScore;
    GeometryJSON geometry;
    Collection<County> splitCounties;
    State state;

    public Districting() {

    }

    @OneToMany
    public Collection<District> getDistricts() {
        return districts;
    }

    public void setDistricts(Collection<District> districts) {
        this.districts = districts;
    }

    @OneToOne
    public DistrictingMeasures getMeasures() {
        return measures;
    }

    public void setMeasures(DistrictingMeasures measures) {
        this.measures = measures;
    }

    @Column
    public double getObjectiveFunctionScore() {
        return ObjectiveFunctionScore;
    }

    public void setObjectiveFunctionScore(double objectiveFunctionScore) {
        ObjectiveFunctionScore = objectiveFunctionScore;
    }

    @OneToOne
    public GeometryJSON getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryJSON geometry) {
        this.geometry = geometry;
    }

    @OneToMany
    public Collection<County> getSplitCounties() {
        return this.splitCounties;
    }

    public void setSplitCounties(Collection<County> splitCounties) {
        this.splitCounties = splitCounties;
    }

    @OneToOne
    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public Districting(Collection<District> districts, DistrictingMeasures measures, double ObjectiveFunctionScore, GeometryJSON geometry, Collection<County> splitCounties, State state) {
        this.districts = districts;
        this.measures = measures;
        this.ObjectiveFunctionScore = ObjectiveFunctionScore;
        this.geometry = geometry;
        this.splitCounties = splitCounties;
        this.state = state;
    }
}
