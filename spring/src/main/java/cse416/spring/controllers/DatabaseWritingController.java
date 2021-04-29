package cse416.spring.controllers;

import cse416.spring.service.database.CountyWriter;
import cse416.spring.service.database.DistrictingWriter;
import cse416.spring.service.database.PrecinctWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * A controller that provides methods for persisting precincts, counties
 * and districtings into the database.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/db")
public class DatabaseWritingController {

    @PostMapping("/writePrecincts")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> writePrecincts() throws IOException {
        PrecinctWriter.persistPrecincts();
        return new ResponseEntity<>("Written.", HttpStatus.OK);
    }

    @PostMapping("/writeCounties")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> writeCounties() throws IOException {
        CountyWriter.persistCounties();
        return new ResponseEntity<>("Written.", HttpStatus.OK);
    }

    @PostMapping("/writeEnacted")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> writeEnactedDistrictings() throws IOException {
        DistrictingWriter.persistEnactedDistrictings();
        return new ResponseEntity<>("Written.", HttpStatus.OK);
    }


    @PostMapping("/writeDistrictings")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> writeDistrictings() throws IOException {
        DistrictingWriter.persistDistrictings();
        return new ResponseEntity<>("Written.", HttpStatus.OK);
    }

    @PostMapping("/writeAll")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> writeAll() throws IOException {
        final long fileStartTime = System.currentTimeMillis();
        PrecinctWriter.persistPrecincts();
        CountyWriter.persistCounties();
        DistrictingWriter.persistEnactedDistrictings();
        DistrictingWriter.persistDistrictings();
        final long fileEndTime = System.currentTimeMillis();
        System.out.println("Wrote the entire database in " + (fileEndTime - fileStartTime) + "ms");
        return new ResponseEntity<>("Written.", HttpStatus.OK);
    }
}
