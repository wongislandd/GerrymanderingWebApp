package cse416.spring.models;

import java.util.Collection;

public class Job {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Districting> getDistrictings() {
        return districtings;
    }

    public void setDistrictings(Collection<Districting> districtings) {
        this.districtings = districtings;
    }

    public Job(String name, Collection<Districting> districtings) {
        this.name = name;
        this.districtings = districtings;
    }

    Collection<Districting> districtings;
}
