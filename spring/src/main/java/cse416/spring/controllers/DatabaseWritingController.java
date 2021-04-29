package cse416.spring.controllers;

import cse416.spring.service.CountyService;
import cse416.spring.service.database.DatabaseWritingService;
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

    private final DatabaseWritingService databaseWritingService;

    public DatabaseWritingController(DatabaseWritingService service) {
        this.databaseWritingService = service;
    }

    @PostMapping("/writePrecincts")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> writePrecincts() throws IOException {
        DatabaseWritingService.persistPrecincts();
        return new ResponseEntity<>("Written.", HttpStatus.OK);
    }

    @PostMapping("/writeCounties")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> writeCounties() throws IOException {
        DatabaseWritingService.persistCounties();
        return new ResponseEntity<>("Written.", HttpStatus.OK);
    }

    @PostMapping("/writeDistrictings")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> writeDistrictings() throws IOException {
        DatabaseWritingService.persistDistrictings();
        return new ResponseEntity<>("Written.", HttpStatus.OK);
    }

    @PostMapping("/writeAll")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> writeAll() throws IOException {
        DatabaseWritingService.persistPrecincts();
        DatabaseWritingService.persistCounties();
        DatabaseWritingService.persistDistrictings();
        return new ResponseEntity<>("Written.", HttpStatus.OK);
    }
}
