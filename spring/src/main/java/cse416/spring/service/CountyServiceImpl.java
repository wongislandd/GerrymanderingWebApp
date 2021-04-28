package cse416.spring.service;

import cse416.spring.enums.StateName;
import cse416.spring.models.county.County;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

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
        Query query = em.createQuery("SELECT c FROM County c WHERE c.name=:name");
        query.setParameter("name", name);
        List<County> counties = query.getResultList();
        return counties.get(0);
    }

    @Override
    public List<County> findAllCounties() {
        Query query = em.createQuery("SELECT c FROM County c");
        return (List<County>) query.getResultList();
    }

    @Override
    public List<County> findByStateName(StateName state) {
        Query query = em.createQuery("SELECT c FROM County c where c.state=:state");
        query.setParameter("state", state);
        return (List<County>) query.getResultList();
    }
}
