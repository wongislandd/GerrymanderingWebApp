package cse416.spring.helperclasses;

import cse416.spring.enums.MinorityPopulation;
import cse416.spring.models.district.Deviation;
import cse416.spring.models.district.District;
import cse416.spring.models.districting.Districting;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class ConstrainedDistrictings {
    Collection<Districting> districtings;
    Districting averageDistricting;
    List<List<Double>> boxAndWhiskerData;
    ObjectiveFunctionWeights currentWeights;
    DistrictingConstraints constraints;

    public Comparator<District> districtMinorityComparator = new Comparator<>() {
        @Override
        public int compare(District d1, District d2) {
            if (d1.getDemographics().getMinorityPercentage(constraints.getMinorityPopulation()) > d2.getDemographics().getMinorityPercentage(constraints.getMinorityPopulation())) {
                return 1;
            } else if (d1.getDemographics().getMinorityPercentage(constraints.getMinorityPopulation()) < d2.getDemographics().getMinorityPercentage(constraints.getMinorityPopulation())) {
                return -1;
            } else {
                return 0;
            }
        }
    };

    public List<List<Double>> getBoxAndWhiskerData() {
        if (boxAndWhiskerData == null) {
            boxAndWhiskerData = new ArrayList<>();
            for (Districting districting : districtings) {
                ArrayList<District> orderedDistricts = new ArrayList<>(districting.getDistricts());
                orderedDistricts.sort(districtMinorityComparator);
                for (int i=0;i<orderedDistricts.size();i++) {
                    boxAndWhiskerData.get(i).add(orderedDistricts.get(i).getDemographics().getMinorityPercentage(constraints.getMinorityPopulation()));
                }
            }
        }
        return boxAndWhiskerData;
    }

    public ConstrainedDistrictings(Collection<Districting> districtings, DistrictingConstraints constraints) {
        // TODO Calculate average, box and whisker (multithread?)
        this.districtings = districtings;
        this.constraints = constraints;

        // TODO Calculate the "average" districting, currently just getting a random one.
        List<Districting> districtingsList = new ArrayList<>(districtings);
        this.averageDistricting = districtingsList.get((int) (Math.random() * districtings.size()));

        // TODO Assign each district a deviation from average after finding the proper average districting
        for (Districting districting : districtings) {
            double totalDeviationFromAvgArea = 0;
            double totalDeviationFromAvgPop = 0;
            for (District district : districting.getDistricts()) {
                district.getMeasures().setDeviationFromAverage(new Deviation(Math.random(), Math.random()));
                totalDeviationFromAvgArea += district.getMeasures().getDeviationFromAverage().getAreaDev();
                totalDeviationFromAvgPop += district.getMeasures().getDeviationFromAverage().getPopulationDev();
            }
            districting.getMeasures().setDeviationFromAverageAvg(
                    new Deviation(totalDeviationFromAvgArea / districting.getDistricts().size(),
                            totalDeviationFromAvgPop / districting.getDistricts().size()));
        }
    }

}
