package cse416.spring.models.district;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "Deviations")
public class Deviation {
    private double areaDev;
    private double populationDev;
    private long id;

    public Deviation() {
        this(0, 0);
    }

    public Deviation(double areaDev, double populationDev) {
        this.areaDev = areaDev;
        this.populationDev = populationDev;
    }

    @Column
    public double getAreaDev() {
        return areaDev;
    }

    public void setAreaDev(double areaDev) {
        this.areaDev = areaDev;
    }

    @Column
    public double getPopulationDev() {
        return populationDev;
    }

    public void setPopulationDev(double populationDev) {
        this.populationDev = populationDev;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void add(Deviation other) {
        this.populationDev += other.getPopulationDev();
        this.areaDev += other.getAreaDev();
    }

    public Deviation getAverage(int numDistricts) {
        return new Deviation(this.populationDev / numDistricts,
                this.areaDev / numDistricts);
    }
}
