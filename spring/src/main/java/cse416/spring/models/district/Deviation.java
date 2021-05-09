package cse416.spring.models.district;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "Deviations")
@Getter
@Setter
public class Deviation {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private double areaDev;
    @Column
    private double populationDev;

    public Deviation() {
        this(0, 0);
    }

    public Deviation(double areaDev, double populationDev) {
        this.areaDev = areaDev;
        this.populationDev = populationDev;
    }

    public void add(Deviation other) {
        this.populationDev += other.getPopulationDev();
        this.areaDev += other.getAreaDev();
    }

    public Deviation getAverage(int numDistricts) {
        return new Deviation(this.populationDev / numDistricts, this.areaDev / numDistricts);
    }

    public double getDeviationScore() {
        return this.populationDev + this.areaDev;
    }
}
