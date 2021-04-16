package cse416.spring.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.MGGGParams;
import cse416.spring.helperclasses.Server;
import cse416.spring.models.Districting;
import cse416.spring.models.DistrictingConstraints;
import cse416.spring.models.Job;
import cse416.spring.models.JobSummary;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/states")
public class StateController {
    Server server;

    public StateController(Server server) {
        this.server = server;
    }

    public JSONObject assembleCountyJSONByState(Map<String, String> countyGeometry) {
        return new JSONObject(countyGeometry);
    }


    @GetMapping(value = "getOutlines", produces = "application/json")
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

    @GetMapping("/statename/loadIncumbents")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> loadIncumbents() {
        try {
            // Currently just returns a hardcoded list of incumbents
            String body = "{\"Jimothy\": \"false\", \"Jim Jam\": \"false\", \"James\": \"false\", \"Gym\": \"false\"}";
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>("", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/current/jobs")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<HashSet> getJobs() {
        Job j2 = new Job(StateName.NORTH_CAROLINA, new JobSummary("Fake job", 100000, new MGGGParams(50, .05)), new ArrayList<Districting>());
        Job j = new Job(StateName.NORTH_CAROLINA, new JobSummary( "Fake job 2", 50000, new MGGGParams(90, .10)), new ArrayList<Districting>());
        HashSet combined = new HashSet<>();
        combined.add(j);
        combined.add(j2);
        return new ResponseEntity<>(combined, HttpStatus.OK);
    }

    @PostMapping("/test")
    @CrossOrigin
    public ResponseEntity<String> postTest(HttpServletRequest request) {
        request.getSession().setAttribute("entry", "poop");
        return new ResponseEntity<>("Test", HttpStatus.OK);
    }

    @GetMapping("/test")
    @CrossOrigin
    public ResponseEntity<String> getTest(HttpServletRequest request) {
        System.out.println(request.getSession().getAttribute("entry"));
        return new ResponseEntity<>("Test", HttpStatus.OK);
    }

}
