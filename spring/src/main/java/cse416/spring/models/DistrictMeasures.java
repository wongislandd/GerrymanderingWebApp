package cse416.spring.models;

import javax.persistence.*;

@Entity
public class DistrictMeasures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public int id;

    @Column
    double populationEquality;

    @Column
    boolean isMajorityMinorityDistrict;

    @OneToOne
    Compactness compactness;

    @Column
    double politicalFairness;

    @Column
    int splitCounties;

    @Column
    double deviationFromEnacted;

    public DistrictMeasures(double populationEquality, boolean isMajorityMinorityDistrict, Compactness compactness, double politicalFairness, int splitCounties, double deviationFromEnacted, double deviationFromAverage) {
        this.populationEquality = populationEquality;
        this.isMajorityMinorityDistrict = isMajorityMinorityDistrict;
        this.compactness = compactness;
        this.politicalFairness = politicalFairness;
        this.splitCounties = splitCounties;
        this.deviationFromEnacted = deviationFromEnacted;
        this.deviationFromAverage = deviationFromAverage;
    }

    public DistrictMeasures() {

    }

    public double getPopulationEquality() {
        return populationEquality;
    }

    public void setPopulationEquality(double populationEquality) {
        this.populationEquality = populationEquality;
    }

    public boolean isMajorityMinorityDistrict() {
        return isMajorityMinorityDistrict;
    }

    public void setMajorityMinorityDistrict(boolean majorityMinorityDistrict) {
        isMajorityMinorityDistrict = majorityMinorityDistrict;
    }

    public Compactness getCompactness() {
        return compactness;
    }

    public void setCompactness(Compactness compactness) {
        this.compactness = compactness;
    }

    public double getPoliticalFairness() {
        return politicalFairness;
    }

    public void setPoliticalFairness(double politicalFairness) {
        this.politicalFairness = politicalFairness;
    }

    public int getSplitCounties() {
        return splitCounties;
    }

    public void setSplitCounties(int splitCounties) {
        this.splitCounties = splitCounties;
    }

    public double getDeviationFromEnacted() {
        return deviationFromEnacted;
    }

    public void setDeviationFromEnacted(double deviationFromEnacted) {
        this.deviationFromEnacted = deviationFromEnacted;
    }

    public double getDeviationFromAverage() {
        return deviationFromAverage;
    }

    public void setDeviationFromAverage(double deviationFromAverage) {
        this.deviationFromAverage = deviationFromAverage;
    }

    double deviationFromAverage;
}
