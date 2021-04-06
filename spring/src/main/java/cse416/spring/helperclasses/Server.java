package cse416.spring.helperclasses;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Component
public class Server {
    public Map<String, String> getAllStatesCountyGeometry() {
        Map<String, String> countiesByState = new HashMap<String, String>();
        try {
            File NC = ResourceUtils.getFile("src/main/resources/static/json/NC/CountiesGeoData.json");
            File LA = ResourceUtils.getFile("src/main/resources/static/json/LA/CountiesGeoData.json");
            File TX = ResourceUtils.getFile("src/main/resources/static/json/TX/CountiesGeoData.json");
            countiesByState.put("NC", new String(Files.readAllBytes(NC.toPath())).replaceAll("\\n",""));
            countiesByState.put("LA", new String(Files.readAllBytes(LA.toPath())).replaceAll("\\n",""));
            countiesByState.put("TX", new String(Files.readAllBytes(TX.toPath())).replaceAll("\\n",""));
            return countiesByState;
        } catch (Exception ex) {
            return countiesByState;
        }
    }
}
