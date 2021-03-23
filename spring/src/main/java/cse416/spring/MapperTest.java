package cse416.spring;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class MapperTest {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Districting districting = new Districting(5);
        objectMapper.writeValue(new File("src/data/districting.json"), districting);
    }
}
