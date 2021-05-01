package cse416.spring.database;

import cse416.spring.models.job.Job;

import javax.persistence.EntityManager;

public class JobWriter {
    public static void persistJob(Job job, EntityManager em) {
        em.getTransaction().begin();
        em.persist(job);
        em.getTransaction().commit();
    }
}
