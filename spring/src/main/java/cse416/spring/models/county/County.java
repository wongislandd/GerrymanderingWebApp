package cse416.spring.models.county;

import com.vividsolutions.jts.geom.Geometry;
import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.JSONObjectConverter;
import cse416.spring.models.precinct.Precinct;
import org.hibernate.annotations.Type;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class County {
    int id;
    String name;
    Geometry geometry;

    // Have county store an array of precinct IDs? Shouldn't store whole object,
    // or should it?
    Collection<Precinct> precincts;

    public County() {

    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Lob
    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public Collection<Precinct> getPrecincts() {
        return precincts;
    }

    public void setPrecincts(Collection<Precinct> precincts) {
        this.precincts = precincts;
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public County(int id, String name, Collection<Precinct> precincts, Geometry geometry) {
        this.id = id;
        this.name = name;
        this.precincts = precincts;
        this.geometry = geometry;
    }


}
