package cse416.spring.helperclasses.analysis;

import cse416.spring.enums.MinorityPopulation;
import cse416.spring.models.districting.Districting;

import java.util.ArrayList;

public class HighScoringMajorityMinority implements AnalysisCategoryContainer {
    ArrayList<Districting> entries;
    MinorityPopulation minority;
    int maxSize = 10;
    int desiredMinorityDistrictUpperBound;
    int desiredMinorityDistrictLowerBound;
    double threshold;

    public HighScoringMajorityMinority(MinorityPopulation minority, int desiredMinorityDistrictLowerBound, int desiredMinorityDistrictUpperBound, double threshold) {
        this.entries = new ArrayList<>();
        this.minority = minority;
        this.desiredMinorityDistrictUpperBound = desiredMinorityDistrictUpperBound;
        this.desiredMinorityDistrictLowerBound = desiredMinorityDistrictLowerBound;
        this.threshold = threshold;
    }

    public ArrayList<Districting> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Districting> entries) {
        this.entries = entries;
    }

    @Override
    public boolean shouldInsert(Districting districting) {
        double numMinorityDistricts = districting.getMMDistrictsCount(minority, threshold);
        if (numMinorityDistricts >= desiredMinorityDistrictLowerBound && numMinorityDistricts <= desiredMinorityDistrictUpperBound) {
            if (entries.size() < maxSize)
                return true;
            double districtingMMScore = calculateMMScore(districting);
            return districtingMMScore > calculateMMScore(entries.get(maxSize));
        }
        return false;
    }

    private double calculateMMScore(Districting districting) {
        /*should return a value for a districting based on how close it is to the enacted box and whisker and its OJ score
         * need to get box and whisker numbers?*/
        return districting.getObjectiveFunctionScore() * Math.random();
    }

    @Override
    public void sortEntries() {
        entries.sort((d1, d2) -> {
            double d1Value = calculateMMScore(d1);
            double d2Value = calculateMMScore(d2);
            double difference = d1Value - d2Value;
            if (difference > 0) {
                return 1;
            } else if (difference < 0) {
                return -1;
            } else {
                return 0;
            }
        });
    }

    @Override
    public void insert(Districting districting) {
        if (entries.size() < maxSize)
            entries.add(districting);
        else {
            entries.remove(maxSize - 1);
            entries.add(districting);
            sortEntries();
        }
    }
}
