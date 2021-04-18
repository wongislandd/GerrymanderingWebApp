package cse416.spring.controller;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.MGGGParams;
import cse416.spring.models.Districting;
import cse416.spring.models.Job;
import cse416.spring.models.JobSummary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/db")
public class DatabaseWritingController {
    @PostMapping("/write")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> loadDistricting() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );

        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );

        Job j = new Job(StateName.NORTH_CAROLINA, new JobSummary("Fake job", 100000, new MGGGParams(50, .05)), new ArrayList<Districting>());
        entitymanager.persist(j);
        entitymanager.getTransaction( ).commit( );

        entitymanager.close( );
        emfactory.close( );
        return new ResponseEntity<>("Written.", HttpStatus.OK);
    }
}
