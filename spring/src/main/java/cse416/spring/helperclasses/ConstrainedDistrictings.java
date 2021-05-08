package cse416.spring.helperclasses;

import cse416.spring.enums.MinorityPopulation;
import cse416.spring.models.district.Deviation;
import cse416.spring.models.district.District;
import cse416.spring.models.districting.Districting;
import lombok.Getter;
import lombok.Setter;
import org.decimal4j.util.DoubleRounder;

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

    public List<List<Double>> getBoxAndWhiskerData() {
        if (boxAndWhiskerData == null) {
            /* Initialize box and whisker data */
            boxAndWhiskerData = new ArrayList<>();
            for (int i=0; i<averageDistricting.getDistricts().size();i++) {
                boxAndWhiskerData.add(new ArrayList<>());
            }

            for (Districting districting : districtings) {
                ArrayList<District> orderedDistricts = districting.getMinorityOrderedDistricts(constraints.getMinorityPopulation());
                for (int i=0;i<orderedDistricts.size();i++) {
                    double minorityPercentage = orderedDistricts.get(i).getMeasures().getMajorityMinorityInfo().getMinorityPercentage(constraints.getMinorityPopulation());
                    boxAndWhiskerData.get(i).add(DoubleRounder.round(minorityPercentage, 5));
                }
            }
        }
        return boxAndWhiskerData;
    }

    public Districting findDistrictingById(long id) {
        for (Districting districting : districtings) {
            if (districting.getId() == id) {
                return districting;
            }
        }
        return averageDistricting;
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
