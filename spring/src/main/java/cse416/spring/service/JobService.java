package cse416.spring.service;

import cse416.spring.enums.StateName;
import cse416.spring.models.job.Job;

import java.util.List;

public interface JobService {
    Job findById(long id);

    List<Job> findByStateName(StateName state);

    List<Job> findAllJobs();
}
