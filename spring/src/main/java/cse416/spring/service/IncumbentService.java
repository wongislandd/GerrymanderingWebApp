package cse416.spring.service;

import cse416.spring.enums.StateName;
import cse416.spring.models.precinct.Incumbent;

import java.util.List;

public interface IncumbentService {
    List<Incumbent> findByStateName(StateName state);
}
