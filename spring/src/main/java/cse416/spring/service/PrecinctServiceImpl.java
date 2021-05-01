package cse416.spring.service;

import cse416.spring.enums.StateName;
import cse416.spring.models.precinct.Precinct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
    public Set<Precinct> findByState(StateName state) {
        TypedQuery<Precinct> query = em.createQuery("SELECT p FROM Precincts p WHERE p.state=:state", Precinct.class);
        query.setParameter("state", state);
        return new HashSet<>(query.getResultList());
    }

    @Override
    public HashMap<Integer, Precinct> getPrecinctHashMapByState(StateName state) {
        Collection<Precinct> allPrecincts = findByState(state);
        HashMap<Integer, Precinct> precinctHash = new HashMap<>();
        for (Precinct precinct : allPrecincts) {
            precinctHash.put(precinct.getPrecinctId(), precinct);
        }
        return precinctHash;
    }
}
