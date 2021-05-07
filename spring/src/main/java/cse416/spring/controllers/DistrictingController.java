package cse416.spring.controllers;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.*;
import cse416.spring.helperclasses.analysis.CloseToEnacted;
import cse416.spring.helperclasses.analysis.HighScoringMajorityMinority;
import cse416.spring.helperclasses.analysis.TopAreaPairDeviation;
import cse416.spring.helperclasses.analysis.TopScoring;
import cse416.spring.models.districting.Districting;
import cse416.spring.models.districting.EnactedDistricting;
import cse416.spring.service.DistrictingService;
import cse416.spring.service.PrecinctService;
import cse416.spring.singletons.DistrictingsSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

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

    @GetMapping(value = "/load/{id}", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> loadDistricting(@PathVariable("id") long id) throws IOException {
        Districting districting = districtingService.findById(id);
        String geoJson = districting.getGeoJson();
        return new ResponseEntity<>(geoJson, HttpStatus.OK);
    }

    @GetMapping(value = "/{state}/enacted/load", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> loadEnacted(@PathVariable("state") StateName state) throws IOException {
        EnactedDistricting enacted = districtingService.findEnactedByState(state);
        String geoJson = enacted.getGeoJson();
        return new ResponseEntity<>(geoJson, HttpStatus.OK);
    }

    @GetMapping(value = "/{state}/enacted/summary")
    public ResponseEntity<DistrictingSummary> loadEnactedSummary(@PathVariable("state") StateName state) throws IOException {
        EnactedDistricting enacted = districtingService.findEnactedByState(state);
        DistrictingSummary summary = enacted.getSummary();
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }

    @PostMapping(path = "/constrain", consumes = "application/json")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<Integer> constrain(HttpServletRequest request, @RequestBody DistrictingConstraints constraints) {
        Collection<Districting> results = DistrictingsSingleton.getDistrictingsByConstraints(constraints);
        if (results.size() > 0) {
            ConstrainedDistrictings cds = new ConstrainedDistrictings(results, constraints);
            request.getSession().setAttribute("constrainedDistrictings", cds);
        }
        return new ResponseEntity<>(results.size(), HttpStatus.OK);
    }


    @PostMapping(path = "/applyWeights", consumes = "application/json")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<InterestingDistrictingAnalysis> applyWeight(HttpServletRequest request, @RequestBody ObjectiveFunctionWeights weights) {
        ConstrainedDistrictings cds = (ConstrainedDistrictings) request.getSession().getAttribute("constrainedDistrictings");
        cds.setCurrentWeights(weights);
        for (Districting d : cds.getDistrictings()) {
            d.assignObjectiveFunctionScores(weights);
        }
        request.getSession().setAttribute("constrainedDistrictings", cds);
        DistrictingConstraints constraints = cds.getConstraints();

        TopScoring topScoring = new TopScoring();
        HighScoringMajorityMinority highScoringMajorityMinority = new HighScoringMajorityMinority(constraints.getMinorityPopulation(), constraints.getMinMinorityDistricts(), 99, constraints.getMinorityThreshold());
        TopAreaPairDeviation topAreaPairDeviation = new TopAreaPairDeviation();
        CloseToEnacted closeToEnacted = new CloseToEnacted();
        for (Districting d : cds.getDistrictings()) {
            if (topScoring.shouldInsert(d)) {
                topScoring.insert(d);
            }
        }
        InterestingDistrictingAnalysis analysis = new InterestingDistrictingAnalysis(topScoring, closeToEnacted, highScoringMajorityMinority, topAreaPairDeviation);
        return new ResponseEntity<>(analysis, HttpStatus.OK);
    }

    @GetMapping(path = "/getBoxAndWhisker")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<String> getBoxAndWhisker(HttpServletRequest request) {
        ConstrainedDistrictings cds = (ConstrainedDistrictings) request.getSession().getAttribute("constrainedDistrictings");
        cds.getBoxAndWhiskerData();
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}