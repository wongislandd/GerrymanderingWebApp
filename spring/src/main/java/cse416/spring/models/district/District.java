package cse416.spring.models.district;

import cse416.spring.models.precinct.Demographics;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;

@Entity
public class District {
    private long id;

    int districtNumber;

    Demographics demographics;

    String precinctKeys;

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

    @Column
    public int getDistrictNumber() {
        return districtNumber;
    }

    public void setDistrictNumber(int districtNumber) {
        this.districtNumber = districtNumber;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrecinctKeys() {
        return precinctKeys;
    }

    public void setPrecinctKeys(String precinctKeys) {
        this.precinctKeys = precinctKeys;
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

    public District(int districtNumber, Demographics demographics, DistrictMeasures measures, JSONArray precinctKeys) {
        this.districtNumber = districtNumber;
        this.demographics = demographics;
        this.measures = measures;
        this.precinctKeys = new JSONObject().put("precincts", precinctKeys).toString();
    }

}
