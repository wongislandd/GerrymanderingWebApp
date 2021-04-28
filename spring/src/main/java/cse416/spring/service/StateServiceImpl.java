package cse416.spring.service;

import cse416.spring.enums.StateName;
import cse416.spring.models.state.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Service
public class StateServiceImpl implements StateService {
    EntityManager em;

    @Autowired
    public StateServiceImpl(EntityManager em){
        this.em = em;
    }

    @Override
    public State findById(long id) {
        return em.find(State.class, id);
    }

    @Override
    public State findByStateName(StateName state) {
        Query query = em.createQuery("SELECT s FROM States s WHERE s.name=:state");
        query.setParameter("state", state);
        List<State> states = query.getResultList();
        return states.get(0);
    }

    @Override
    public List<State> findAllStates() {
        Query query = em.createQuery("SELECT s FROM States s");
        return (List<State>) query.getResultList();
    }
}
