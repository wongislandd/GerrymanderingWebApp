package cse416.spring.service;


import cse416.spring.models.districting.Districting;

import java.util.List;

public interface DistrictingService {
    Districting findById(long id);

    Districting findByName(String name);

    List<Districting> findByJob(int jobId);

    List<Districting> findAllDistrictings();



//    public static void getInterestingDistrictings() {

//    }
}
