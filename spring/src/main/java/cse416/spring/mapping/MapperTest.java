package cse416.spring.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MapperTest {
    public static void main(String[] args) throws IOException {
        Mapper mapper = new Mapper();
        ObjectMapper objectMapper = mapper.getObjectMapper();

//        List<District> dummyDistricts = Arrays.asList(new District(0, ));
//        Districting districting = new Districting(5, dummyDistricts);
//        objectMapper.writeValue(new File("src/data/districting.json"), districting);*/
    }
}
