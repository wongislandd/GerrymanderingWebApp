package cse416.spring.service;

import cse416.spring.enums.StateName;
import cse416.spring.models.precinct.Precinct;

import java.util.List;

public interface PrecinctService {
    Precinct findById(long id);

    Precinct findByName(String name);

    List<Precinct> findAllPrecincts();

    List<Precinct> findByState(StateName state);
}
