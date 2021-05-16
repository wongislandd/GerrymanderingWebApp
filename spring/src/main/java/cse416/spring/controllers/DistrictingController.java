package cse416.spring.controllers;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.*;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        String geoJson = districting.getGeoJson().toString();
        return new ResponseEntity<>(geoJson, HttpStatus.OK);
    }

    @GetMapping(value = "/{state}/enacted/load", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> loadEnacted(@PathVariable("state") StateName state) throws IOException {
        EnactedDistricting enacted = districtingService.findEnactedByState(state);
        String geoJson = enacted.getGeoJson();
        return new ResponseEntity<>(geoJson, HttpStatus.OK);
    }

    @GetMapping(value = "/{state}/enacted/summary", produces = "application/json")
    public ResponseEntity<DistrictingSummary> loadEnactedSummary(@PathVariable("state") StateName state) throws IOException {
        EnactedDistricting enacted = districtingService.findEnactedByState(state);
        DistrictingSummary summary = enacted.getSummary();
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }


    @PostMapping(path = "/constrain", consumes = "application/json")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<Integer> constrain(HttpServletRequest request, @RequestBody DistrictingConstraints constraints) throws IOException {
        System.out.println("Constrian called.");
        Collection<Districting> results = DistrictingsSingleton.getDistrictingsByConstraints(constraints);
        if (results.size() > 0) {
            ConstrainedDistrictings cds = new ConstrainedDistrictings(results, constraints);
            cds.calculateNormalizedMeasures();
            request.getSession().setAttribute("constrainedDistrictings", cds);
        }
        System.out.println("Returning " + results.size());
        return new ResponseEntity<>(results.size(), HttpStatus.OK);
    }


    @PostMapping(path = "/applyWeights", consumes = "application/json")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<List<DistrictingSummary>> applyWeight(HttpServletRequest request, @RequestBody ObjectiveFunctionWeights weights) {
        System.out.println("Apply weight called.");
        ConstrainedDistrictings cds = (ConstrainedDistrictings) request.getSession().getAttribute("constrainedDistrictings");
        cds.setCurrentWeights(weights);
        request.getSession().setAttribute("constrainedDistrictings", cds);
        DistrictingConstraints constraints = cds.getConstraints();
        ArrayList<DistrictingSummary> summaries = new ArrayList<>();
        for (Districting districting : cds.getDistrictings()) {
            summaries.add(new DistrictingSummary(districting));
        }
        TopScoring topScoring = new TopScoring();
        for (DistrictingSummary summary : summaries) {
            summary.calculateNormalizedObjectiveFunctionScore(weights);
            topScoring.insertIfFit(summary);
        }

        // Calculate area pair deviation
        for (DistrictingSummary summary : topScoring.getEntries()) {
            summary.setAreaPairDeviation(summaries);
        }

        InterestingDistrictingAnalysis analysis = new InterestingDistrictingAnalysis(topScoring, constraints);

        return new ResponseEntity<>(analysis.getSummaries(), HttpStatus.OK);
    }

    @GetMapping(path = "/getBoxAndWhisker")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<List<List<Double>>> getBoxAndWhisker(HttpServletRequest request) {
        ConstrainedDistrictings cds = (ConstrainedDistrictings) request.getSession().getAttribute("constrainedDistrictings");
        List<List<Double>> bw = cds.getBoxAndWhiskerData();
        return new ResponseEntity<>(bw, HttpStatus.OK);
    }

    @GetMapping(path = "getMinorityPoints/{id}")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<List<Double>> getMinorityPointData(HttpServletRequest request, @PathVariable("id") long id) {
        ConstrainedDistrictings cds = (ConstrainedDistrictings) request.getSession().getAttribute("constrainedDistrictings");
        Districting targetDistricting = cds.findDistrictingById(id);
        List<Double> pointData = targetDistricting.getMinorityPointData(cds.getConstraints().getMinorityPopulation());
        return new ResponseEntity<>(pointData, HttpStatus.OK);
    }

    @GetMapping(value = "/{state}/enacted/getMinorityPoints")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<List<Double>> getEnactedPointData(HttpServletRequest request, @PathVariable("state") StateName state) throws IOException {
        ConstrainedDistrictings cds = (ConstrainedDistrictings) request.getSession().getAttribute("constrainedDistrictings");
        EnactedDistricting enacted = districtingService.findEnactedByState(state);
        List<Double> pointData = enacted.getMinorityPointData(cds.getConstraints().getMinorityPopulation());
        return new ResponseEntity<>(pointData, HttpStatus.OK);
    }
}