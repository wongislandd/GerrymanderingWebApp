package cse416.spring.service;

import cse416.spring.enums.StateName;
import cse416.spring.models.state.State;

import java.util.List;

public interface StateService {
    State findById(long id);

    State findByStateName(StateName state);

    List<State> findAllStates();

}
