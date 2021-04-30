package cse416.spring.models.county;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.builders.UnionBuilder;
import cse416.spring.models.precinct.Precinct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;
import java.util.ArrayList;


@Entity(name = "Counties")
@Getter
@Setter
@NoArgsConstructor
public class County {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String name;
    @Lob
    private Geometry geometry;
    @Column
    private StateName state;

    public County(StateName state, int id, String name, ArrayList<Precinct> precincts) {
        this.state = state;
        this.id = id;
        this.name = name;

        JSONArray precinctKeys = new JSONArray();
        for (Precinct precinct : precincts) {
            precinctKeys.put(precinct.getId());
        }
        this.geometry = UnionBuilder.getUnion(precincts);
    }
}
