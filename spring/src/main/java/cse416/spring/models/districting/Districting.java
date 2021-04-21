package cse416.spring.models.districting;

import cse416.spring.models.district.Compactness;
import cse416.spring.models.district.District;
import cse416.spring.models.district.DistrictMeasures;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Districting {
    Collection<District> districts;
    DistrictingMeasures measures;
    double ObjectiveFunctionScore;
    private long id;

    public Districting() {

    }
    public Districting(ArrayList<District> districts) {
        this.districts = districts;
        this.measures = compileDistrictingMeasures(districts);
    }

    @ManyToMany(cascade = CascadeType.ALL)
    public Collection<District> getDistricts() {
        return districts;
    }

    public void setDistricts(Collection<District> districts) {
        this.districts = districts;
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

    public DistrictingMeasures compileDistrictingMeasures(ArrayList<District> districts) {
        int blackDistricts = 0;
        int hispanicDistricts = 0;
        int asianDistricts = 0;
        int nativeDistricts = 0;
        double totalPolsbyPopperCompactness = 0;
        double totalPopulationFatnessCompactness = 0;
        double totalGraphCompactness = 0;
        double totalSplitCounties = 0;
        double totalPopulationEquality = 0;
        double totalPoliticalFairness = 0;
        double totalDeviationFromEnacted = 0;
        double totalDeviationFromAverage = 0;


        for (int i=0;i<districts.size();i++) {
            DistrictMeasures districtMeasures = districts.get(i).getMeasures();
            if (districtMeasures.getMajorityMinorityInfo().isBlackMajority()) {
                blackDistricts += 1;
            }
            if (districtMeasures.getMajorityMinorityInfo().isHispanicMajority()) {
                hispanicDistricts += 1;
            }
            if (districtMeasures.getMajorityMinorityInfo().isAsianMajority()) {
                asianDistricts += 1;
            }
            if (districtMeasures.getMajorityMinorityInfo().isNativeMajority()) {
                nativeDistricts += 1;
            }

            totalPolsbyPopperCompactness += districtMeasures.getCompactness().getPolsbyPopper();
            totalPopulationFatnessCompactness += districtMeasures.getCompactness().getPopulationFatness();
            totalGraphCompactness += districtMeasures.getCompactness().getGraphCompactness();

            totalPopulationEquality += districtMeasures.getPopulationEquality();
            totalPoliticalFairness += districtMeasures.getPoliticalFairness();

            totalSplitCounties += districtMeasures.getSplitCounties();
            totalDeviationFromEnacted += districtMeasures.getDeviationFromEnacted();
            totalDeviationFromAverage += districtMeasures.getDeviationFromAverage();
        };
        MajorityMinorityDistrictsCount minorityDistrictsCount = new MajorityMinorityDistrictsCount(blackDistricts, hispanicDistricts, asianDistricts, nativeDistricts);
        Compactness compactnessAvg = new Compactness(totalPolsbyPopperCompactness/districts.size(), totalPopulationFatnessCompactness/districts.size(), totalGraphCompactness/districts.size());
        double populationEqualityAvg = totalPopulationEquality / districts.size();
        double politicalFairnessAvg = totalPoliticalFairness / districts.size();
        double deviationFromEnactedAvg = totalDeviationFromEnacted / districts.size();
        double deviationFromAverageAvg = totalDeviationFromAverage / districts.size();
        return new DistrictingMeasures(minorityDistrictsCount, compactnessAvg,
                populationEqualityAvg, politicalFairnessAvg, totalSplitCounties, deviationFromEnactedAvg, deviationFromAverageAvg);

    }
}
