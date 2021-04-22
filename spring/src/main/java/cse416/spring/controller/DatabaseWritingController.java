package cse416.spring.controller;

import cse416.spring.helperclasses.EntityManagerSingleton;
import cse416.spring.service.DatabaseWritingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/db")
public class DatabaseWritingController {

    DatabaseWritingService service = new DatabaseWritingService();


    @PostMapping("/writePrecincts")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> writeTest() throws IOException {
        service.persistPrecincts();
        return new ResponseEntity<>("Written.", HttpStatus.OK);
    }


    @PostMapping("/writeCounties")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> writeCounties() throws IOException {
        service.persistCounties();
        return new ResponseEntity<>("Written.", HttpStatus.OK);
    }

    @PostMapping("/writeDistrictings")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> persistDistrictings() throws IOException, InterruptedException {
        service.persistDistrictings();
        return new ResponseEntity<>("Written.", HttpStatus.OK);
    }

    @PostMapping("/persistAll")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> test() throws IOException, InterruptedException {
        service.persistPrecincts();
        service.persistCounties();
        service.persistDistrictings();
        EntityManagerSingleton.getInstance().shutdown();
        return new ResponseEntity<>("Written.", HttpStatus.OK);
    }



}
