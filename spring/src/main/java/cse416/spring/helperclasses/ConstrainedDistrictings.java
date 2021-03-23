package cse416.spring.helperclasses;

import cse416.spring.models.Districting;

import java.util.Collection;

public class ConstrainedDistrictings {
    Collection<Districting> districtings;
    double[][] boxAndWhiskerData;

    public ConstrainedDistrictings(Collection<Districting> districtings, double[][] boxAndWhiskerData) {
        this.districtings = districtings;
        this.boxAndWhiskerData = boxAndWhiskerData;
    }

    public Collection<Districting> getDistrictings() {
        return districtings;
    }

    public void setDistrictings(Collection<Districting> districtings) {
        this.districtings = districtings;
    }

    public double[][] getBoxAndWhiskerData() {
        return boxAndWhiskerData;
    }

    public void setBoxAndWhiskerData(double[][] boxAndWhiskerData) {
        this.boxAndWhiskerData = boxAndWhiskerData;
    }
}
