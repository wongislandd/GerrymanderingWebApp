package cse416.spring.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    ObjectMapper objectMapper = new ObjectMapper();

    public Mapper() {

    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
