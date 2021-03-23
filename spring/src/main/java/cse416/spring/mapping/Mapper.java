package cse416.spring.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Mapper {
    ObjectMapper objectMapper = new ObjectMapper();

    public Mapper() {

    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
