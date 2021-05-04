package cse416.spring.controllers;

import cse416.spring.enums.MinorityPopulation;
import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.DistrictingConstraints;
import cse416.spring.helperclasses.InterestingDistrictingAnalysis;
import cse416.spring.helperclasses.analysis.CloseToEnacted;
import cse416.spring.helperclasses.analysis.HighScoringMajorityMinority;
import cse416.spring.helperclasses.analysis.TopAreaPairDeviation;
import cse416.spring.helperclasses.analysis.TopScoring;
import cse416.spring.models.districting.Districting;
import cse416.spring.models.districting.EnactedDistricting;
import cse416.spring.service.DistrictingService;
import cse416.spring.service.PrecinctService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/districtings")
public class DistrictingController {
    DistrictingService districtingService;
    PrecinctService precinctService;

    @Autowired
    public DistrictingController(DistrictingService districtingService, PrecinctService precinctService) {
        this.districtingService = districtingService;
        this.precinctService = precinctService;
    }

    @GetMapping(value = "/load/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> loadDistricting(@PathVariable("id") long id) throws IOException {
        Districting districting = districtingService.findById(id);
        String geoJson = districting.getGeoJson();
        return new ResponseEntity<>(geoJson, HttpStatus.OK);
    }

    @GetMapping(value = "/{state}/enacted/load")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> loadEnacted(@PathVariable("state") StateName state) throws IOException {
        EnactedDistricting enacted = districtingService.findEnactedByState(state);
        String geoJson = enacted.getGeoJson();
        return new ResponseEntity<>(geoJson, HttpStatus.OK);
    }

    @GetMapping(value = "/getSummary")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> getInterestingDistrictings() {
        // TODO: Implement sorting hat
        ArrayList<Districting> allDistrictings = new ArrayList<>();
        // ^ ITERATE THROUGH THIS LIST OF DISTRICTINGS AND GET THE 4 CATEGORIES
        CloseToEnacted closeToEnacted = new CloseToEnacted();
        HighScoringMajorityMinority highScoringMajorityMinority = new HighScoringMajorityMinority(MinorityPopulation.BLACK, 3, 6, 0.3);
        TopAreaPairDeviation topAreaPairDeviation = new TopAreaPairDeviation();
        TopScoring topScoring = new TopScoring();
        for (Districting currentDistricting : allDistrictings) {
            if (closeToEnacted.shouldInsert(currentDistricting)) {
                closeToEnacted.insert(currentDistricting);
            }
            if (highScoringMajorityMinority.shouldInsert(currentDistricting)) {
                highScoringMajorityMinority.insert(currentDistricting);
            }
            if (topAreaPairDeviation.shouldInsert(currentDistricting)) {
                topAreaPairDeviation.insert(currentDistricting);
            }
            if (topScoring.shouldInsert(currentDistricting)) {
                topScoring.insert(currentDistricting);
            }
        }
        // RESULTS
        ArrayList<Districting> r1 = closeToEnacted.getEntries();
        ArrayList<Districting> r2 = highScoringMajorityMinority.getEntries();
        ArrayList<Districting> r3 = topAreaPairDeviation.getEntries();
        ArrayList<Districting> r4 = topScoring.getEntries();
        System.out.println("Put breakpoint here to view the listings after");
        return new ResponseEntity<>("hi", HttpStatus.OK);
    }

    @PostMapping(path="/constrain", consumes="application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<InterestingDistrictingAnalysis> constrain(@RequestBody DistrictingConstraints constraints) {
            // TODO: Implement the constraining
            TopScoring topScoring = new TopScoring();
            HighScoringMajorityMinority highScoringMajorityMinority = new HighScoringMajorityMinority(constraints.getMinorityPopulation(), constraints.getMinMinorityDistricts(), 99, constraints.getMinorityThreshold());
            TopAreaPairDeviation topAreaPairDeviation = new TopAreaPairDeviation();
            CloseToEnacted closeToEnacted = new CloseToEnacted();
            topScoring.forceInsert(districtingService.findById(6038));
            InterestingDistrictingAnalysis analysis = new InterestingDistrictingAnalysis(topScoring, closeToEnacted, highScoringMajorityMinority, topAreaPairDeviation);
            return new ResponseEntity<>(analysis, HttpStatus.OK);
    }
}
