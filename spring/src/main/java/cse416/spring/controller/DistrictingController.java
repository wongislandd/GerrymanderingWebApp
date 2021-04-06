package cse416.spring.controller;

import cse416.spring.helperclasses.Server;
import cse416.spring.helperclasses.ConstrainedDistrictings;
import cse416.spring.mapping.Mapper;
import cse416.spring.models.Districting;
import cse416.spring.models.DistrictingConstraints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/districting")
public class DistrictingController {
    Server server;
    Mapper mapper;

    @Autowired
    ConstrainedDistrictings currentConstraintedDistrictings;


    public DistrictingController(Server server, Mapper mapper, ConstrainedDistrictings currentConstraintedDistrictings) {
        this.server = server;
        this.mapper = mapper;
        this.currentConstraintedDistrictings = currentConstraintedDistrictings;
    }

    /* Load a particular districting based on ID */
    @GetMapping("/load")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> loadDistricting(@RequestParam String id) {
        try {
            /* Interpret, load, and return */
            File file = ResourceUtils.getFile("src/main/resources/static/json/EnactedDistrictingPlan2011WithData.json");
            String content = new String(Files.readAllBytes(file.toPath()));
            // Build a districting object from the id and then return it

            return new ResponseEntity<>(content, HttpStatus.OK);

        }
        catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>("{\"message\":\""+ex.getMessage()+"\"}", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/constrain")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> constrain(@RequestBody DistrictingConstraints constraints) {
        try {
            // Use constraints to filter within the db
            // Construct a set of IDs which match said constraints

            // Return the set of IDs

            return new ResponseEntity<>("{\"message\":\""+"YUH"+"\"}", HttpStatus.CONFLICT);
        }
        catch (Exception ex) {
            System.out.println(ex);
            return new ResponseEntity<>("{\"message\":\""+ex.getMessage()+"\"}", HttpStatus.CONFLICT);
        }
    }
























    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Mapper getMapper() {
        return mapper;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    public ConstrainedDistrictings getCurrentConstraintedDistrictings() {
        return currentConstraintedDistrictings;
    }

    public void setCurrentConstraintedDistrictings(ConstrainedDistrictings currentConstraintedDistrictings) {
        this.currentConstraintedDistrictings = currentConstraintedDistrictings;
    }
}
