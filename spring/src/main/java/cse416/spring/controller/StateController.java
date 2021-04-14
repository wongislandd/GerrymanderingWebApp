package cse416.spring.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.MGGGParams;
import cse416.spring.helperclasses.Server;
import cse416.spring.models.Districting;
import cse416.spring.models.Job;
import cse416.spring.models.JobSummary;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/state")
public class StateController {
    Server server;

    public StateController(Server server) {
        this.server = server;
    }

    public JSONObject assembleCountyJSONByState(Map<String, String> countyGeometry) {
        return new JSONObject(countyGeometry);
    }


    @GetMapping(value = "allCounties", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> getAllCounties() {
        try {
            String body = assembleCountyJSONByState(server.getAllStatesCountyGeometry()).toString();
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>("", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/current/jobs")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<HashSet> getJobs() {
        Job j2 = new Job(StateName.NORTH_CAROLINA, 1, new JobSummary(1, "Fake job", 100000, new MGGGParams(50, .05)), new ArrayList<Districting>());
        Job j = new Job(StateName.NORTH_CAROLINA, 2, new JobSummary(2, "Fake job 2", 50000, new MGGGParams(90, .10)), new ArrayList<Districting>());
        HashSet combined = new HashSet<>();
        combined.add(j);
        combined.add(j2);
        return new ResponseEntity<>(combined, HttpStatus.OK);
    }


}
