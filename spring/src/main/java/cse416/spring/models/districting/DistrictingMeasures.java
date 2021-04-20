package cse416.spring.models.districting;

import cse416.spring.models.district.Compactness;

import javax.persistence.*;

@Entity
public class DistrictingMeasures {
    MajorityMinorityDistrictsCount minorityDistrictsCount;
    Compactness compactnessAvg;
    double populationEqualityAvg;
    double politicalFairnessAvg;
    double splitCountiesScore;
    double deviationFromEnactedAvg;
    double deviationFromAverageAvg;

    private long id;

    public DistrictingMeasures() {

    }

    /* Calculate districting measures from the collection of district measures */
    public DistrictingMeasures(MajorityMinorityDistrictsCount minorityDistrictsCount, Compactness compactnessAvg, double populationEqualityAvg, double politicalFairnessAvg, double splitCountiesScore, double deviationFromEnactedAvg, double deviationFromAverageAvg) {
        this.populationEqualityAvg = populationEqualityAvg;
        this.minorityDistrictsCount = minorityDistrictsCount;
        this.compactnessAvg = compactnessAvg;
        this.politicalFairnessAvg = politicalFairnessAvg;
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
    public double getPoliticalFairnessAvg() {
        return politicalFairnessAvg;
    }

    public void setPoliticalFairnessAvg(double politicalFairnessAvg) {
        this.politicalFairnessAvg = politicalFairnessAvg;
    }
    @Column
    public double getSplitCountiesScore() {
        return splitCountiesScore;
    }

    public void setSplitCountiesScore(double splitCountiesScore) {
        this.splitCountiesScore = splitCountiesScore;
    }
    @Column
    public double getDeviationFromEnactedAvg() {
        return deviationFromEnactedAvg;
    }

    public void setDeviationFromEnactedAvg(double deviationFromEnactedAvg) {
        this.deviationFromEnactedAvg = deviationFromEnactedAvg;
    }
    @Column
    public double getDeviationFromAverageAvg() {
        return deviationFromAverageAvg;
    }

    public void setDeviationFromAverageAvg(double deviationFromAverageAvg) {
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
