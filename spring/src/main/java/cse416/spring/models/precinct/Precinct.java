package cse416.spring.models.precinct;

import cse416.spring.enums.StateName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Precincts")
@Getter
@Setter
@NoArgsConstructor
public class Precinct {
    @Id
    @GeneratedValue
    private long id;
    @Column
    String name;
    @Column(columnDefinition = "json")
    String geoJson;
    @Column
    private int precinctId;
    @OneToOne(cascade = CascadeType.ALL)
    Demographics demographics;
    @Column
    StateName state;

    public Precinct(StateName state, int id, String name, String geoJson,
                    Demographics demographics) {
        this.state = state;
        this.name = name;
        this.geoJson = geoJson;
        this.demographics = demographics;
        this.precinctId = id;
    }

    private static boolean isMultiPolygon(JSONArray coordinatesJson) {
        try {
            JSONArray firstCoord = coordinatesJson.getJSONArray(0)
                    .getJSONArray(0).getJSONArray(0);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    private static JSONArray getPolygonCoords(JSONArray coordinatesJson) {
        return coordinatesJson.getJSONArray(0);
    }

    public Collection<JSONArray> retrieveCoordinates() {
        JSONArray coordinatesJson = new JSONObject(geoJson).getJSONObject("geometry").getJSONArray("coordinates");
        List<JSONArray> coordinates = new ArrayList<>();

        if (isMultiPolygon(coordinatesJson)) {
            for (int i = 0; i < coordinatesJson.length(); i++)
                coordinates.add(getPolygonCoords(coordinatesJson.getJSONArray(i)));
        } else
            coordinates.add(getPolygonCoords(coordinatesJson));

        return coordinates;
    }
}
