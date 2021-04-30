package cse416.spring.helperclasses;

import cse416.spring.enums.MinorityPopulation;
import cse416.spring.enums.VotingPopulation;
import cse416.spring.models.district.Compactness;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;


@Getter
@Setter
public class DistrictingConstraints {
    MinorityPopulation minorityPopulation;
    VotingPopulation votingPopulation;
    double maxPopulationDifference;
    int minMinorityDistricts;
    Compactness compactness;
    Collection<String> incumbentOptions;
    private long id;

    public DistrictingConstraints(MinorityPopulation minorityPopulation, VotingPopulation votingPopulation, double maxPopulationDifference, int minMinorityDistricts, Compactness compactness, Collection<String> incumbentOptions) {
        this.minorityPopulation = minorityPopulation;
        this.votingPopulation = votingPopulation;
        this.maxPopulationDifference = maxPopulationDifference;
        this.minMinorityDistricts = minMinorityDistricts;
        this.compactness = compactness;
        this.incumbentOptions = incumbentOptions;
    }

}
