package cse416.spring.controllers;

import com.github.javafaker.Faker;
import cse416.spring.enums.StateName;
import cse416.spring.models.precinct.Incumbent;
import cse416.spring.service.IncumbentService;
import cse416.spring.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static cse416.spring.controllers.StateController.getStateName;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/incumbents")
public class IncumbentController {
    private IncumbentService incumbentService;

    @Autowired
    public IncumbentController(IncumbentService service) {
        this.incumbentService = service;
    }

    // TODO Load incumbents into DB and actually query them
    private static ArrayList<Incumbent> getIncumbents(StateName state) {
        int numIncumbents = 5;
        Faker faker = new Faker();
        ArrayList<Incumbent> fakePeople = new ArrayList<Incumbent>();
        for (int i=0; i<numIncumbents;i++) {
            fakePeople.add(new Incumbent(faker.gameOfThrones().character(), StateName.NORTH_CAROLINA, faker.gameOfThrones().city()));
        }
        return fakePeople;
    }

    @GetMapping("/{stateID}/loadIncumbents")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ArrayList<Incumbent>> loadIncumbents(@PathVariable("stateID") String stateID) {
        ArrayList<Incumbent> incumbents = getIncumbents(getStateName(stateID));
        return new ResponseEntity<>(incumbents, HttpStatus.OK);
    }

}