package cse416.spring.helperclasses.analysis;

import cse416.spring.enums.MinorityPopulation;
import cse416.spring.helperclasses.DistrictingSummary;
import cse416.spring.models.districting.Districting;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class MajorityMinorityHighlight implements AnalysisCategoryContainer {
    ArrayList<DistrictingSummary> entries;
    MinorityPopulation minority;
    int maxSize = 10;
    int desiredMinorityDistrictUpperBound;
    int desiredMinorityDistrictLowerBound;
    double threshold;

    public MajorityMinorityHighlight(MinorityPopulation minority, int desiredMinorityDistrictLowerBound, int desiredMinorityDistrictUpperBound, double threshold) {
        this.entries = new ArrayList<>();
        this.minority = minority;
        this.desiredMinorityDistrictUpperBound = desiredMinorityDistrictUpperBound;
        this.desiredMinorityDistrictLowerBound = desiredMinorityDistrictLowerBound;
        this.threshold = threshold;
    }

    @Override
    public boolean shouldInsert(DistrictingSummary summary) {
        double numMinorityDistricts = summary.getMeasures().getMajorityMinorityDistricts();
        if (numMinorityDistricts >= desiredMinorityDistrictLowerBound && numMinorityDistricts <= desiredMinorityDistrictUpperBound) {
            if (entries.size() < maxSize)
                return true;
            double districtingMMScore = calculateMMScore(summary);
            return districtingMMScore > calculateMMScore(entries.get(maxSize));
        }
        return false;
    }

    private double calculateMMScore(DistrictingSummary summary) {
        /*should return a value for a districting based on how close it is to the enacted box and whisker and its OJ score
         * need to get box and whisker numbers?*/
        return summary.getObjectiveFunctionScore() * Math.random();
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
    public void insertIfFit(DistrictingSummary summary) {
        if (shouldInsert(summary)) {
            insert(summary);
        }
    }

    @Override
    public void insert(DistrictingSummary summary) {
        if (entries.size() < maxSize) {
            entries.add(summary);
        }
        else {
            entries.remove(entries.size() - 1);
            entries.add(summary);
        }
        sortEntries();
    }
}
