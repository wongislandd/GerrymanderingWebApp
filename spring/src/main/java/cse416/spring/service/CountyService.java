package cse416.spring.service;

import cse416.spring.enums.StateName;
import cse416.spring.models.county.County;

import java.util.List;

public interface CountyService {
    County findById(long id);

    County findByName(String name);

    List<County> findAllCounties();

    List<County> findByStateName(StateName state);
}
