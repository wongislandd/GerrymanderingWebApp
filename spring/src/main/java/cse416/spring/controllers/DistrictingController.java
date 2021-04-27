package cse416.spring.controllers;

import cse416.spring.models.districting.DistrictingConstraints;
import cse416.spring.service.DistrictingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/districtings")
public class DistrictingController {
    public DistrictingController() {
    }

    //GET MAPPING TO https://localhost:3000/districtings/getSummary
    @GetMapping(value = "/getSummary")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> getInterestingDistrictings() {
        // TODO: Implement
        DistrictingService.getInterestingDistrictings();
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

    /* Load a particular districting based on ID */
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
