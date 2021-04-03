package cse416.spring.models;

import javax.persistence.*;

@Entity
public class DistrictingMeasures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public int id;

    @OneToOne
    District district;

    public DistrictingMeasures() {

    }

    public double getPopulationEqualityAvg() {
        return populationEqualityAvg;
    }

    public void setPopulationEqualityAvg(double populationEqualityAvg) {
        this.populationEqualityAvg = populationEqualityAvg;
    }

    public int getMajorityMinorityDistrictsAvg() {
        return majorityMinorityDistrictsAvg;
    }

    public void setMajorityMinorityDistrictsAvg(int majorityMinorityDistrictsAvg) {
        this.majorityMinorityDistrictsAvg = majorityMinorityDistrictsAvg;
    }

    public Compactness getCompactnessAvg() {
        return compactnessAvg;
    }

    public void setCompactnessAvg(Compactness compactnessAvg) {
        this.compactnessAvg = compactnessAvg;
    }

    public double getPoliticalFairnessAvg() {
        return politicalFairnessAvg;
    }

    public void setPoliticalFairnessAvg(double politicalFairnessAvg) {
        this.politicalFairnessAvg = politicalFairnessAvg;
    }

    public double getSplitCountiesScore() {
        return splitCountiesScore;
    }

    public void setSplitCountiesScore(double splitCountiesScore) {
        this.splitCountiesScore = splitCountiesScore;
    }

    public double getDeviationFromEnactedAvg() {
        return deviationFromEnactedAvg;
    }

    public void setDeviationFromEnactedAvg(double deviationFromEnactedAvg) {
        this.deviationFromEnactedAvg = deviationFromEnactedAvg;
    }

    public double getDeviationFromAverageAvg() {
        return deviationFromAverageAvg;
    }

    public void setDeviationFromAverageAvg(double deviationFromAverageAvg) {
        this.deviationFromAverageAvg = deviationFromAverageAvg;
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
    double populationEqualityAvg;

    @Column
    int majorityMinorityDistrictsAvg;

    @OneToOne
    Compactness compactnessAvg;

    @Column
    double politicalFairnessAvg;

    @Column
    double splitCountiesScore;

    @Column
    double deviationFromEnactedAvg;

    @Column
    double deviationFromAverageAvg;
}
