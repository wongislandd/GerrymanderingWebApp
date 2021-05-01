package cse416.spring.models.job;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MGGGParams {
    @Id
    @GeneratedValue
    private long id;
    @Column
    int coolingPeriod;
    @Column
    double maxPopulationDiff;

    public MGGGParams(int coolingPeriod, double maxPopulationDiff) {
        this.coolingPeriod = coolingPeriod;
        this.maxPopulationDiff = maxPopulationDiff;
    }
}
