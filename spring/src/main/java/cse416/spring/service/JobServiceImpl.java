package cse416.spring.service;

import cse416.spring.enums.StateName;
import cse416.spring.models.job.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Service
public class JobServiceImpl implements JobService {
    EntityManager em;

    @Autowired
    public JobServiceImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Job findById(long id) {
        return em.find(Job.class, id);
    }

    @Override
    public List<Job> findByStateName(StateName state) {
        Query query = em.createQuery("SELECT j from Jobs j WHERE j.state=:state");
        query.setParameter("state", state);
        return (List<Job>) query.getResultList();
    }
    @Override
    public List<Job> findAllJobs() {
        Query query = em.createQuery("SELECT j from Jobs j");
        return (List<Job>) query.getResultList();
    }
}
