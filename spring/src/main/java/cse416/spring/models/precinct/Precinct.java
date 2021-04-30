package cse416.spring.models.precinct;

import cse416.spring.enums.StateName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;

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

    public JSONArray retrieveCoordinates() {
        return new JSONObject(geoJson).getJSONObject("geometry")
                .getJSONArray("coordinates").getJSONArray(0);
    }
}
