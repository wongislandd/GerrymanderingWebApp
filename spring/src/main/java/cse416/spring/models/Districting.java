package cse416.spring.models;

import cse416.spring.helperclasses.GeometryJSON;
import cse416.spring.infowrappers.DistrictingMeasures;

import java.util.Collection;

public class Districting {
    int id;

    Collection<District> districts;

    DistrictingMeasures measures;

    double ObjectiveFunctionScore;

    GeometryJSON geometry;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Collection<District> getDistricts() {
        return districts;
    }

    public void setDistricts(Collection<District> districts) {
        this.districts = districts;
    }

    public DistrictingMeasures getMeasures() {
        return measures;
    }

    public void setMeasures(DistrictingMeasures measures) {
        this.measures = measures;
    }

    public double getObjectiveFunctionScore() {
        return ObjectiveFunctionScore;
    }

    public void setObjectiveFunctionScore(double objectiveFunctionScore) {
        ObjectiveFunctionScore = objectiveFunctionScore;
    }

    public GeometryJSON getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryJSON geometry) {
        this.geometry = geometry;
    }

    public Districting(int id, Collection<District> districts, DistrictingMeasures measures, double objectiveFunctionScore, GeometryJSON geometry) {
        this.id = id;
        this.districts = districts;
        this.measures = measures;
        ObjectiveFunctionScore = objectiveFunctionScore;
        this.geometry = geometry;
    }
}
