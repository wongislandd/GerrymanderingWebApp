package cse416.spring.controllers;

import cse416.spring.enums.MinorityPopulation;
import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.analysis.CloseToEnacted;
import cse416.spring.helperclasses.analysis.HighScoringMajorityMinority;
import cse416.spring.helperclasses.analysis.TopAreaPairDeviation;
import cse416.spring.helperclasses.analysis.TopScoring;
import cse416.spring.models.districting.Districting;
import cse416.spring.helperclasses.DistrictingConstraints;
import cse416.spring.service.DistrictingService;
import cse416.spring.service.PrecinctService;
import cse416.spring.singletons.PrecinctHashSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    @GetMapping(value = "/{state}/load/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> loadDistricting(@PathVariable("state") StateName state, @PathVariable("id") long id) throws IOException {
        Districting districting = districtingService.findById(id);
        String geoJson = districting.getDistrictingGeoJson(PrecinctHashSingleton.getPrecinctHash(state));
        return new ResponseEntity<>(geoJson, HttpStatus.OK);
    }




    @GetMapping(value = "/getSummary")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> getInterestingDistrictings() {
        // TODO: Implement
        ArrayList<Districting> allDistrictings = new ArrayList<>();
        // ^ ITERATE THROUGH THIS LIST OF DISTRICTINGS AND GET THE 4 CATEGORIES
        CloseToEnacted closeToEnacted = new CloseToEnacted();
        HighScoringMajorityMinority highScoringMajorityMinority = new HighScoringMajorityMinority(MinorityPopulation.BLACK, 3, 6);
        TopAreaPairDeviation topAreaPairDeviation = new TopAreaPairDeviation();
        TopScoring topScoring = new TopScoring();
        for (int i=0;i<allDistrictings.size();i++) {
            Districting currentDistricting = allDistrictings.get(i);
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

    /**
     * Build the response entity to send in case of an error.
     *
     * @param ex The exception object
     * @return A response entity with a CONFLICT HTTP status.
     */
    private static ResponseEntity<String> buildErrorResponseEntity(Exception ex) {
        String body = "{\"message\":\"" + ex.getMessage() + "\"}";
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/load")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> loadDistricting(@RequestParam String id) {
        try {
            /* Interpret, load, and return */
            String filePath = "src/main/resources/static/json/EnactedDistrictingPlan2011WithData.json";
            File file = ResourceUtils.getFile(filePath);
            String content = new String(Files.readAllBytes(file.toPath()));

            // Build a districting object from the id and then return it
            return new ResponseEntity<>(content, HttpStatus.OK);
        } catch (Exception ex) {
            return buildErrorResponseEntity(ex);
        }
    }

    @PostMapping("/constrain")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> constrain(@RequestBody DistrictingConstraints constraints) {
        try {
            // Use constraints to filter within the db
            // Construct a set of IDs which match said constraints
            //server.postConstraints()
            // Return the set of IDs
            // TODO: Implement

            return new ResponseEntity<>("{\"message\":\"" + "YUH" + "\"}", HttpStatus.NOT_IMPLEMENTED);
        } catch (Exception ex) {
            return buildErrorResponseEntity(ex);
        }
    }
}
