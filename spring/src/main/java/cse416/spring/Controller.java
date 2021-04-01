package cse416.spring;

import cse416.spring.helperclasses.ConstrainedDistrictings;
import cse416.spring.mapping.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:8080")
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
    @PostMapping("/load")
    @CrossOrigin(origins = "http://localhost:8080")
    public String loadDistrictings() {
        return "Hello!...";
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
