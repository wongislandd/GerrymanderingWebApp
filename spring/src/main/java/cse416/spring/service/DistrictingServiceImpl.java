package cse416.spring.service;

import cse416.spring.enums.StateName;
import cse416.spring.models.districting.Districting;
import cse416.spring.helperclasses.DistrictingConstraints;
import cse416.spring.models.districting.EnactedDistricting;
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
        Query query = em.createQuery("SELECT d FROM Districtings d WHERE d.id =:id");
        query.setParameter("id", jobId);
        return (List<Districting>) query.getResultList();
    }

    @Override
    public EnactedDistricting findEnactedByState(StateName state){
        Query query = em.createQuery("SELECT d FROM EnactedDistrictings d WHERE d.state =:state");
        query.setParameter("state", state);
        List<EnactedDistricting> results =  query.getResultList();
        return results.get(0);
    }

    @Override
    public List<Districting> findAllDistrictings() {
        Query query = em.createQuery("SELECT d from Districtings d");
        return (List<Districting>) query.getResultList();
    }

    @Override
    public List<Districting> findByConstraints(DistrictingConstraints constraints) {
//        Query query = em.createQuery("SELECT d from Districtings d WHERE . . . . .");
        return null;
    }
}
