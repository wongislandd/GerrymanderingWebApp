package cse416.spring.service;

import cse416.spring.enums.StateName;
import cse416.spring.models.county.County;

import java.util.Set;

public interface CountyService {
    County findById(long id);

    County findByName(String name);

    Set<County> findByStateName(StateName state);
}
