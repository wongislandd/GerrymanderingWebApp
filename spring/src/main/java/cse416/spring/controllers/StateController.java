package cse416.spring.controllers;

import cse416.spring.enums.StateName;
import cse416.spring.models.job.Job;
import cse416.spring.models.precinct.Incumbent;
import cse416.spring.service.StateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/states")
public class StateController {
    public StateController() {
    }

    private static StateName getStateName(String stateID) {
        switch (stateID) {
            case "LA":
                return StateName.LOUISIANA;
            case "TX":
                return StateName.TEXAS;
            default:
                return StateName.NORTH_CAROLINA;
        }
    }

    @GetMapping("/{stateID}/loadIncumbents")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ArrayList<Incumbent>> loadIncumbents(@PathVariable("stateID") String stateID) {
        ArrayList<Incumbent> incumbents = StateService.getIncumbents(getStateName(stateID));
        return new ResponseEntity<>(incumbents, HttpStatus.OK);
    }

    @GetMapping("/{stateID}/loadJobs")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ArrayList<Job>> loadJobs(@PathVariable("stateID") String stateID) {
        ArrayList<Job> jobs = StateService.getJobs(getStateName(stateID));
        // TODO
        /* Get a way for jackson not to send the districting keys, no need for them on the client */
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("/{stateID}/loadPrecincts")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> loadPrecincts(@PathVariable("stateID") String stateID) {
        String precinctsGeoJson = StateService.getPrecincts(getStateName(stateID));
        return new ResponseEntity<>(precinctsGeoJson, HttpStatus.OK);
    }

    @GetMapping("/{stateID}/loadCounties")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> loadCounties(@PathVariable("stateID") String stateID) {
        String countiesGeoJson = StateService.getCounties(getStateName(stateID));
        return new ResponseEntity<>(countiesGeoJson, HttpStatus.OK);
    }
}
