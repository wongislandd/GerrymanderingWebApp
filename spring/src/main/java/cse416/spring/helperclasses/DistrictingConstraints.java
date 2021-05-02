package cse416.spring.helperclasses;

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
    MinorityPopulation minorityPopulation;
    VotingPopulation votingPopulation;
    double maxPopulationDifference;
    int minMinorityDistricts;
    Compactness compactness;
    Collection<String> incumbentOptions;
}
