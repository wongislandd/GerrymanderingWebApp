package cse416.spring.models.district;

import cse416.spring.enums.MinorityPopulation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "MajorityMinorityInfos")
@Getter
@Setter
@NoArgsConstructor
public class MajorityMinorityInfo {
    @Column
    private double blackPercentage;
    @Column
    private double hispanicPercentage;
    @Column
    private double asianPercentage;
    @Column
    private double nativePercentage;
    @Id
    @GeneratedValue
    private Long id;

    public boolean isMajorityMinorityDistrict(MinorityPopulation minority, double threshold) {
        switch (minority) {
            case BLACK:
                return blackPercentage > threshold;
            case ASIAN:
                return asianPercentage > threshold;
            case HISPANIC:
                return hispanicPercentage > threshold;
            case NATIVE_AMERICAN:
                return nativePercentage > threshold;
            default:
                return false;
        }
    }

    public MajorityMinorityInfo(double blackPercentage, double hispanicPercentage, double asianPercentage, double nativePercentage) {
        this.blackPercentage = blackPercentage;
        this.hispanicPercentage = hispanicPercentage;
        this.asianPercentage = asianPercentage;
        this.nativePercentage = nativePercentage;
    }

    public double getMinorityPercentage(MinorityPopulation minority) {
        switch (minority) {
            case BLACK:
                return blackPercentage;
            case ASIAN:
                return asianPercentage;
            case HISPANIC:
                return hispanicPercentage;
            case NATIVE_AMERICAN:
                return nativePercentage;
            default:
                return 0.0;
        }
    }
}
