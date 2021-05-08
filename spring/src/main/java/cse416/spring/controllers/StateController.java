package cse416.spring.controllers;

import cse416.spring.enums.StateName;
import cse416.spring.service.StateService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/states")
public class StateController {
    // TODO: Do we need to use the service in this controller?
    private final StateService service;

    public StateController(StateService service) {
        this.service = service;
    }

    public static StateName getStateName(String stateID) {
        switch (stateID) {
            case "LA":
                return StateName.LOUISIANA;
            case "AL":
                return StateName.ALABAMA;
            default:
                return StateName.NORTH_CAROLINA;
        }
    }

    @GetMapping(value="/getOutlines", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> getOutlines() {
        Map<String, String> outlineMap = getAllStatesCountyGeometry();
        return new ResponseEntity<>(new JSONObject(outlineMap).toString(), HttpStatus.OK);
    }

    private static Map<String, String> getAllStatesCountyGeometry() {
        Map<String, String> countiesByState = new HashMap<>();
        try {
            File NC = ResourceUtils.getFile("src/main/resources/static/json/NC/NCBoundary.json");
            File LA = ResourceUtils.getFile("src/main/resources/static/json/LA/LABoundary.json");
            File AL = ResourceUtils.getFile("src/main/resources/static/json/AL/ALBoundary.json");
            countiesByState.put("NC", new String(Files.readAllBytes(NC.toPath())).replaceAll("\\n",""));
            countiesByState.put("LA", new String(Files.readAllBytes(LA.toPath())).replaceAll("\\n",""));
            countiesByState.put("AL", new String(Files.readAllBytes(AL.toPath())).replaceAll("\\n",""));
            return countiesByState;
        } catch (Exception ex) {
            return countiesByState;
        }
    }
}
