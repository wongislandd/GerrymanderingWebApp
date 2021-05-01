package cse416.spring.service;

import cse416.spring.enums.StateName;
import cse416.spring.models.county.County;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CountyServiceImpl implements CountyService {
    EntityManager em;

    public CountyServiceImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public County findById(long id) {
        return em.find(County.class, id);
    }

    @Override
    public County findByName(String name) {
        TypedQuery<County> query = em.createQuery("SELECT c FROM Counties c WHERE c.name=:name", County.class);
        query.setParameter("name", name);
        List<County> counties = query.getResultList();
        return counties.get(0);
    }

    @Override
    public Set<County> findByStateName(StateName state) {
        TypedQuery<County> query = em.createQuery("SELECT c FROM Counties c where c.state=:state", County.class);
        query.setParameter("state", state);
        return new HashSet<>(query.getResultList());
    }
}
