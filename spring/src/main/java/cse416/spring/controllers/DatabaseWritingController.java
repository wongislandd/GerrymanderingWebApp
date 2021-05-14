package cse416.spring.controllers;

import cse416.spring.database.CountyWriter;
import cse416.spring.database.DistrictingWriter;
import cse416.spring.database.PrecinctWriter;
import cse416.spring.enums.StateName;
import cse416.spring.models.districting.Districting;
import cse416.spring.models.districting.EnactedDistricting;
import cse416.spring.service.DistrictingServiceImpl;
import cse416.spring.singletons.EmfSingleton;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

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

    @PostMapping(value="/writeAll")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> writeAll() throws IOException {
        final long fileStartTime = System.currentTimeMillis();
        PrecinctWriter.persistPrecincts();
        System.out.println("Persisted Precincts");
        CountyWriter.persistCounties();
        System.out.println("Persisted Counties");
        DistrictingWriter.persistEnactedDistrictings();
        System.out.println("Persisted Enacted Districtings");
        DistrictingWriter.persistDistrictings();
        final long fileEndTime = System.currentTimeMillis();
        System.out.println("Wrote the entire database in " + (fileEndTime - fileStartTime) + "ms");
        return new ResponseEntity<>("Written.", HttpStatus.OK);
    }

}
