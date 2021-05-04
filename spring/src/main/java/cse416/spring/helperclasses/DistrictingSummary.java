package cse416.spring.helperclasses;

import cse416.spring.enums.MinorityPopulation;
import cse416.spring.models.district.District;
import cse416.spring.models.districting.Districting;
import cse416.spring.models.districting.DistrictingMeasures;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.mapping.Set;

import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
public class DistrictingSummary {
    // TODO Implement
    long id;
    double objectiveFunctionScore;
    DistrictingMeasures measures;
    Collection<DistrictSummary> districtSummaries;
    int majorityMinorityDistricts;

    public DistrictingSummary(Districting districting) {
        id = districting.getId();
        measures = districting.getMeasures();
        objectiveFunctionScore = districting.getObjectiveFunctionScore();
        districtSummaries = new HashSet<>();
        majorityMinorityDistricts = 0;
        for (District d : districting.getDistricts()) {
            // TODO Get majority minority district information onto the districting object earlier.
            if (d.getMeasures().getMajorityMinorityInfo().isMajorityMinorityDistrict(MinorityPopulation.BLACK, .3)) {
                majorityMinorityDistricts += 1;
            }
            districtSummaries.add(new DistrictSummary(d));
        }
    }
}
