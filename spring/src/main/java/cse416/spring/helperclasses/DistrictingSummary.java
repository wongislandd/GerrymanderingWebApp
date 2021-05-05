package cse416.spring.helperclasses;

import cse416.spring.enums.MinorityPopulation;
import cse416.spring.models.district.Deviation;
import cse416.spring.models.district.District;
import cse416.spring.models.districting.Districting;
import cse416.spring.models.districting.DistrictingMeasures;
import cse416.spring.models.districting.EnactedDistricting;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.mapping.Set;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;

@Getter
@Setter
public class DistrictingSummary {
    // TODO Implement
    long id;
    double objectiveFunctionScore;
    DistrictingMeasures measures;
    ArrayList<DistrictSummary> districtSummaries;
    boolean isEnacted;

    public DistrictingSummary(Districting districting) {
        isEnacted = false;
        id = districting.getId();
        measures = districting.getMeasures();
        // TODO Calculate the actual districting's deviation from average
        measures.setDeviationFromAverageAvg(new Deviation(.5,.5));
        objectiveFunctionScore = districting.getObjectiveFunctionScore();
        districtSummaries = new ArrayList<>();
        for (District d : districting.getDistricts()) {
            // TODO Have all the transient properties of measures set before passing it in here.
            d.getMeasures().setDeviationFromAverage(new Deviation(.5,.5));
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
//        objectiveFunctionScore = enacted.getObjectiveFunctionScore();
        districtSummaries = new ArrayList<>();
        for (District d : enacted.getDistricts()) {
            // TODO Have all the transient properties of measures set before passing it in here.
            d.getMeasures().setDeviationFromAverage(new Deviation(0,0));
            districtSummaries.add(new DistrictSummary(d));
        }
        districtSummaries.sort(districtNumberComparator);
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
