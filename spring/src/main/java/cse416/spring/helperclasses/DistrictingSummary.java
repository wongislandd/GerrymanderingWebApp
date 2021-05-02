package cse416.spring.helperclasses;

import cse416.spring.models.district.District;
import cse416.spring.models.districting.DistrictingMeasures;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class DistrictingSummary {
    // TODO Implement
    long id;
    double objectiveFunctionScore;
    DistrictingMeasures measures;
    Collection<DistrictSummary> districtSummaries;

    public DistrictingSummary() {

    }
}
