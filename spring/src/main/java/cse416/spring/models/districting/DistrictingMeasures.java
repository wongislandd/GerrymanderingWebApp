package cse416.spring.models.districting;

import cse416.spring.enums.StateName;
import cse416.spring.models.county.County;
import cse416.spring.models.district.Compactness;
import cse416.spring.models.district.Deviation;
import cse416.spring.models.district.District;
import cse416.spring.models.district.DistrictMeasures;
import cse416.spring.models.precinct.Precinct;
import cse416.spring.singletons.CountiesSetSingleton;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;
import java.util.*;

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
    double minorityDeviationFromAvg;
    @Transient
    int majorityMinorityDistricts;

    @Id
    @GeneratedValue
    private long id;

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

    private static HashMap<Integer, District> getPrecinctToDistrictMap(ArrayList<District> districts) throws IOException {
        HashMap<Integer, District> map = new HashMap<>();

        for (District d : districts) {
            for (Precinct p : d.getPrecincts()) {
                map.put(p.getPrecinctId(), d);
            }
        }
        return map;
    }




    private static int sum(ArrayList<Integer> list) {
        int sum = 0;
        for (int i : list) {
            sum += i;
        }
        return sum;
    }

    private static double calculateSplitCountyScore(ArrayList<District> districts) throws IOException {
        /* Each county has the following:
           district 1: [Precincts set]
           district 2: [Precincts set]
           ...
         */
        HashMap<County, HashMap<District, Set<Precinct>>> districtsPerCounty = new HashMap<>();
        HashMap<Integer, District> precinctToDistrict = getPrecinctToDistrictMap(districts);
        Set<County> countySet = CountiesSetSingleton.getCountiesSet(StateName.NORTH_CAROLINA);

        for (County c : countySet) {
            districtsPerCounty.put(c, new HashMap<>());

            for (Precinct p : c.getPrecincts()) {
                District d = precinctToDistrict.get(p.getPrecinctId());
                if (!districtsPerCounty.get(c).containsKey(d))
                    districtsPerCounty.get(c).put(d, new HashSet<>());

                districtsPerCounty.get(c).get(d).add(p);
            }
        }

        // Convert to easier format for calculating split county score
        HashMap<County, ArrayList<Integer>> countyToNumPrecincts = new HashMap<>();

        for (County c : countySet) {
            HashMap<District, Set<Precinct>> precinctsPerDistrict = districtsPerCounty.get(c);
            ArrayList<Integer> numPrecincts = new ArrayList<>();

            for (Set<Precinct> set : precinctsPerDistrict.values()) {
                numPrecincts.add(set.size());
            }
            Collections.sort(numPrecincts);
            countyToNumPrecincts.put(c, numPrecincts);
        }

        // Calculate the split county score
        int count2split = 0, count3split = 0;
        double w2 = 0, w3 = 0;
        double mc = 3.0;  // Constant to penalize 3-splits

        for (ArrayList<Integer> numPrecinctsInDistrict : countyToNumPrecincts.values()) {
            int numPrecincts = sum(numPrecinctsInDistrict);
            int numDistricts = numPrecinctsInDistrict.size();

            if (numDistricts == 2) {
                count2split++;
                double fraction = (double) numPrecinctsInDistrict.get(0) / numPrecincts;
                w2 += Math.sqrt(fraction);
            }
            else if (numDistricts > 2) {
                count3split++;
                double fraction = (double) (numPrecinctsInDistrict.get(0) + numPrecinctsInDistrict.get(1)) / numPrecincts;
                w3 += Math.sqrt(fraction);
            }
        }

        return (count2split * w2) + (mc * count3split * w3);
    }


    public DistrictingMeasures(ArrayList<District> districts) throws IOException {
        double totalPopulationEquality = 0;
//        Deviation totalDeviationFromEnacted = new Deviation();
//        Deviation totalDeviationFromAverage = new Deviation();

        int numDistricts = districts.size();
        int largestDistrictPop = 0;
        int smallestPopulationPop = districts.get(0).getDemographics().getTP();

        for (District district : districts) {
            DistrictMeasures districtMeasures = district.getMeasures();
            totalPopulationEquality += districtMeasures.getPopulationEquality();
//            totalDeviationFromEnacted.add(districtMeasures.getDeviationFromEnacted());
//            totalDeviationFromAverage.add(districtMeasures.getDeviationFromAverage());
            int districtPopulation = district.getDemographics().getTP();
            if (districtPopulation > largestDistrictPop) {
                largestDistrictPop = districtPopulation;
            }
            if (districtPopulation < smallestPopulationPop) {
                smallestPopulationPop = districtPopulation;
            }
        }
        this.compactnessAvg = getAvgCompactness(districts);

        this.populationEqualityAvg = totalPopulationEquality / numDistricts;
//        this.deviationFromEnactedAvg = totalDeviationFromEnacted.getAverage(numDistricts);
//        this.deviationFromAverageAvg = totalDeviationFromAverage.getAverage(numDistricts);

        this.splitCountiesScore = calculateSplitCountyScore(districts);
    }
}
