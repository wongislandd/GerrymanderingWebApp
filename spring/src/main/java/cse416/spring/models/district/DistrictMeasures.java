package cse416.spring.models.district;

import javax.persistence.*;

@Entity
public class DistrictMeasures {
    private long id;

    double populationEquality;

    MajorityMinorityInfo majorityMinorityInfo;

    Compactness compactness;

    double politicalFairness;

    int splitCounties;

    double deviationFromAverage;

    double deviationFromEnacted;

    public DistrictMeasures(double populationEquality, MajorityMinorityInfo minorityInfo, Compactness compactness, double politicalFairness, int splitCounties, double deviationFromEnacted, double deviationFromAverage) {
        this.populationEquality = populationEquality;
        this.majorityMinorityInfo = minorityInfo;
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


    @OneToOne(cascade = CascadeType.ALL)
    public MajorityMinorityInfo getMajorityMinorityInfo() {
        return majorityMinorityInfo;
    }

    public void setMajorityMinorityInfo(MajorityMinorityInfo majorityMinorityInfo) {
        this.majorityMinorityInfo = majorityMinorityInfo;
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
