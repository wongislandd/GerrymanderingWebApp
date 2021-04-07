package cse416.spring.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import cse416.spring.helperclasses.Server;
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
import java.util.Map;

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
}
