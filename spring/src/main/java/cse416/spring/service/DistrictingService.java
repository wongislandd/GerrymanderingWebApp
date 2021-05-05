package cse416.spring.service;


import cse416.spring.enums.StateName;
import cse416.spring.models.districting.Districting;
import cse416.spring.helperclasses.DistrictingConstraints;
import cse416.spring.models.districting.EnactedDistricting;

import java.util.List;
import java.util.Set;

public interface DistrictingService {
    Districting findById(long id);

    List<Districting> findByJob(long jobId);

    List<Districting> findAllDistrictings();

    Set<Districting> findByConstraints(DistrictingConstraints constraints);

    EnactedDistricting findEnactedByState(StateName state);
}
