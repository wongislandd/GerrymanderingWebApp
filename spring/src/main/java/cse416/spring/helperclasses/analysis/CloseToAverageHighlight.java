package cse416.spring.helperclasses.analysis;

import cse416.spring.helperclasses.DistrictingSummary;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class CloseToAverageHighlight implements AnalysisCategoryContainer {
    ArrayList<DistrictingSummary> entries;

    int maxSize = 5;

    public CloseToAverageHighlight() {
        this.entries = new ArrayList<>();
    }

    @Override
    public boolean shouldInsert(DistrictingSummary summary) {
        if (entries.size() < maxSize || entries.get(entries.size() - 1).getMeasures().getDeviationFromAverageAvg().getDeviationScore()
                > summary.getMeasures().getDeviationFromAverageAvg().getDeviationScore()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void sortEntries() {
        entries.sort((d1, d2) -> {
                    double d1Value = d1.getMeasures().getDeviationFromAverageAvg().getDeviationScore();
                    double d2Value = d2.getMeasures().getDeviationFromAverageAvg().getDeviationScore();
                    if (d1Value - d2Value > 0) {
                        return 1;
                    } else if (d1Value - d2Value < 0) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
        );
    }


    @Override
    public void insert(DistrictingSummary summary) {
        if (entries.size() < maxSize) {
            entries.add(summary);
        } else {
            entries.remove(entries.size() - 1);
            entries.add(summary);
        }
        sortEntries();
    }

    @Override
    public void insertIfFit(DistrictingSummary summary) {
        if (shouldInsert(summary)) {
            insert(summary);
        }
    }

}
