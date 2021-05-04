package cse416.spring.controllers;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.builders.GeoJsonBuilder;
import cse416.spring.models.precinct.Precinct;
import cse416.spring.service.PrecinctService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/precincts")
public class PrecinctController {
    private final PrecinctService precinctService;

    public PrecinctController(PrecinctService service) {
        this.precinctService = service;
    }

    @GetMapping(value="/{state}/loadPrecincts", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> loadPrecincts(@PathVariable("state") StateName state) throws IOException {
        Collection<Precinct> allPrecincts = precinctService.findByState(state);
        String geoJson = new GeoJsonBuilder().buildPrecincts(allPrecincts).toString();
        return new ResponseEntity<>(geoJson, HttpStatus.OK);
    }
}
