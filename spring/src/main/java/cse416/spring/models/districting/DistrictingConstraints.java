package cse416.spring.models.districting;

import cse416.spring.enums.MinorityPopulation;
import cse416.spring.enums.VotingPopulation;
import cse416.spring.models.district.Compactness;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class DistrictingConstraints {

    MinorityPopulation minorityPopulation;
    VotingPopulation votingPopulation;
    double maxPopulationDifference;
    int minMinorityDistricts;
    Compactness compactness;
    Collection<String> incumbentOptions;
    private long id;

    public DistrictingConstraints() {

    }

    public DistrictingConstraints(MinorityPopulation minorityPopulation, VotingPopulation votingPopulation, double maxPopulationDifference, int minMinorityDistricts, Compactness compactness, Collection<String> incumbentOptions) {
        this.minorityPopulation = minorityPopulation;
        this.votingPopulation = votingPopulation;
        this.maxPopulationDifference = maxPopulationDifference;
        this.minMinorityDistricts = minMinorityDistricts;
        this.compactness = compactness;
        this.incumbentOptions = incumbentOptions;
    }

    @Enumerated(EnumType.STRING)
    public MinorityPopulation getMinorityPopulation() {
        return minorityPopulation;
    }

    public void setMinorityPopulation(MinorityPopulation minorityPopulation) {
        this.minorityPopulation = minorityPopulation;
    }

    @Enumerated(EnumType.STRING)
    public VotingPopulation getVotingPopulation() {
        return votingPopulation;
    }

    public void setVotingPopulation(VotingPopulation votingPopulation) {
        this.votingPopulation = votingPopulation;
    }

    @Column
    public double getMaxPopulationDifference() {
        return maxPopulationDifference;
    }

    public void setMaxPopulationDifference(double maxPopulationDifference) {
        this.maxPopulationDifference = maxPopulationDifference;
    }

    @Column
    public int getMinMinorityDistricts() {
        return minMinorityDistricts;
    }

    public void setMinMinorityDistricts(int minMinorityDistricts) {
        this.minMinorityDistricts = minMinorityDistricts;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public Compactness getCompactness() {
        return compactness;
    }

    public void setCompactness(Compactness compactness) {
        this.compactness = compactness;
    }

    @ElementCollection
    public Collection<String> getIncumbentOptions() {
        return incumbentOptions;
    }

    public void setIncumbentOptions(Collection<String> incumbentOptions) {
        this.incumbentOptions = incumbentOptions;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
