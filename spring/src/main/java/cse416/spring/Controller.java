package cse416.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import cse416.spring.helperclasses.ConstrainedDistrictings;
import cse416.spring.mapping.Mapper;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/districting")
public class Controller {
    Server server;
    Mapper mapper;

    @Autowired
    ConstrainedDistrictings currentConstraintedDistrictings;


    public Controller(Server server, Mapper mapper, ConstrainedDistrictings currentConstraintedDistrictings) {
        this.server = server;
        this.mapper = mapper;
        this.currentConstraintedDistrictings = currentConstraintedDistrictings;
    }

    // Obtain the list of districtings that fit the constraints. For now, it will just
    // return the constraints and a default districting.
    @RequestMapping(
            value = "/load",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> loadDistricting() {
        try {
            File file = ResourceUtils.getFile("src/main/resources/static/json/EnactedDistrictingPlan2011WithData.json");
            String content = new String(Files.readAllBytes(file.toPath()));
            return new ResponseEntity<>(content, HttpStatus.OK);
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
