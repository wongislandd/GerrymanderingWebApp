package cse416.spring.models.districting;

import cse416.spring.models.district.Compactness;
import cse416.spring.models.district.District;
import cse416.spring.models.district.DistrictMeasures;
import cse416.spring.models.district.MajorityMinorityInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Districting {
    private long id;
    private int jobID;
    protected DistrictingMeasures measures;
    protected double ObjectiveFunctionScore;
    protected String districtKeys;

    public Districting() {
    }

    public Districting(int jobID, ArrayList<District> districts) {
        JSONArray districtKeys = new JSONArray();
        for (District district : districts) {
            districtKeys.put(district.getId());
        }

        this.jobID = jobID;
        this.measures = compileDistrictingMeasures(districts);
        this.districtKeys = new JSONObject().put("districts", districtKeys).toString();
    }

    @Lob
    public String getDistrictKeys() {
        return districtKeys;
    }

    public void setDistrictKeys(String districtKeys) {
        this.districtKeys = districtKeys;
    }

    @Column
    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public DistrictingMeasures getMeasures() {
        return measures;
    }

    public void setMeasures(DistrictingMeasures measures) {
        this.measures = measures;
    }

    @Transient
    public double getObjectiveFunctionScore() {
        return ObjectiveFunctionScore;
    }

    public void setObjectiveFunctionScore(double objectiveFunctionScore) {
        ObjectiveFunctionScore = objectiveFunctionScore;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private static MajorityMinorityDistrictsCount getMMDistrictsCount(ArrayList<District> districts) {
        int blackDistricts = 0;
        int hispanicDistricts = 0;
        int asianDistricts = 0;
        int nativeDistricts = 0;

        for (District district : districts) {
            MajorityMinorityInfo mmInfo = district.getMeasures().getMajorityMinorityInfo();

            if (mmInfo.isBlackMajority()) {
                blackDistricts += 1;
            } else if (mmInfo.isHispanicMajority()) {
                hispanicDistricts += 1;
            } else if (mmInfo.isAsianMajority()) {
                asianDistricts += 1;
            } else if (mmInfo.isNativeMajority()) {
                nativeDistricts += 1;
            }
        }

        return new MajorityMinorityDistrictsCount(blackDistricts, hispanicDistricts, asianDistricts, nativeDistricts);
    }

    private static Compactness getAvgCompactness(ArrayList<District> districts) {
        double totalPolsbyPopperCompactness = 0;
        double totalPopulationFatnessCompactness = 0;
        double totalGraphCompactness = 0;

        int numDistricts = districts.size();

        for (District district : districts) {
            DistrictMeasures districtMeasures = district.getMeasures();
            totalPolsbyPopperCompactness += districtMeasures.getCompactness().getPolsbyPopper();
            totalPopulationFatnessCompactness += districtMeasures.getCompactness().getPopulationFatness();
            totalGraphCompactness += districtMeasures.getCompactness().getGraphCompactness();
        }

        return new Compactness(totalPolsbyPopperCompactness / numDistricts,
                totalPopulationFatnessCompactness / numDistricts,
                totalGraphCompactness / numDistricts);
    }

    private static double calculateSplitCountyScore() {
        // TODO: Implement
        return 0.0;
    }

    private static DistrictingMeasures compileDistrictingMeasures(ArrayList<District> districts) {
        double totalPopulationEquality = 0;
        double totalDeviationFromEnacted = 0;
        double totalDeviationFromAverage = 0;

        int numDistricts = districts.size();

        for (District district : districts) {
            DistrictMeasures districtMeasures = district.getMeasures();

            totalPopulationEquality += districtMeasures.getPopulationEquality();
            totalDeviationFromEnacted += districtMeasures.getDeviationFromEnacted();
            totalDeviationFromAverage += districtMeasures.getDeviationFromAverage();
        }

        MajorityMinorityDistrictsCount mmDistrictsCount = getMMDistrictsCount(districts);
        Compactness compactnessAvg = getAvgCompactness(districts);

        double populationEqualityAvg = totalPopulationEquality / numDistricts;
        double deviationFromEnactedAvg = totalDeviationFromEnacted / numDistricts;
        double deviationFromAverageAvg = totalDeviationFromAverage / numDistricts;

        double splitCountyScore = calculateSplitCountyScore();

        return new DistrictingMeasures(mmDistrictsCount, compactnessAvg,
                populationEqualityAvg, splitCountyScore,
                deviationFromEnactedAvg, deviationFromAverageAvg);
    }
}
