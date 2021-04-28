package cse416.spring.controllers;

import com.github.javafaker.Faker;
import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.FeatureCollectionJSON;
import cse416.spring.models.job.Job;
import cse416.spring.models.precinct.Incumbent;
import cse416.spring.service.StateService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/states")
public class StateController {
    private StateService service;

    @Autowired
    public StateController(StateService service) {
        this.service = service;
    }


    private static Map<String, String> getAllStatesCountyGeometry() {
        Map<String, String> countiesByState = new HashMap<String, String>();
        try {
            File NC = ResourceUtils.getFile("src/main/resources/static/json/NC/NCBoundary.json");
            File LA = ResourceUtils.getFile("src/main/resources/static/json/LA/LABoundary.json");
            File TX = ResourceUtils.getFile("src/main/resources/static/json/TX/TXBoundary.json");
            countiesByState.put("NC", new String(Files.readAllBytes(NC.toPath())).replaceAll("\\n",""));
            countiesByState.put("LA", new String(Files.readAllBytes(LA.toPath())).replaceAll("\\n",""));
            countiesByState.put("TX", new String(Files.readAllBytes(TX.toPath())).replaceAll("\\n",""));
            return countiesByState;
        } catch (Exception ex) {
            return countiesByState;
        }
    }

    public static StateName getStateName(String stateID) {
        switch (stateID) {
            case "LA":
                return StateName.LOUISIANA;
            case "TX":
                return StateName.TEXAS;
            default:
                return StateName.NORTH_CAROLINA;
        }
    }

    @GetMapping("/getOutlines")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> getOutlines() {
        Map<String, String> outlineMap = getAllStatesCountyGeometry();
        return new ResponseEntity<>(new JSONObject(outlineMap).toString(), HttpStatus.OK);
    }


}
