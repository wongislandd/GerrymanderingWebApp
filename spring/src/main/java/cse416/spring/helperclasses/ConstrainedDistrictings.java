package cse416.spring.helperclasses;

import cse416.spring.models.districting.Districting;
import cse416.spring.models.districting.DistrictingConstraints;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Collection;

@Component
public class ConstrainedDistrictings {
    Collection<Districting> districtings;
    double[][] boxAndWhiskerData;
    ObjectiveFunctionWeights currentWeights;
    DistrictingConstraints constraints;
    private long id;

    public ConstrainedDistrictings() {
    }

    public ConstrainedDistrictings(Collection<Districting> districtings, double[][] boxAndWhiskerData, ObjectiveFunctionWeights currentWeights, DistrictingConstraints constraints) {
        this.districtings = districtings;
        this.boxAndWhiskerData = boxAndWhiskerData;
        this.currentWeights = currentWeights;
        this.constraints = constraints;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public Collection<Districting> getDistrictings() {
        return districtings;
    }

    public void setDistrictings(Collection<Districting> districtings) {
        this.districtings = districtings;
    }

    @Column
    public double[][] getBoxAndWhiskerData() {
        return boxAndWhiskerData;
    }

    public void setBoxAndWhiskerData(double[][] boxAndWhiskerData) {
        this.boxAndWhiskerData = boxAndWhiskerData;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public ObjectiveFunctionWeights getCurrentWeights() {
        return currentWeights;
    }

    public void setCurrentWeights(ObjectiveFunctionWeights currentWeights) {
        this.currentWeights = currentWeights;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public DistrictingConstraints getConstraints() {
        return constraints;
    }

    public void setConstraints(DistrictingConstraints constraints) {
        this.constraints = constraints;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
