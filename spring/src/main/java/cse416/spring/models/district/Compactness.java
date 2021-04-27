package cse416.spring.models.district;

import javax.persistence.*;

/**
 * A data class to represent the three compactness measures of a district.
 */
@Entity
public class Compactness {
    private double polsbyPopper;
    private double populationFatness;
    private double graphCompactness;
    private long id;

    public Compactness() {
    }

    public Compactness(double polsbyPopper, double populationFatness, double graphCompactness) {
        this.polsbyPopper = polsbyPopper;
        this.populationFatness = populationFatness;
        this.graphCompactness = graphCompactness;
    }

    @Column
    public double getPolsbyPopper() {
        return polsbyPopper;
    }

    public void setPolsbyPopper(double polsbyPopper) {
        this.polsbyPopper = polsbyPopper;
    }

    @Column
    public double getPopulationFatness() {
        return populationFatness;
    }

    public void setPopulationFatness(double populationFatness) {
        this.populationFatness = populationFatness;
    }

    @Column
    public double getGraphCompactness() {
        return graphCompactness;
    }

    public void setGraphCompactness(double graphCompactness) {
        this.graphCompactness = graphCompactness;
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
