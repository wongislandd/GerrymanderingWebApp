package cse416.spring.service.database;

import cse416.spring.enums.StateName;
import cse416.spring.models.districting.Districting;
import cse416.spring.models.job.Job;
import cse416.spring.models.job.JobSummary;
import org.json.JSONArray;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;

public class JobWriter {
    private static Job createJob(StateName state, int jobId, JobSummary js, EntityManager em) {
        Query query = em.createQuery("SELECT d FROM Districtings d WHERE d.jobID = :jobId");
        query.setParameter("jobId", jobId);

        ArrayList<Districting> districtingsInJob = new ArrayList<Districting>(query.getResultList());
        JSONArray districtingKeysArr = new JSONArray();
        for (Districting districting : districtingsInJob) {
            districtingKeysArr.put(districting.getId());
        }

        js.setSize(districtingsInJob.size());
        return new Job(state, js);
    }


    public static void persistJob(StateName state, int jobId, JobSummary js,
                                   EntityManager em) {

        Job newJob = createJob(state, jobId, js, em);
        em.getTransaction().begin();
        em.persist(newJob);
        em.getTransaction().commit();
    }

}
