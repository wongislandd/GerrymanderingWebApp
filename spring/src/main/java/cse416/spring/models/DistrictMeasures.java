package cse416.spring.models;

import javax.persistence.*;

@Entity
public class DistrictMeasures {
    private long id;

    double populationEquality;

    boolean isMajorityMinorityDistrict;

    Compactness compactness;

    double politicalFairness;

    int splitCounties;

    double deviationFromAverage;

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

    @Column
    public double getPopulationEquality() {
        return populationEquality;
    }

    public void setPopulationEquality(double populationEquality) {
        this.populationEquality = populationEquality;
    }

    @Column
    public boolean isMajorityMinorityDistrict() {
        return isMajorityMinorityDistrict;
    }

    public void setMajorityMinorityDistrict(boolean majorityMinorityDistrict) {
        isMajorityMinorityDistrict = majorityMinorityDistrict;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public Compactness getCompactness() {
        return compactness;
    }

    public void setCompactness(Compactness compactness) {
        this.compactness = compactness;
    }

    @Column
    public double getPoliticalFairness() {
        return politicalFairness;
    }

    public void setPoliticalFairness(double politicalFairness) {
        this.politicalFairness = politicalFairness;
    }

    @Column
    public int getSplitCounties() {
        return splitCounties;
    }

    public void setSplitCounties(int splitCounties) {
        this.splitCounties = splitCounties;
    }

    @Column
    public double getDeviationFromEnacted() {
        return deviationFromEnacted;
    }

    public void setDeviationFromEnacted(double deviationFromEnacted) {
        this.deviationFromEnacted = deviationFromEnacted;
    }

    @Column
    public double getDeviationFromAverage() {
        return deviationFromAverage;
    }

    public void setDeviationFromAverage(double deviationFromAverage) {
        this.deviationFromAverage = deviationFromAverage;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }
}
