package cse416.spring.controllers;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.FeatureCollectionJSON;
import cse416.spring.models.precinct.Precinct;
import cse416.spring.service.JobService;
import cse416.spring.service.PrecinctService;
import cse416.spring.service.StateService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;

import static cse416.spring.controllers.StateController.getStateName;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/precincts")
public class PrecinctController {
    private PrecinctService precinctService;

    @Autowired
    public PrecinctController(PrecinctService service) {
        this.precinctService = service;
    }

    @GetMapping("/{stateID}/loadPrecincts")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> loadPrecincts(@PathVariable("stateID") String stateID) {
        String precinctsGeoJson = getPrecincts(getStateName(stateID));
        return new ResponseEntity<>(precinctsGeoJson, HttpStatus.OK);
    }


    public String getPrecincts(StateName state) {
        FeatureCollectionJSON geoJson = new FeatureCollectionJSON();
        ArrayList<Precinct> allPrecincts = new ArrayList<>(precinctService.findByState(state));
        for (Precinct precinct : allPrecincts) {
            geoJson.put(new JSONObject(precinct.getGeoJson()));
        }
        return geoJson.toString();
    }

}
