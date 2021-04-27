package cse416.spring.models.district;

import com.vividsolutions.jts.geom.Geometry;
import cse416.spring.enums.MinorityPopulation;
import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.constants.IdealPopulation;
import cse416.spring.models.precinct.Demographics;
import cse416.spring.models.precinct.Precinct;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * A class to represent a congressional district.
 */
@Entity
public class District {
    private int districtNumber;
    private Demographics demographics;
    private String precinctKeys;
    private DistrictMeasures measures;
    private double objectiveFunctionScore;
    private long id;

    public District() {
    }

    public District(int districtNumber, ArrayList<Precinct> precincts, StateName stateName) {
        this.districtNumber = districtNumber;
        this.demographics = compileDemographics(precincts);
        JSONArray precinctKeysArr = new JSONArray();
        for (Precinct p : precincts) {
            precinctKeysArr.put(p.getId());
        }
        this.precinctKeys = new JSONObject().put("precincts", precinctKeysArr).toString();

        /* TODO: Complete the math for these*/
        int idealPopulation = IdealPopulation.getIdealPopulation(stateName);
        double populationEquality = this.calculatePopulationEquality(idealPopulation);

        MajorityMinorityInfo minorityInfo = compileMinorityInfo(demographics);
        Compactness compactness = calculateCompactness();
        double politicalFairness = calculatePoliticalFairness(demographics);
        int splitCounties = calculateSplitCounties(precincts);

        this.measures = new DistrictMeasures(populationEquality, minorityInfo,
                compactness, politicalFairness, splitCounties);
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

    @Lob
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

    /* Temporary helper functions for generating fake data */
    private int generateRandInt(int min, int max) {
        return ((int) Math.floor(Math.random() * (max - min + 1) + min));
    }

    private double generateRandDouble(int min, int max) {
        return (double) Math.round((Math.random() * (max - min + 1) + min) * 100) / 100;
    }

    // Methods to calculate district measures

    public double calculateDeviationFrom(District other) {
        return Math.random();
    }

    private int calculateSplitCounties(ArrayList<Precinct> precincts) {
        return 5;
    }

    private double calculatePopulationEquality(int idealPopulation) {
        double popRatio = (double) demographics.getTP() / idealPopulation;
        return Math.pow((popRatio - 1), 2);
    }

    private double calculatePoliticalFairness(Demographics d) {
        return Math.random();
    }

    private double calculateDeviationFromEnacted(Geometry hull, Demographics d) {
        return Math.random();
    }

    private double calculateDeviationFromAverage(Geometry hull, Demographics d) {
        return Math.random();
    }

    private Compactness calculateCompactness() {
        return new Compactness(Math.random(), Math.random(), Math.random());
    }

    private static Demographics compileDemographics(ArrayList<Precinct> precincts) {
        int total_democrats = 0;
        int total_republicans = 0;
        int total_otherParty = 0;
        int total_asian = 0;
        int total_black = 0;
        int total_natives = 0;
        int total_pacific = 0;
        int total_whiteHispanic = 0;
        int total_whiteNonHispanic = 0;
        int total_otherRace = 0;
        int total_TP = 0;
        int total_VAP = 0;
        int total_CVAP = 0;

        for (Precinct precinct : precincts) {
            Demographics currentPrecinctDemographics = precinct.getDemographics();
            total_democrats += currentPrecinctDemographics.getDemocrats();
            total_republicans += currentPrecinctDemographics.getRepublicans();
            total_otherParty += currentPrecinctDemographics.getOtherParty();
            total_asian += currentPrecinctDemographics.getAsian();
            total_black += currentPrecinctDemographics.getBlack();
            total_natives += currentPrecinctDemographics.getNatives();
            total_pacific += currentPrecinctDemographics.getPacific();
            total_whiteHispanic += currentPrecinctDemographics.getWhiteHispanic();
            total_whiteNonHispanic += currentPrecinctDemographics.getWhiteNonHispanic();
            total_otherRace += currentPrecinctDemographics.getOtherRace();
            total_TP += currentPrecinctDemographics.getTP();
            total_VAP += currentPrecinctDemographics.getVAP();
            total_CVAP += currentPrecinctDemographics.getCVAP();
        }

        return new Demographics(total_democrats, total_republicans, total_otherParty, total_asian, total_black,
                total_natives, total_pacific, total_whiteHispanic, total_whiteNonHispanic, total_otherRace,
                total_TP, total_VAP, total_CVAP);
    }

    private MajorityMinorityInfo compileMinorityInfo(Demographics demographics) {
        return (new MajorityMinorityInfo(
                demographics.isMajorityMinorityDistrict(MinorityPopulation.BLACK),
                demographics.isMajorityMinorityDistrict(MinorityPopulation.HISPANIC),
                demographics.isMajorityMinorityDistrict(MinorityPopulation.ASIAN),
                demographics.isMajorityMinorityDistrict(MinorityPopulation.NATIVE_AMERICAN)));
    }
}
