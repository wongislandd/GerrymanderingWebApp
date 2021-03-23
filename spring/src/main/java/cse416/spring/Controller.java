package cse416.spring;

import cse416.spring.helperclasses.ConstrainedDistrictings;
import cse416.spring.mapping.Mapper;

public class Controller {
    Server server;
    Mapper mapper;
    ConstrainedDistrictings currentConstraintedDistrictings;

    public Controller(Server server, Mapper mapper, ConstrainedDistrictings currentConstraintedDistrictings) {
        this.server = server;
        this.mapper = mapper;
        this.currentConstraintedDistrictings = currentConstraintedDistrictings;
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
