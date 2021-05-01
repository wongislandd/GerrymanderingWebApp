package cse416.spring.models.districting;

import cse416.spring.models.district.Compactness;
import cse416.spring.models.district.Deviation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DistrictingMeasures {
    @OneToOne(cascade = CascadeType.ALL)
    Compactness compactnessAvg;
    @Column
    double populationEqualityAvg;
    @Column
    double splitCountiesScore;
    @OneToOne(cascade = CascadeType.ALL)
    Deviation deviationFromEnactedAvg;
    @Transient
    Deviation deviationFromAverageAvg;

    @Id
    @GeneratedValue
    private long id;

    /* Calculate districting measures from the collection of district measures */
    public DistrictingMeasures(Compactness compactnessAvg, double populationEqualityAvg,
                               double splitCountiesScore, Deviation deviationFromEnactedAvg, Deviation deviationFromAverageAvg) {
        this.populationEqualityAvg = populationEqualityAvg;
        this.compactnessAvg = compactnessAvg;
        this.splitCountiesScore = splitCountiesScore;
        this.deviationFromEnactedAvg = deviationFromEnactedAvg;
        this.deviationFromAverageAvg = deviationFromAverageAvg;
    }
}
