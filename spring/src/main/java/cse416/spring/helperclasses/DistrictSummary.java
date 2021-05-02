package cse416.spring.helperclasses;

import cse416.spring.models.district.DistrictMeasures;
import cse416.spring.models.precinct.Demographics;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DistrictSummary {
    // TODO Implement
    int districtNumber;
    double objectiveFunctionScore;
    Demographics demographics;
    DistrictMeasures measures;
}
