package cse416.spring.helperclasses;

import cse416.spring.enums.CompactnessType;
import cse416.spring.enums.MinorityPopulation;
import cse416.spring.enums.VotingPopulation;
import cse416.spring.models.district.Compactness;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class DistrictingConstraints {
    long jobId;
    MinorityPopulation minorityPopulation;
    int minMinorityDistricts;
    int maxMinorityDistricts;
    double minorityThreshold;
    VotingPopulation votingPopulation;
    double maxPopulationDifference;
    CompactnessType compactnessType;
    double compactnessThreshold;
//    Collection<String> incumbentOptions;
}
