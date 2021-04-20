package cse416.spring.helperclasses;

import cse416.spring.models.JobSummary;

import javax.persistence.*;

@Entity
public class MGGGParams {
    private long id;
    int coolingPeriod;
    double maxPopulationDiff;

    public MGGGParams(int coolingPeriod, double maxPopulationDiff) {
        this.coolingPeriod = coolingPeriod;
        this.maxPopulationDiff = maxPopulationDiff;
    }

    public MGGGParams() {

    }

    @Column(name="cooling_period")
    public int getCoolingPeriod() {
        return coolingPeriod;
    }

    public void setCoolingPeriod(int coolingPeriod) {
        this.coolingPeriod = coolingPeriod;
    }

    @Column(name="max_population_diff")
    public double getMaxPopulationDiff() {
        return maxPopulationDiff;
    }

    public void setMaxPopulationDiff(double maxPopulationDiff) {
        this.maxPopulationDiff = maxPopulationDiff;
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
