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
    int maxSize = 5;
    int desiredMinorityDistrictUpperBound;
    int desiredMinorityDistrictLowerBound;
    double threshold;

    public MajorityMinorityHighlight(MinorityPopulation minority) {
        this.entries = new ArrayList<>();
        this.minority = minority;
    }

    @Override
    public boolean shouldInsert(DistrictingSummary summary) {
        if (entries.size() < maxSize || entries.get(entries.size() - 1).getMeasures().getMajorityMinorityDistricts()
                < summary.getMeasures().getMajorityMinorityDistricts()) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void sortEntries() {
        entries.sort((d1, d2) -> {
            double difference = d1.getMeasures().getMajorityMinorityDistricts() - d2.getMeasures().getMajorityMinorityDistricts();
            if (difference > 0) {
                return -1;
            } else if (difference < 0) {
                return 1;
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
