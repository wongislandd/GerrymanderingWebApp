package cse416.spring.helperclasses;

import cse416.spring.models.district.Compactness;

import javax.persistence.*;

@Entity
public class ObjectiveFunctionWeights {
    double populationEquality;
    double splitCountyScores;
    double deviationFromAverage;
    double deviationFromEnacted;
    Compactness compactness;
    private long id;

    public ObjectiveFunctionWeights() {

    }

    public ObjectiveFunctionWeights(double populationEquality, double splitCountyScores, double deviationFromAverage,
                                    double deviationFromEnacted, Compactness compactness) {
        this.populationEquality = populationEquality;
        this.splitCountyScores = splitCountyScores;
        this.deviationFromAverage = deviationFromAverage;
        this.deviationFromEnacted = deviationFromEnacted;
        this.compactness = compactness;
    }

    @Column
    public double getPopulationEquality() {
        return populationEquality;
    }

    public void setPopulationEquality(double populationEquality) {
        this.populationEquality = populationEquality;
    }

    @Column
    public double getSplitCountyScores() {
        return splitCountyScores;
    }

    public void setSplitCountyScores(double splitCountyScores) {
        this.splitCountyScores = splitCountyScores;
    }

    @Column
    public double getDeviationFromAverage() {
        return deviationFromAverage;
    }

    public void setDeviationFromAverage(double deviationFromAverage) {
        this.deviationFromAverage = deviationFromAverage;
    }

    @Column
    public double getDeviationFromEnacted() {
        return deviationFromEnacted;
    }

    public void setDeviationFromEnacted(double deviationFromEnacted) {
        this.deviationFromEnacted = deviationFromEnacted;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public Compactness getCompactness() {
        return compactness;
    }

    public void setCompactness(Compactness compactness) {
        this.compactness = compactness;
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
