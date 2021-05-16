package cse416.spring.helperclasses;

import cse416.spring.enums.MinorityPopulation;
import cse416.spring.models.district.Deviation;
import cse416.spring.models.district.District;
import cse416.spring.models.districting.Districting;
import cse416.spring.models.districting.DistrictingMeasures;
import cse416.spring.models.districting.NormalizedDistrictingMeasures;
import lombok.Getter;
import lombok.Setter;
import org.decimal4j.util.DoubleRounder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

        if (districtings.size() == 1) {
            this.averageDistricting = districtings.stream().findAny().get();
        } else {
            this.averageDistricting = calculateAverageDistricting(districtings, constraints.getMinorityPopulation());
        }
        calculateDeviationFromAvg();
    }

    public void calculateDeviationFromAvg() throws IOException {
        ArrayList<District> averageDistrictingOrdered = averageDistricting.getMinorityOrderedDistricts(constraints.getMinorityPopulation());
        for (Districting districting : districtings) {
            Deviation totalDeviationFromAvg = new Deviation();
            double sumOfSquares = 0.0;
            ArrayList<District> orderedDistricts = districting.getMinorityOrderedDistricts(constraints.getMinorityPopulation());

            for (int i = 0; i < orderedDistricts.size(); i++) {
                District currentDistrict = orderedDistricts.get(i);
                District averageDistrict = averageDistrictingOrdered.get(i);
                // Calculate and set deviation from enacted
                Deviation deviationFromAvg = currentDistrict.calculateDeviationFrom(averageDistrict);
                currentDistrict.getMeasures().setDeviationFromAverage(deviationFromAvg);
                totalDeviationFromAvg.addAbsolute(currentDistrict.getMeasures().getDeviationFromAverage());

                double minorityPercentage = currentDistrict.getMeasures().getMajorityMinorityInfo().getMinorityPercentage(constraints.getMinorityPopulation());
                double avgMinorityPercentage = averageDistrict.getMeasures().getMajorityMinorityInfo().getMinorityPercentage(constraints.getMinorityPopulation());
                double deviation = (minorityPercentage - avgMinorityPercentage) / avgMinorityPercentage;
                sumOfSquares += Math.abs(deviation);
            }

            double avgSumOfSquares = sumOfSquares / orderedDistricts.size();
            districting.getMeasures().setMinorityDeviationFromAvg(avgSumOfSquares);

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

    public void calculateNormalizedMeasures() {
        List<Double> populationEqualities = districtings.stream().map(districting -> districting.getAverageDeviationFromIdeal()).collect(Collectors.toList());
        double popMax = populationEqualities.stream().max(Comparator.naturalOrder()).get();
        double popMin = populationEqualities.stream().min(Comparator.naturalOrder()).get();
        List<Double> compactnesses = districtings.stream().map(districting -> districting.getMeasures().getCompactnessAvg().getCompactness(constraints.compactnessType)).collect(Collectors.toList());
        double compactnessMax = compactnesses.stream().max(Comparator.naturalOrder()).get();
        double compactnessMin = compactnesses.stream().min(Comparator.naturalOrder()).get();
        List<Double> deviationsFromEnacted = districtings.stream().map(districting -> districting.getMeasures().getDeviationFromEnactedAvg().getDeviationScore()).collect(Collectors.toList());
        double deviationEnactedMax = deviationsFromEnacted.stream().max(Comparator.naturalOrder()).get();
        double deviationEnactedMin = deviationsFromEnacted.stream().min(Comparator.naturalOrder()).get();
        List<Double> deviationsFromAverage = districtings.stream().map(districting -> districting.getMeasures().getDeviationFromAverageAvg().getDeviationScore()).collect(Collectors.toList());
        double deviationAverageMax = deviationsFromAverage.stream().max(Comparator.naturalOrder()).get();
        double deviationAverageMin = deviationsFromAverage.stream().min(Comparator.naturalOrder()).get();
        List<Double> splitCountyScores = districtings.stream().map(districting -> districting.getMeasures().getSplitCountiesScore()).collect(Collectors.toList());
        double splitCountyMax = splitCountyScores.stream().max(Comparator.naturalOrder()).get();
        double splitCountyMin = splitCountyScores.stream().min(Comparator.naturalOrder()).get();

        for (Districting districting : districtings) {
            DistrictingMeasures measures = districting.getMeasures();
            double normalizedPop = normalize(districting.getAverageDeviationFromIdeal(), popMin, popMax, false);
            double normalizedCompactness = normalize(measures.getCompactnessAvg().getCompactness(constraints.getCompactnessType()), compactnessMin, compactnessMax, true);
            double normalizedDeviationEnacted = normalize(measures.getDeviationFromEnactedAvg().getDeviationScore(), deviationEnactedMin, deviationEnactedMax, false);
            double normalizedDeviationAverage = normalize(measures.getDeviationFromAverageAvg().getDeviationScore(), deviationAverageMin, deviationAverageMax, false);
            double splitCountyScore = normalize(measures.getSplitCountiesScore(), splitCountyMin, splitCountyMax, false);;
            NormalizedDistrictingMeasures normalizedMeasures = new NormalizedDistrictingMeasures(normalizedCompactness, normalizedPop, normalizedDeviationEnacted, normalizedDeviationAverage, splitCountyScore);
            districting.setNormalizedMeasures(normalizedMeasures);
        }

    }

    public double normalize(double value, double min, double max, boolean higherIsGood) {
        if (min == max) {
            return 1;
        }
        if (higherIsGood) {
            return (value-min)/(max-min);
        } else {
            return (max-value)/(max-min);
        }
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
