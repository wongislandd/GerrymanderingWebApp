package cse416.spring.models.districting;

import cse416.spring.models.district.Compactness;
import cse416.spring.models.district.Deviation;

import javax.persistence.*;

@Entity
public class DistrictingMeasures {
    MajorityMinorityDistrictsCount minorityDistrictsCount;
    Compactness compactnessAvg;
    double populationEqualityAvg;
    double splitCountiesScore;
    Deviation deviationFromEnactedAvg;
    Deviation deviationFromAverageAvg;

    private long id;

    public DistrictingMeasures() {
    }

    /* Calculate districting measures from the collection of district measures */
    public DistrictingMeasures(MajorityMinorityDistrictsCount minorityDistrictsCount, Compactness compactnessAvg, double populationEqualityAvg,
                               double splitCountiesScore, Deviation deviationFromEnactedAvg, Deviation deviationFromAverageAvg) {
        this.populationEqualityAvg = populationEqualityAvg;
        this.minorityDistrictsCount = minorityDistrictsCount;
        this.compactnessAvg = compactnessAvg;
        this.splitCountiesScore = splitCountiesScore;
        this.deviationFromEnactedAvg = deviationFromEnactedAvg;
        this.deviationFromAverageAvg = deviationFromAverageAvg;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public MajorityMinorityDistrictsCount getMinorityDistrictsCount() {
        return minorityDistrictsCount;
    }

    public void setMinorityDistrictsCount(MajorityMinorityDistrictsCount minorityDistrictsCount) {
        this.minorityDistrictsCount = minorityDistrictsCount;
    }

    @Column
    public double getPopulationEqualityAvg() {
        return populationEqualityAvg;
    }

    public void setPopulationEqualityAvg(double populationEqualityAvg) {
        this.populationEqualityAvg = populationEqualityAvg;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public Compactness getCompactnessAvg() {
        return compactnessAvg;
    }

    public void setCompactnessAvg(Compactness compactnessAvg) {
        this.compactnessAvg = compactnessAvg;
    }

    @Column
    public double getSplitCountiesScore() {
        return splitCountiesScore;
    }

    public void setSplitCountiesScore(double splitCountiesScore) {
        this.splitCountiesScore = splitCountiesScore;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public Deviation getDeviationFromEnactedAvg() {
        return deviationFromEnactedAvg;
    }

    public void setDeviationFromEnactedAvg(Deviation deviationFromEnactedAvg) {
        this.deviationFromEnactedAvg = deviationFromEnactedAvg;
    }

    @Transient
    public Deviation getDeviationFromAverageAvg() {
        return deviationFromAverageAvg;
    }

    public void setDeviationFromAverageAvg(Deviation deviationFromAverageAvg) {
        this.deviationFromAverageAvg = deviationFromAverageAvg;
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
