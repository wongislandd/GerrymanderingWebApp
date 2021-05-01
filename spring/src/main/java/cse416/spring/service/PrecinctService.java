package cse416.spring.service;

import cse416.spring.enums.StateName;
import cse416.spring.models.precinct.Precinct;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface PrecinctService {
    Precinct findById(long id);

    Set<Precinct> findByState(StateName state);

    HashMap<Integer, Precinct> getPrecinctHashMapByState(StateName state);
}
