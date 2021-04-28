package cse416.spring.service;

import cse416.spring.models.county.County;
import cse416.spring.models.districting.Districting;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class DistrictingServiceImpl implements DistrictingService{

    private EntityManager em;

    @Autowired
    public DistrictingServiceImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Districting findById(long id) {
        Districting districting = em.find(Districting.class, id);
        return districting;
    }

    @Override
    public Districting findByName(String name) {
        Query query = em.createQuery("SELECT d FROM Districting d WHERE d.name=:name");
        query.setParameter("name", name);
        List<Districting> districtings = query.getResultList();
        return districtings.get(0);
    }

    @Override
    public List<Districting> findByJob(int jobId) {
        Query query = em.createQuery("SELECT d FROM Districting d WHERE d.id =:id");
        query.setParameter("id", jobId);
        List<Districting> districtings = query.getResultList();
        return districtings;
    }

    @Override
    public List<Districting> findAllDistrictings() {
        Query query = em.createQuery("SELECT d from Districting d");
        List<Districting> districtings = query.getResultList();
        return districtings;
    }
}
