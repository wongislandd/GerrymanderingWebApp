package cse416.spring.service;

import cse416.spring.enums.StateName;
import cse416.spring.models.precinct.Incumbent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class IncumbentServiceImpl implements IncumbentService {
    EntityManager em;

    @Autowired
    public IncumbentServiceImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Incumbent> findByStateName(StateName state) {
        Query query = em.createQuery("SELECT i from Incumbents i WHERE i.state=:state");
        query.setParameter("state", state);
        return (List<Incumbent>) query.getResultList();
    }
}
