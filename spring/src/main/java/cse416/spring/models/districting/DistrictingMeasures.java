package cse416.spring.models.districting;

import cse416.spring.models.district.Compactness;
import cse416.spring.models.district.Deviation;
import cse416.spring.models.district.District;
import cse416.spring.models.district.DistrictMeasures;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DistrictingMeasures {
    @OneToOne(cascade = CascadeType.ALL)
    Compactness compactnessAvg;
    @Column
    double populationEqualityAvg;
    @Column
    double splitCountiesScore;
    @OneToOne(cascade = CascadeType.ALL)
    Deviation deviationFromEnactedAvg;
    @Transient
    Deviation deviationFromAverageAvg;
    @Transient
    int majorityMinorityDistricts;

    @Id
    @GeneratedValue
    private long id;

    /* Calculate districting measures from the collection of district measures */
    public DistrictingMeasures(Compactness compactnessAvg, double populationEqualityAvg,
                               double splitCountiesScore, Deviation deviationFromEnactedAvg, Deviation deviationFromAverageAvg) {
        this.populationEqualityAvg = populationEqualityAvg;
        this.compactnessAvg = compactnessAvg;
        this.splitCountiesScore = splitCountiesScore;
        this.deviationFromEnactedAvg = deviationFromEnactedAvg;
        this.deviationFromAverageAvg = deviationFromAverageAvg;
        // TODO Access actual data by parsing through districts dynamically.
        this.majorityMinorityDistricts = (int) (Math.random() * 10);
    }

    private static Compactness getAvgCompactness(ArrayList<District> districts) {
        // TODO: Use streams?
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

    private static double calculateSplitCountyScore(ArrayList<District> districts) {
        // TODO: Implement
//        Set<County> countySet = CountiesSetSingleton.getCountiesSet();
        return 0.0;
    }

    public DistrictingMeasures(ArrayList<District> districts) {
        double totalPopulationEquality = 0;
        Deviation totalDeviationFromEnacted = new Deviation();
        Deviation totalDeviationFromAverage = new Deviation();

        int numDistricts = districts.size();

        for (District district : districts) {
            DistrictMeasures districtMeasures = district.getMeasures();

            totalPopulationEquality += districtMeasures.getPopulationEquality();
            totalDeviationFromEnacted.add(districtMeasures.getDeviationFromEnacted());
            totalDeviationFromAverage.add(districtMeasures.getDeviationFromAverage());
        }

        this.compactnessAvg = getAvgCompactness(districts);

        this.populationEqualityAvg = totalPopulationEquality / numDistricts;
        this.deviationFromEnactedAvg = totalDeviationFromEnacted.getAverage(numDistricts);
        this.deviationFromAverageAvg = totalDeviationFromAverage.getAverage(numDistricts);

        this.splitCountiesScore = calculateSplitCountyScore(districts);
    }
}
