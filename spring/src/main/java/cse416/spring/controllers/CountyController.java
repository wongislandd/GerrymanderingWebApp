package cse416.spring.controllers;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.builders.GeoJsonBuilder;
import cse416.spring.models.county.County;
import cse416.spring.service.CountyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/counties")
public class CountyController {
    private final CountyService countyService;

    public CountyController(CountyService service) {
        this.countyService = service;
    }

    @GetMapping(value= "/{state}/loadCounties", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> loadCounties(@PathVariable("state") StateName state) throws IOException {
        Collection<County> allCounties = countyService.findByStateName(state);
        String geoJson = new GeoJsonBuilder().buildCounties(allCounties).toString();
        return new ResponseEntity<>(geoJson, HttpStatus.OK);
    }
}