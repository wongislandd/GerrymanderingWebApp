package cse416.spring.models;

import cse416.spring.helperclasses.GeometryJSON;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Districting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    int id;

    @OneToMany(mappedBy = "districting")
    Collection<District> districts;

    @OneToOne
    DistrictingMeasures measures;

    @Column
    double ObjectiveFunctionScore;

    @OneToOne
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
}
