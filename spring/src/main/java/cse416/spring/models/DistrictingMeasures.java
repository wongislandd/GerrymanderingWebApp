package cse416.spring.models;

import javax.persistence.*;

@Entity
public class DistrictingMeasures {
    double populationEqualityAvg;
    int majorityMinorityDistrictsAvg;
    Compactness compactnessAvg;
    double politicalFairnessAvg;
    double splitCountiesScore;
    double deviationFromEnactedAvg;
    double deviationFromAverageAvg;
    private long id;

    public DistrictingMeasures() {

    }

    public DistrictingMeasures(double populationEqualityAvg, int majorityMinorityDistrictsAvg, Compactness compactnessAvg, double politicalFairnessAvg, double splitCountiesScore, double deviationFromEnactedAvg, double deviationFromAverageAvg) {
        this.populationEqualityAvg = populationEqualityAvg;
        this.majorityMinorityDistrictsAvg = majorityMinorityDistrictsAvg;
        this.compactnessAvg = compactnessAvg;
        this.politicalFairnessAvg = politicalFairnessAvg;
        this.splitCountiesScore = splitCountiesScore;
        this.deviationFromEnactedAvg = deviationFromEnactedAvg;
        this.deviationFromAverageAvg = deviationFromAverageAvg;
    }

    @Column
    public double getPopulationEqualityAvg() {
        return populationEqualityAvg;
    }

    public void setPopulationEqualityAvg(double populationEqualityAvg) {
        this.populationEqualityAvg = populationEqualityAvg;
    }
    @Column
    public int getMajorityMinorityDistrictsAvg() {
        return majorityMinorityDistrictsAvg;
    }

    public void setMajorityMinorityDistrictsAvg(int majorityMinorityDistrictsAvg) {
        this.majorityMinorityDistrictsAvg = majorityMinorityDistrictsAvg;
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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
