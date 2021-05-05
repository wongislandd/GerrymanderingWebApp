package cse416.spring.service;

import cse416.spring.enums.StateName;
import cse416.spring.models.districting.Districting;
import cse416.spring.helperclasses.DistrictingConstraints;
import cse416.spring.models.districting.EnactedDistricting;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public List<Districting> findByJob(long jobId) {
        Query query = em.createQuery("SELECT d FROM Districtings d WHERE d.job.id =:id");
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
        return query.getResultList();
    }

    @Override
    public Set<Districting> findByConstraints(DistrictingConstraints constraints) {
        //TODO Move the entire filtering to the query, may need to add more properties to districting
        Query query;
        switch (constraints.getCompactnessType()) {
            case POLSBY_POPPER:
                query = em.createQuery("SELECT d from Districtings d WHERE d.job.id =:jobId AND d.measures.compactnessAvg.polsbyPopper>:compactnessThreshold");
                break;
            case GRAPH_COMPACTNESS:
                query = em.createQuery("SELECT d from Districtings d WHERE d.job.id =:jobId AND d.measures.compactnessAvg.graphCompactness>:compactnessThreshold");
                break;
            default:
                query = em.createQuery("SELECT d from Districtings d WHERE d.job.id =:jobId AND d.measures.compactnessAvg.populationFatness>:compactnessThreshold");
        }
        query.setParameter("jobId", constraints.getJobId());
        query.setParameter("compactnessThreshold", constraints.getCompactnessThreshold());
        Set<Districting> preliminaryResults = new HashSet<>(query.getResultList());
        Set<Districting> filteredResults = new HashSet<>();
        for (Districting d : preliminaryResults) {
            // TODO Also filter by voting population
            int majorityMinorityDistrictsCount = d.getMMDistrictsCount(constraints.getMinorityPopulation(), constraints.getMinorityThreshold());
            if(majorityMinorityDistrictsCount > constraints.getMinMinorityDistricts()) {
                filteredResults.add(d);
                d.getMeasures().setMajorityMinorityDistricts(majorityMinorityDistrictsCount);
            };
        }
        return filteredResults;
    }
}
