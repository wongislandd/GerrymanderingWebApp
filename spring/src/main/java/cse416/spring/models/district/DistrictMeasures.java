package cse416.spring.models.district;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DistrictMeasures {
    @Id
    @GeneratedValue
    private long id;
    @Column
    double populationEquality;

    @Column
    double populationDiffFromIdeal;

    @OneToOne(cascade = CascadeType.ALL)
    MajorityMinorityInfo majorityMinorityInfo;
    @OneToOne(cascade = CascadeType.ALL)
    Compactness compactness;
    @OneToOne(cascade = CascadeType.ALL)
    Deviation deviationFromEnacted;
    @Transient
    Deviation deviationFromAverage;
    @Transient
    boolean isMajorityMinority;

    public DistrictMeasures(double populationEquality, double populationDiffFromIdeal, MajorityMinorityInfo minorityInfo, Compactness compactness) {
        this.populationEquality = populationEquality;
        this.populationDiffFromIdeal = populationDiffFromIdeal;
        this.majorityMinorityInfo = minorityInfo;
        this.compactness = compactness;
    }
}
