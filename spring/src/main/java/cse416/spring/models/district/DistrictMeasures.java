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

    @OneToOne(cascade = CascadeType.ALL)
    MajorityMinorityInfo majorityMinorityInfo;
    @OneToOne(cascade = CascadeType.ALL)
    Compactness compactness;
    @OneToOne(cascade = CascadeType.ALL)
    Deviation deviationFromEnacted;
    @Transient
    Deviation deviationFromAverage;
    @Transient
    int splitCounties;
    @Transient
    boolean isMajorityMinority;

    public DistrictMeasures(double populationEquality, MajorityMinorityInfo minorityInfo, Compactness compactness) {
        this.populationEquality = populationEquality;
        this.majorityMinorityInfo = minorityInfo;
        this.compactness = compactness;
        // TODO Implement counting split counties
        this.splitCounties = (int) (Math.random() * 10);
    }
}
