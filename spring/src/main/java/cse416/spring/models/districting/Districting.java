package cse416.spring.models.districting;

import cse416.spring.enums.MinorityPopulation;
import cse416.spring.models.district.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jgrapht.graph.SimpleWeightedGraph;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "Districtings")
@Getter
@Setter
@NoArgsConstructor
public class Districting {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private int jobID;
    @OneToOne(cascade = CascadeType.ALL)
    private DistrictingMeasures measures;
    @Transient
    private double ObjectiveFunctionScore;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<District> districts;

    public Districting(int jobID, ArrayList<District> districts, EnactedDistricting enactedDistricting) {
        this.jobID = jobID;
        this.measures = compileDistrictingMeasures(districts);
        this.districts = districts;
        this.renumberDistricts(enactedDistricting);
    }


    private void renumberDistricts(EnactedDistricting enactedDistricting) {
        // TODO Implement
        ArrayList<District> districts = new ArrayList<>(this.districts);
    }

    public double getMMDistrictsCount(MinorityPopulation minority, double threshhold) {
        int count = 0;
        for (District district : districts) {
            MajorityMinorityInfo mmInfo = district.getMeasures().getMajorityMinorityInfo();
            if (mmInfo.isMajorityMinorityDistrict(minority, threshhold)) {
                count++;
            }
        }
        return count;
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

    private DistrictingMeasures compileDistrictingMeasures(ArrayList<District> districts) {
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

        Compactness compactnessAvg = getAvgCompactness(districts);

        double populationEqualityAvg = totalPopulationEquality / numDistricts;
        Deviation deviationFromEnactedAvg = totalDeviationFromEnacted.getAverage(numDistricts);
        Deviation deviationFromAverageAvg = totalDeviationFromAverage.getAverage(numDistricts);

        double splitCountyScore = calculateSplitCountyScore();

        return new DistrictingMeasures(compactnessAvg,
                populationEqualityAvg, splitCountyScore,
                deviationFromEnactedAvg, deviationFromAverageAvg);
    }
}
