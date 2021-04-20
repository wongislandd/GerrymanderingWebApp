package cse416.spring.controller;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.MGGGParams;
import cse416.spring.models.*;
import cse416.spring.service.DatabaseWritingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

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

    @PostMapping("/test")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> test() throws IOException {
        service.test();
        return new ResponseEntity<>("Written.", HttpStatus.OK);
    }

}
