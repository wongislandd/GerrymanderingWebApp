package cse416.spring.helperclasses;

import cse416.spring.enums.MinorityPopulation;
import cse416.spring.models.district.Deviation;
import cse416.spring.models.district.District;
import cse416.spring.models.districting.Districting;
import lombok.Getter;
import lombok.Setter;
import org.decimal4j.util.DoubleRounder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class ConstrainedDistrictings {
    Collection<Districting> districtings;
    Districting averageDistricting;
    List<List<Double>> boxAndWhiskerData;
    ObjectiveFunctionWeights currentWeights;
    DistrictingConstraints constraints;

    public ConstrainedDistrictings(Collection<Districting> districtings, DistrictingConstraints constraints) throws IOException {
        this.districtings = districtings;
        this.constraints = constraints;
        this.averageDistricting = calculateAverageDistricting(districtings, constraints.getMinorityPopulation());
        ArrayList<District> averageDistrictingOrdered = averageDistricting.getMinorityOrderedDistricts(constraints.getMinorityPopulation());
        for (Districting districting : districtings) {
            Deviation totalDeviationFromAvg = new Deviation();
            ArrayList<District> orderedDistricts = districting.getMinorityOrderedDistricts(constraints.getMinorityPopulation());
            for (int i = 0; i < orderedDistricts.size(); i++) {
                long startTime = System.currentTimeMillis();
                District currentDistrict = orderedDistricts.get(i);
                District averageDistrict = averageDistrictingOrdered.get(i);
                // Calculate and set deviation from enacted
                Deviation deviationFromAvg = currentDistrict.calculateDeviationFrom(averageDistrict);
                currentDistrict.getMeasures().setDeviationFromAverage(deviationFromAvg);
                totalDeviationFromAvg.add(currentDistrict.getMeasures().getDeviationFromAverage());
                long endTime = System.currentTimeMillis();
                System.out.println("Calculated deviation from avg in " + (endTime-startTime) + "ms");
            }
            Deviation avgDeviation = totalDeviationFromAvg.getAverage(orderedDistricts.size());
            districting.getMeasures().setDeviationFromAverageAvg(avgDeviation);
        }
    }

    public List<List<Double>> getBoxAndWhiskerData() {
        if (boxAndWhiskerData == null) {
            /* Initialize box and whisker data */
            boxAndWhiskerData = new ArrayList<>();
            for (int i = 0; i < averageDistricting.getDistricts().size(); i++) {
                boxAndWhiskerData.add(new ArrayList<>());
            }

            for (Districting districting : districtings) {
                ArrayList<District> orderedDistricts = districting.getMinorityOrderedDistricts(constraints.getMinorityPopulation());
                for (int i = 0; i < orderedDistricts.size(); i++) {
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

    public Districting calculateAverageDistricting(Collection<Districting> districtings, MinorityPopulation minority) {
        double bestScore = 0;
        Districting averageDistricting = null;
        double[] districtMeanCalculator = new double[districtings.stream().findAny().get().getDistricts().size()];
        // TODO Optimize?
        // Calculate totals
        for (Districting districting : districtings) {
            ArrayList<District> orderedDistricts = districting.getMinorityOrderedDistricts(minority);
            for (int j = 0; j < orderedDistricts.size(); j++) {
                districtMeanCalculator[j] += orderedDistricts.get(j).getMeasures().getMajorityMinorityInfo().getMinorityPercentage(minority);
            }
        }
        // Turn totals into means
        for (int i = 0; i < districtMeanCalculator.length; i++) {
            districtMeanCalculator[i] = districtMeanCalculator[i] / districtings.size();
        }
        // Compare each districting
        for (Districting districting : districtings) {
            ArrayList<District> orderedDistricts = districting.getMinorityOrderedDistricts(minority);
            double runningScore = 0;
            for (int j = 0; j < orderedDistricts.size(); j++) {
                runningScore += Math.pow(orderedDistricts.get(j).getMeasures().getMajorityMinorityInfo().getMinorityPercentage(minority) - districtMeanCalculator[j], 2);
            }
            if (runningScore > bestScore) {
                bestScore = runningScore;
                averageDistricting = districting;
            }
        }
        return averageDistricting;
    }

}
