package cse416.spring.service;

import cse416.spring.models.Demographics;
import cse416.spring.models.District;
import cse416.spring.models.Districting;
import cse416.spring.models.Precinct;

import java.util.HashSet;
import java.util.Set;

public class StateService {
    public Demographics generateRandomDemographics() {
        Demographics d = new Demographics()
    }

    public Precinct generateRandomPrecinct() {
        Precinct p = new Precinct();
        return p;
    }

    public District generateRandomDistrict() {
        District d = new District();
        return d;
    }

    public Districting generateRandomDistricting() {
        Districting d = new Districting();
        return d;
    }

    public HashSet<Districting> generateRandomDistrictings() {
        HashSet<Districting> districtings = new HashSet<Districting>();
        return districtings;
    }
}
