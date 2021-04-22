package cse416.spring.models.district;

import com.vividsolutions.jts.geom.Geometry;
import cse416.spring.enums.MinorityPopulation;
import cse416.spring.models.precinct.Demographics;
import cse416.spring.models.precinct.Precinct;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;

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

    public District(int districtNumber, ArrayList<Precinct> precincts) {
        //int districtNumber, Demographics demographics, DistrictMeasures measures, JSONArray precinctKeys
        this.districtNumber = districtNumber;
        this.demographics = compileDemographics(precincts);
        MajorityMinorityInfo minorityInfo = compileMinorityInfo(demographics);

        /* Complete the math for these*/
        int splitCounties = calculateSplitCounties(precincts);
        double populationEquality = calculatePopulationEquality(demographics);
        double politicalFairness = calculatePoliticalFairness(demographics);
        Compactness compactness = calculateCompactness();

        this.measures =  new DistrictMeasures(populationEquality, minorityInfo, compactness, politicalFairness, splitCounties);
        this.precinctKeys = new JSONObject().put("precincts", precinctKeys).toString();
    }

    private int calculateSplitCounties(ArrayList<Precinct> precincts) {
        return 5;
    }

    private int calculatePopulationEquality(Demographics d) {
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
        for (int i = 0; i < precincts.size(); i++) {
            Demographics currentPrecinctDemographics = precincts.get(i).getDemographics();
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
        return new Demographics(total_democrats, total_republicans, total_otherParty, total_asian, total_black, total_natives, total_pacific, total_whiteHispanic, total_whiteNonHispanic, total_otherRace, total_TP, total_VAP, total_CVAP);
    }

    private MajorityMinorityInfo compileMinorityInfo(Demographics demographics) {
        return(new MajorityMinorityInfo(
                demographics.isMajorityMinorityDistrict(MinorityPopulation.BLACK),
                demographics.isMajorityMinorityDistrict(MinorityPopulation.HISPANIC),
                demographics.isMajorityMinorityDistrict(MinorityPopulation.ASIAN),
                demographics.isMajorityMinorityDistrict(MinorityPopulation.NATIVE_AMERICAN)));
    }

    private Compactness calculateCompactness() {
        return new Compactness(.5, .6, .7);
    }
}
