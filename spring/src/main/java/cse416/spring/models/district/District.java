package cse416.spring.models.district;

import cse416.spring.enums.MinorityPopulation;
import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.builders.UnionBuilder;
import cse416.spring.helperclasses.constants.IdealPopulation;
import cse416.spring.models.precinct.Demographics;
import cse416.spring.models.precinct.Precinct;
import org.json.JSONArray;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * A class to represent a congressional district.
 */
@Entity(name = "Districts")
public class District {
    /**
     * District number that corresponds to the district numbering of the
     * enacted districting based on the Gill metric.
     */
    private int districtNumber;
    private Demographics demographics;
    private DistrictMeasures measures;
    private Geometry geometry;
    private DistrictReference districtReference;
    private double objectiveFunctionScore;
    private long id;

    public District() {
    }


    /* filePath = file in districtings folder (ex. nc_plans_1_0.json)
       indexInFile = refers to the districting this district is a part of
       districtNumber = which district it is in that districting
     */
    public District(ArrayList<Precinct> precincts, StateName stateName,
                    District enactedDistrict, DistrictReference districtReference) {
        this.demographics = compileDemographics(precincts);
        this.geometry = UnionBuilder.getUnion(precincts);
        this.districtReference = districtReference;

        JSONArray precinctKeysArr = new JSONArray();
        for (Precinct p : precincts) {
            precinctKeysArr.put(p.getId());
        }
        int idealPopulation = IdealPopulation.getIdealPopulation(stateName);
        double populationEquality = this.calculatePopulationEquality(idealPopulation);
        MajorityMinorityInfo minorityInfo = compileMinorityInfo(demographics);
        Compactness compactness = calculateCompactness(geometry);

        if (enactedDistrict != null) {

        }

        this.measures = new DistrictMeasures(populationEquality, minorityInfo,
                compactness);
    }


    @Column
    public int getDistrictNumber() {
        return districtNumber;
    }

    public void setDistrictNumber(int districtNumber) {
        this.districtNumber = districtNumber;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public DistrictReference getDistrictReference() {
        return districtReference;
    }

    public void setDistrictReference(DistrictReference districtReference) {
        this.districtReference = districtReference;
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

    // Methods to calculate district measures

    public double calculateDeviationFrom(District other) {
        return Math.random();
    }

    private double calculatePopulationEquality(int idealPopulation) {
        double popRatio = (double) this.demographics.getTP() / idealPopulation;
        return Math.pow((popRatio - 1), 2);
    }

    private double calculateDeviationFromEnacted(Geometry hull, Demographics d) {
        return Math.random();
    }

    private double calculateDeviationFromAverage(Geometry hull, Demographics d) {
        return Math.random();
    }

    private static Compactness calculateCompactness(Geometry geometry) {
        return new Compactness(
                Compactness.calculatePolsbyPopper(geometry),
                Compactness.calculateFatness(geometry),
                0
        );
    }

    private static Demographics compileDemographics(ArrayList<Precinct> precincts) {
        int total_asian = 0;
        int total_black = 0;
        int total_natives = 0;
        int total_pacific = 0;
        int total_white = 0;
        int total_hispanic = 0;
        int total_otherRace = 0;
        int total_TP = 0;
        int total_VAP = 0;
        int total_CVAP = 0;

        for (Precinct precinct : precincts) {
            Demographics currentPrecinctDemographics = precinct.getDemographics();
            total_asian += currentPrecinctDemographics.getAsian();
            total_black += currentPrecinctDemographics.getBlack();
            total_natives += currentPrecinctDemographics.getNatives();
            total_pacific += currentPrecinctDemographics.getPacific();
            total_white += currentPrecinctDemographics.getWhite();
            total_hispanic += currentPrecinctDemographics.getHispanic();
            total_otherRace += currentPrecinctDemographics.getOtherRace();
            total_TP += currentPrecinctDemographics.getTP();
            total_VAP += currentPrecinctDemographics.getVAP();
            total_CVAP += currentPrecinctDemographics.getCVAP();
        }

        return new Demographics(total_asian, total_black,
                total_natives, total_pacific, total_white, total_hispanic, total_otherRace,
                total_TP, total_VAP, total_CVAP);
    }

    private static MajorityMinorityInfo compileMinorityInfo(Demographics demographics) {
        return (new MajorityMinorityInfo(
                demographics.isMajorityMinorityDistrict(MinorityPopulation.BLACK),
                demographics.isMajorityMinorityDistrict(MinorityPopulation.HISPANIC),
                demographics.isMajorityMinorityDistrict(MinorityPopulation.ASIAN),
                demographics.isMajorityMinorityDistrict(MinorityPopulation.NATIVE_AMERICAN)));
    }
}
