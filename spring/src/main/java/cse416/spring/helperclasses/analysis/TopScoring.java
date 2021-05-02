package cse416.spring.helperclasses.analysis;

import cse416.spring.models.district.Compactness;
import cse416.spring.models.district.Deviation;
import cse416.spring.models.districting.Districting;
import cse416.spring.models.districting.DistrictingMeasures;

import java.util.ArrayList;
import java.util.Comparator;

public class TopScoring implements AnalysisCategoryContainer{
    ArrayList<Districting> entries;

    int maxSize = 10;

    public TopScoring() {
        this.entries = new ArrayList<>();
    }

    public ArrayList<Districting> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Districting> entries) {
        this.entries = entries;
    }


    public void sortEntries() {
        entries.sort(new Comparator<Districting>() {
            @Override
            public int compare(Districting d1, Districting d2) {
                double difference = d1.getObjectiveFunctionScore() - d2.getObjectiveFunctionScore();
                if (difference > 0) {
                    return 1;
                } else if(difference < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }

    @Override
    public boolean shouldInsert(Districting districting) {
        // Get measures; calculate Obj Func. Score here
        DistrictingMeasures measures = districting.getMeasures();

        double populationEquality = measures.getPopulationEqualityAvg();
        double splitCountiesScore = measures.getSplitCountiesScore();
        Deviation deviationFromAverage = measures.getDeviationFromAverageAvg();
        double areaDeviationAverage = deviationFromAverage.getAreaDev();
        double populationDeviationAverage = deviationFromAverage.getPopulationDev();

        Deviation deviationFromEnacted = measures.getDeviationFromEnactedAvg();
        double areaDeviationEnacted = deviationFromEnacted.getAreaDev();
        double populationDeviationEnacted = deviationFromEnacted.getPopulationDev();

        Compactness compactness = measures.getCompactnessAvg();
        double polsbyPopper = compactness.getPolsbyPopper();
        double populationFatness = compactness.getPopulationFatness();
        double graphCompactness = compactness.getGraphCompactness();

        // Obj Function Weights @TODO assign from DistrictingController
        double populationEqualityWeight = 1.0;
        double splitCountiesScoreWeight = 1.0;
        double deviationAverageWeight = 1.0;
        double deviationEnactedWeight = 1.0;
        double compactnessWeight = 1.0;

        double objectiveFunctionResult = (populationEqualityWeight * populationEquality) + (splitCountiesScoreWeight * splitCountiesScore) +
                (deviationAverageWeight * areaDeviationAverage) + (deviationAverageWeight * populationDeviationAverage) +
                (deviationEnactedWeight * areaDeviationEnacted) + (deviationEnactedWeight * populationDeviationEnacted) +
                (compactnessWeight * polsbyPopper) + (compactnessWeight * populationFatness) + (compactnessWeight * graphCompactness);

        districting.setObjectiveFunctionScore(objectiveFunctionResult);

        /* If this districting's score is greater than the lowest score on the list, replace.*/
        if (entries.get(entries.size() - 1).getObjectiveFunctionScore() < districting.getObjectiveFunctionScore() || entries.size() < maxSize) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void insert(Districting districting) {
//        if (entries.size() < maxSize) {
//            entries.add(districting);
//        }
//        else {
//            entries.remove(entries.size() - 1);
//            entries.add(districting);
//        }
//        sortEntries();
    }
}
