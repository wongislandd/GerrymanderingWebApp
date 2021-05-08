package cse416.spring.models.county;

import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.builders.UnionBuilder;
import cse416.spring.models.precinct.Precinct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;
import java.util.Collection;


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
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Collection<Precinct> precincts;
    @Lob
    private Geometry geometry;
    @Column
    private StateName state;

    public County(StateName state, String name, Collection<Precinct> precincts) {
        this.state = state;
        this.name = name;
        this.precincts = precincts;

        JSONArray precinctKeys = new JSONArray();
        for (Precinct precinct : precincts) {
            precinctKeys.put(precinct.getId());
        }
        this.geometry = UnionBuilder.getUnion(precincts);
    }
}
