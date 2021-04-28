package cse416.spring.service;

import cse416.spring.enums.StateName;
import cse416.spring.models.precinct.Precinct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class PrecinctServiceImpl implements PrecinctService{

    EntityManager em;

    @Autowired
    public PrecinctServiceImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Precinct findById(long id) {
        Precinct precinct = em.find(Precinct.class, id);
        return precinct;
    }

    @Override
    public Precinct findByName(String name) {
        Query query = em.createQuery("SELECT p FROM Precinct p WHERE p.name=:name");
        query.setParameter("name", name);
        List<Precinct> precincts = query.getResultList();
        return precincts.get(0);
    }

    @Override
    public List<Precinct> findAllPrecincts() {
        Query query = em.createQuery("SELECT p from Precinct p");
        List<Precinct> precincts = query.getResultList();
        return precincts;
    }

    @Override
    public List<Precinct> findByState(StateName state) {
        Query query = em.createQuery("SELECT p FROM Precinct p WHERE c.state=:state");
        query.setParameter("state", state);
        List<Precinct> precincts = query.getResultList();
        return precincts;
    }
}
