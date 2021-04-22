package cse416.spring.controller;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.MGGGParams;
import cse416.spring.helperclasses.Server;
import cse416.spring.models.districting.Districting;
import cse416.spring.models.job.Job;
import cse416.spring.models.job.JobSummary;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
            String body = "{\"Jimothy\": false, \"Jim Jam\": false, \"James\": false, \"Gym\": false}";
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>("", HttpStatus.EXPECTATION_FAILED);
        }
    }

//    @GetMapping("/current/jobs")
//    @CrossOrigin(origins = "http://localhost:3000")
//    public ResponseEntity<HashSet> getJobs() {
//        return new ResponseEntity<>(combined, HttpStatus.OK);
//    }

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
