package cse416.spring.controllers;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.FeatureCollectionJSON;
import cse416.spring.models.county.County;
import cse416.spring.service.CountyService;
import cse416.spring.service.PrecinctService;
import cse416.spring.service.StateService;
import org.locationtech.jts.geom.Geometry;
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
@RequestMapping("/counties")
public class CountyController {
    private CountyService countyService;

    @Autowired
    public CountyController(CountyService service) {
        this.countyService = service;
    }

    @GetMapping("/{stateID}/loadCounties")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> loadCounties(@PathVariable("stateID") String stateID) {
        String countiesGeoJson = getCounties(getStateName(stateID));
        return new ResponseEntity<>(countiesGeoJson, HttpStatus.OK);
    }

    private String getCounties(StateName state) {
        ArrayList<County> allCounties = new ArrayList<>(countyService.findByStateName(state));
        ArrayList<Geometry> geometries = new ArrayList<>();
        for (County county : allCounties) {
            geometries.add(county.getGeometry());
        }
        FeatureCollectionJSON geoJson = new FeatureCollectionJSON(geometries);
        return geoJson.toString();
    }
}