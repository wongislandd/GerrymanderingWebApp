package cse416.spring.models.district;

import com.vividsolutions.jts.geom.Geometry;
import cse416.spring.enums.MinorityPopulation;
import cse416.spring.models.precinct.Demographics;
import cse416.spring.models.precinct.Precinct;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class District {

    private long id;
    private int districtNumber;
    private Demographics demographics;
    private String precinctKeys;
    private DistrictMeasures measures;
    private double objectiveFunctionScore;

    public District() {
    }

    public District(int districtNumber, ArrayList<Precinct> precincts) {
        this.districtNumber = districtNumber;
        this.demographics = compileDemographics(precincts);
        this.precinctKeys = new JSONObject().put("precincts", precinctKeys).toString();

        /* TODO: Complete the math for these*/
        double populationEquality = this.calculatePopulationEquality();
        MajorityMinorityInfo minorityInfo = compileMinorityInfo(demographics);
        Compactness compactness = calculateCompactness();
        double politicalFairness = calculatePoliticalFairness(demographics);
        int splitCounties = calculateSplitCounties(precincts);

        this.measures = new DistrictMeasures(populationEquality, minorityInfo, compactness, politicalFairness, splitCounties);
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

    public double calculateDeviationFrom(District other) {
        return 1;
    }

    private int calculateSplitCounties(ArrayList<Precinct> precincts) {
        return 5;
    }

    private int calculatePopulationEquality() {
        return 5;
    }

    private int calculatePoliticalFairness(Demographics d) {
        return 5;
    }

    private int calculateDeviationFromEnacted(Geometry hull, Demographics d) {
        return 5;
    }

    private int calculateDeviationFromAverage(Geometry hull, Demographics d) {
        return 5;
    }

    private Compactness calculateCompactness() {
        return new Compactness(.5, .6, .7);
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
