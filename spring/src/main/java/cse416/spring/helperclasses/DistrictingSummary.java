package cse416.spring.helperclasses;

import cse416.spring.enums.HighlightTypes;
import cse416.spring.enums.MinorityPopulation;
import cse416.spring.models.district.Deviation;
import cse416.spring.models.district.District;
import cse416.spring.models.districting.Districting;
import cse416.spring.models.districting.DistrictingMeasures;
import cse416.spring.models.districting.EnactedDistricting;
import cse416.spring.models.districting.NormalizedDistrictingMeasures;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.mapping.Array;
import org.hibernate.mapping.Set;

import java.util.*;

@Getter
@Setter
public class DistrictingSummary {
    long id;
    double objectiveFunctionScore;
    DistrictingMeasures measures;
    NormalizedDistrictingMeasures normalizedMeasures;
    ArrayList<DistrictSummary> districtSummaries;
    double areaPairDeviation;
    double averageDeviationFromIdeal;
    boolean isEnacted;
    List<HighlightTypes> tags;


    public void addTag(HighlightTypes tag) {
        tags.add(tag);
    }

    public void calculateNormalizedObjectiveFunctionScore(ObjectiveFunctionWeights weights) {
        double populationEquality = normalizedMeasures.getPopulationEquality();
        double splitCountiesScore = normalizedMeasures.getSplitCountyScore();
        double deviationFromAverage = normalizedMeasures.getDeviationFromAverage();
        double deviationFromEnacted = normalizedMeasures.getDeviationFromEnacted();
        double compactness = normalizedMeasures.getCompactness();

        double populationEqualityWeight = weights.getPopulationEquality();
        double splitCountiesScoreWeight = weights.getSplitCounties();
        double deviationAverageWeight = weights.getDeviationFromAverage();
        double deviationEnactedWeight = weights.getDeviationFromEnacted();
        double compactnessWeight = weights.getCompactness();

        double objectiveFunctionResult = (populationEquality * populationEqualityWeight) + (splitCountiesScore * splitCountiesScoreWeight)
                + (deviationFromAverage*deviationAverageWeight) + (deviationFromEnacted*deviationEnactedWeight)
                + (compactness * compactnessWeight);
        this.objectiveFunctionScore = objectiveFunctionResult;
    }


    public DistrictingSummary(Districting districting) {
        isEnacted = false;
        id = districting.getId();
        measures = districting.getMeasures();
        normalizedMeasures = districting.getNormalizedMeasures();
        objectiveFunctionScore = districting.getObjectiveFunctionScore();
        averageDeviationFromIdeal = districting.getAverageDeviationFromIdeal();
        districtSummaries = new ArrayList<>();
        tags = new ArrayList<>();
        for (District d : districting.getDistricts()) {
            // TODO Have all the transient properties of measures set before passing it in here.
            d.getMeasures().setDeviationFromAverage(d.getMeasures().getDeviationFromAverage());
            districtSummaries.add(new DistrictSummary(d));
        }
        districtSummaries.sort(districtNumberComparator);
    }


    public DistrictingSummary(EnactedDistricting enacted) {
        isEnacted = true;
        id = enacted.getId();
        measures = enacted.getMeasures();
        // TODO Calculate the actual districting's deviation from average
        measures.setDeviationFromAverageAvg(new Deviation(0,0));
        normalizedMeasures = new NormalizedDistrictingMeasures(0,0,0,0,0);
        districtSummaries = new ArrayList<>();
        tags = new ArrayList<>();
        for (District d : enacted.getDistricts()) {
            // TODO Have all the transient properties of measures set before passing it in here.
            d.getMeasures().setDeviationFromAverage(new Deviation(0,0));
            districtSummaries.add(new DistrictSummary(d));
        }
        districtSummaries.sort(districtNumberComparator);
    }

    public void setAreaPairDeviation(List<DistrictingSummary> others) {
        double total = 0;

        for (DistrictingSummary other : others) {
            double deviationFromOther = 0;

            for (int i = 0; i < districtSummaries.size(); i++) {
                DistrictSummary d1 = this.districtSummaries.get(i);
                DistrictSummary d2 = other.getDistrictSummaries().get(i);
                double area1 = d1.getArea();
                double area2 = d2.getArea();

                double avgArea = (area1 + area2) / 2;
                double deviation = (area1 - area2) / avgArea;
                deviationFromOther += Math.pow(deviation, 2);
            }
            total += deviationFromOther;
        }

        areaPairDeviation = total;
    }

    public static Comparator<DistrictSummary> districtNumberComparator = new Comparator<DistrictSummary>() {
        @Override
        public int compare(DistrictSummary districtSummary, DistrictSummary districtSummary2) {
            if (districtSummary.getDistrictNumber() > districtSummary2.getDistrictNumber()) {
                return 1;
            } else if (districtSummary.getDistrictNumber() < districtSummary2.getDistrictNumber()) {
                return -1;
            } else {
                return 0;
            }
        }
    };
}
