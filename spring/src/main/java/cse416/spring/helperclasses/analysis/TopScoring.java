package cse416.spring.helperclasses.analysis;

import cse416.spring.helperclasses.DistrictingSummary;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class TopScoring implements AnalysisCategoryContainer {
    ArrayList<DistrictingSummary> entries;

    int maxSize = 20;

    public TopScoring() {
        this.entries = new ArrayList<>();
    }

    public void sortEntries() {
        entries.sort((d1, d2) -> {
                    double difference = d1.getObjectiveFunctionScore() - d2.getObjectiveFunctionScore();
                    if (difference > 0) {
                        return -1;
                    } else if (difference < 0) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
        );
    }

    @Override
    public void insertIfFit(DistrictingSummary summary) {
        if (shouldInsert(summary)) {
            insert(summary);
        }
    }

    @Override
    public boolean shouldInsert(DistrictingSummary summary) {
        if (entries.size() < maxSize || entries.get(entries.size() - 1).getObjectiveFunctionScore() < summary.getObjectiveFunctionScore()) {
            return true;
        } else {
            return false;
        }
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
}
