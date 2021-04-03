package cse416.spring.models;

import cse416.spring.enums.VotingPopulation;
import cse416.spring.enums.MinorityPopulation;

import java.util.Collection;

public class DistrictingConstraints {
    MinorityPopulation minorityPopulation;
    VotingPopulation votingPopulation;
    double maxPopulationDifference;
    double minMinorityDistricts;
    Compactness compactness;
    Collection<String> incumbentOptions;

    public MinorityPopulation getMinorityPopulation() {
        return minorityPopulation;
    }

    public void setMinorityPopulation(MinorityPopulation minorityPopulation) {
        this.minorityPopulation = minorityPopulation;
    }

    public VotingPopulation getVotingPopulation() {
        return votingPopulation;
    }

    public void setVotingPopulation(VotingPopulation votingPopulation) {
        this.votingPopulation = votingPopulation;
    }

    public double getMaxPopulationDifference() {
        return maxPopulationDifference;
    }

    public void setMaxPopulationDifference(double maxPopulationDifference) {
        this.maxPopulationDifference = maxPopulationDifference;
    }

    public double getMinMinorityDistricts() {
        return minMinorityDistricts;
    }

    public void setMinMinorityDistricts(double minMinorityDistricts) {
        this.minMinorityDistricts = minMinorityDistricts;
    }

    public Compactness getCompactness() {
        return compactness;
    }

    public void setCompactness(Compactness compactness) {
        this.compactness = compactness;
    }

    public Collection<String> getIncumbentOptions() {
        return incumbentOptions;
    }

    public void setIncumbentOptions(Collection<String> incumbentOptions) {
        this.incumbentOptions = incumbentOptions;
    }

    public DistrictingConstraints(MinorityPopulation minorityPopulation, VotingPopulation votingPopulation, double maxPopulationDifference, double minMinorityDistricts, Compactness compactness, Collection<String> incumbentOptions) {
        this.minorityPopulation = minorityPopulation;
        this.votingPopulation = votingPopulation;
        this.maxPopulationDifference = maxPopulationDifference;
        this.minMinorityDistricts = minMinorityDistricts;
        this.compactness = compactness;
        this.incumbentOptions = incumbentOptions;
    }
}
