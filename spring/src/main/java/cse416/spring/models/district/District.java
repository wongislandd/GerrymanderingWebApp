package cse416.spring.models.district;

import com.vividsolutions.jts.geom.Geometry;
import cse416.spring.models.precinct.Demographics;
import cse416.spring.models.precinct.Precinct;
import org.hibernate.annotations.Type;
import org.hibernate.spatial.JTSGeometryType;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class District {
    private long id;

    Demographics demographics;

    Geometry geometry;

    Collection<Precinct> precincts;

    DistrictMeasures measures;

    double objectiveFunctionScore;

    public District() {

    }


    /**
     * Calculate the difference between this district and another district
     * Used for deviation from enacted and deviation from average, just pass their demographics in here.
     * @param other
     * @return
     */
    public double calculateDeviationFrom(District other) {
        return 1;
    }


    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Transient
    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    // this means that if u call persist on a district then it'll also persist the
    // precincts, i think it doesn't do it if the precinct already exists but MAYBE not
    // maybe it does do it
    @ManyToMany(cascade = CascadeType.PERSIST)
    public Collection<Precinct> getPrecincts() {
        return precincts;
    }

    public void setPrecincts(Collection<Precinct> precincts) {
        this.precincts = precincts;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public DistrictMeasures getMeasures() {
        return measures;
    }

    public void setMeasures(DistrictMeasures measures) {
        this.measures = measures;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public Demographics getDemographics() {
        return this.demographics;
    }

    public void setDemographics(Demographics demographics) {
        this.demographics = demographics;
    }

    @Transient
    public double getObjectiveFunctionScore() {
        return this.objectiveFunctionScore;
    }

    public void setObjectiveFunctionScore(double objectiveFunctionScore) {
        this.objectiveFunctionScore = objectiveFunctionScore;
    }

    public District(Demographics demographics, Collection<Precinct> precincts, DistrictMeasures measures) {
        this.demographics = demographics;
        this.precincts = precincts;
        this.measures = measures;
    }

}
