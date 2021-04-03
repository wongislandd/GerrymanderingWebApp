package cse416.spring.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Compactness {
    @Column
    double polsbyPopper;

    @Column
    double populationFatness;

    @Column
    double graphCompactness;
    @Id
    private Long id;

    public Compactness(double polsbyPopper, double populationFatness, double graphCompactness) {
        this.polsbyPopper = polsbyPopper;
        this.populationFatness = populationFatness;
        this.graphCompactness = graphCompactness;
    }

    public Compactness() {

    }

    public double getPolsbyPopper() {
        return polsbyPopper;
    }

    public void setPolsbyPopper(double polsbyPopper) {
        this.polsbyPopper = polsbyPopper;
    }

    public double getPopulationFatness() {
        return populationFatness;
    }

    public void setPopulationFatness(double populationFatness) {
        this.populationFatness = populationFatness;
    }

    public double getGraphCompactness() {
        return graphCompactness;
    }

    public void setGraphCompactness(double graphCompactness) {
        this.graphCompactness = graphCompactness;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
