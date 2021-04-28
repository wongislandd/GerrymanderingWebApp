package cse416.spring.controllers;

import cse416.spring.enums.StateName;
import cse416.spring.models.job.Job;
import cse416.spring.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static cse416.spring.controllers.StateController.getStateName;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService service) {
        this.jobService = service;
    }

    private ArrayList<Job> getJobs(StateName state) {
        return new ArrayList<Job>(jobService.findByStateName(state));
    }

    @GetMapping("/{stateID}/loadJobs")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ArrayList<Job>> loadJobs(@PathVariable("stateID") String stateID) {
        ArrayList<Job> jobs = getJobs(getStateName(stateID));
        // TODO Get a way for jackson not to send the districting keys, no need for them on the client
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }
}