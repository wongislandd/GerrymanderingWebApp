package cse416.spring.helperclasses;

import cse416.spring.models.district.District;
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
    boolean isMajorityMinorityDistrict;
    double area;

    public DistrictSummary(District district) {
        this.districtNumber = district.getDistrictNumber();
        this.demographics = district.getDemographics();
        this.measures = district.getMeasures();
        this.objectiveFunctionScore = district.getObjectiveFunctionScore();
        this.isMajorityMinorityDistrict = district.getMeasures().isMajorityMinority();
        this.area = district.getArea();
    }
}
