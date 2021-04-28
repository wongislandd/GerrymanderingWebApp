package cse416.spring.service;

import cse416.spring.models.districting.Districting;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class DistrictingServiceImpl implements DistrictingService {
    private final EntityManager em;

    public DistrictingServiceImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Districting findById(long id) {
        return em.find(Districting.class, id);
    }

    @Override
    public List<Districting> findByJob(int jobId) {
        Query query = em.createQuery("SELECT d FROM Districting d WHERE d.id =:id");
        query.setParameter("id", jobId);
        return (List<Districting>) query.getResultList();
    }

    @Override
    public List<Districting> findAllDistrictings() {
        Query query = em.createQuery("SELECT d from Districting d");
        return (List<Districting>) query.getResultList();
    }
}
