package cse416.spring.models.districting;

import cse416.spring.helperclasses.FeatureCollectionJSON;
import cse416.spring.models.county.County;
import cse416.spring.models.state.State;
import cse416.spring.models.district.District;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Districting {
    private long id;
    Collection<District> districts;
    DistrictingMeasures measures;
    double ObjectiveFunctionScore;
    FeatureCollectionJSON geometry;
    Collection<County> splitCounties;
    State state;

    public Districting() {

    }

    @OneToMany(cascade = CascadeType.ALL)
    public Collection<District> getDistricts() {
        return districts;
    }

    public void setDistricts(Collection<District> districts) {
        this.districts = districts;
    }

    @OneToOne(cascade = CascadeType.ALL)
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

    @OneToOne(cascade = CascadeType.ALL)
    public FeatureCollectionJSON getGeometry() {
        return geometry;
    }

    public void setGeometry(FeatureCollectionJSON geometry) {
        this.geometry = geometry;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public Collection<County> getSplitCounties() {
        return this.splitCounties;
    }

    public void setSplitCounties(Collection<County> splitCounties) {
        this.splitCounties = splitCounties;
    }

    @OneToOne(cascade = CascadeType.ALL)
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
    public long getId() {
        return id;
    }

    public Districting(Collection<District> districts, DistrictingMeasures measures, double ObjectiveFunctionScore, FeatureCollectionJSON geometry, Collection<County> splitCounties, State state) {
        this.districts = districts;
        this.measures = measures;
        this.ObjectiveFunctionScore = ObjectiveFunctionScore;
        this.geometry = geometry;
        this.splitCounties = splitCounties;
        this.state = state;
    }
}
