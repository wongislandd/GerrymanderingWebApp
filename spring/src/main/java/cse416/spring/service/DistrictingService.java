package cse416.spring.service;


import cse416.spring.models.districting.Districting;
import cse416.spring.models.districting.DistrictingConstraints;

import java.util.List;

public interface DistrictingService {
    Districting findById(long id);

    List<Districting> findByJob(int jobId);

    List<Districting> findAllDistrictings();

    List<Districting> findByConstraints(DistrictingConstraints constriants);
}
