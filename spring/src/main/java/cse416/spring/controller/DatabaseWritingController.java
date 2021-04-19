package cse416.spring.controller;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.MGGGParams;
import cse416.spring.models.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/db")
public class DatabaseWritingController {

    public static String readFile(String filePath) throws IOException {
        File file = ResourceUtils.getFile("src/main/resources/static/" + filePath);
        String content = new String(Files.readAllBytes(file.toPath()));
        return content;
    }

    public static void persistPrecincts(EntityManager em) throws IOException {
        String content = readFile("/json/NC/PrecinctGeoDataSimplified.json");
        JSONObject j = new JSONObject(content);
        JSONArray features = j.getJSONArray("features");
        for (int i=0;i<features.length();i++) {
            JSONObject feature = features.getJSONObject(i);
            JSONObject properties = feature.getJSONObject("properties");
            String precinctName = properties.getString("PREC_NAME");
            int democrats = properties.getInt("DEM");
            int republicans = properties.getInt("REP");
            int otherParty = properties.getInt("OTHER");
            int asian = properties.getInt("A");
            int black = properties.getInt("B");
            int natives = properties.getInt("I");
            int pacific = properties.getInt("P");
            int whiteHispanic = properties.getInt("WHL");
            int whiteNonHispanic = properties.getInt("WNL");
            int otherRace = properties.getInt("O");
            int TP = -1;
            int VAP = democrats + republicans + otherParty;
            int CVAP = -1;
            int id = properties.getInt("id");
            Demographics d = new Demographics(democrats, republicans, otherParty,asian,black, natives,
                    pacific, whiteHispanic, whiteNonHispanic, otherRace, TP, VAP, CVAP);
            Precinct p = new Precinct(id, precinctName, feature, d);
            em.persist(p);
        }

    }

    @PostMapping("/writePrecincts")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> writeTest() throws IOException {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "orioles_db" );

        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );

        persistPrecincts(entitymanager);

        entitymanager.getTransaction( ).commit( );
        entitymanager.close( );
        emfactory.close( );
        return new ResponseEntity<>("Written.", HttpStatus.OK);
    }


    @PostMapping("writeCounties")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> writeCounties() throws IOException {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "orioles_db" );
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );


        String content = readFile("json/NC/CountiesGeoData.json");





        entitymanager.getTransaction( ).commit( );
        entitymanager.close( );
        emfactory.close( );
        return new ResponseEntity<>("Written.", HttpStatus.OK);
    }
}
