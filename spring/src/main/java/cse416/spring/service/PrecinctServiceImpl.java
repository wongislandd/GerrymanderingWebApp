package cse416.spring.service;

import cse416.spring.enums.StateName;
import cse416.spring.models.precinct.Precinct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class PrecinctServiceImpl implements PrecinctService {

    EntityManager em;

    @Autowired
    public PrecinctServiceImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Precinct findById(long id) {
        return em.find(Precinct.class, id);
    }

    @Override
    public Precinct findByName(String name) {
        Query query = em.createQuery("SELECT p FROM Precincts p WHERE p.name=:name");
        query.setParameter("name", name);
        List<Precinct> precincts = query.getResultList();
        return precincts.get(0);
    }

    @Override
    public List<Precinct> findAllPrecincts() {
        Query query = em.createQuery("SELECT p from Precincts p");
        return (List<Precinct>) query.getResultList();
    }

    @Override
    public List<Precinct> findByState(StateName state) {
        Query query = em.createQuery("SELECT p FROM Precincts p WHERE p.state=:state");
        query.setParameter("state", state);
        return (List<Precinct>) query.getResultList();
    }
}
