package cse416.spring.controllers;

import cse416.spring.enums.StateName;
import cse416.spring.models.job.Job;
import cse416.spring.service.JobService;
import cse416.spring.singletons.PrecinctHashSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/jobs")
public class JobController {
    private final JobService jobService;

    @Autowired
    public JobController(JobService service) {
        this.jobService = service;
    }

    private ArrayList<Job> getJobs(StateName state) {
        return new ArrayList<>(jobService.findByStateName(state));
    }

    @GetMapping("/{state}/loadJobs")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ArrayList<Job>> loadJobs(@PathVariable("state") StateName state) {
        PrecinctHashSingleton.getPrecinctHash(state);
        ArrayList<Job> jobs = getJobs(state);

        // TODO Get a way for jackson not to send the districting keys, no need for them on the client
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }
}