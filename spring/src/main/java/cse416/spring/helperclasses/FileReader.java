package cse416.spring.helperclasses;

import cse416.spring.models.district.DistrictReference;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileReader {
    public static JSONObject readJsonFile(String filePath) throws IOException {
        File file = ResourceUtils.getFile("src/main/resources/static/json" + filePath);
        String content = new String(Files.readAllBytes(file.toPath()));
        return new JSONObject(content);
    }

    public static String[] getFilesInFolder(String directoryPath) throws IOException {
        File dir = ResourceUtils.getFile("src/main/resources/static/" + directoryPath);
        return dir.list();
    }

    public static JSONArray getDistrictsPrecinctsFromJsonFile(DistrictReference districtReference) throws IOException {
        File file = ResourceUtils.getFile("src/main/resources/static/json" + districtReference.getFilePath());
        String content = new String(Files.readAllBytes(file.toPath()));
        JSONObject root = new JSONObject(content);
        JSONArray districtings = root.getJSONArray("districtings");
        JSONObject targetDistricting = districtings.getJSONObject(districtReference.getDistrictingIndex());
        return targetDistricting.getJSONArray(districtReference.getDistrictKey());
    }
}
