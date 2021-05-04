package cse416.spring.models.precinct;

import cse416.spring.enums.MinorityPopulation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Demographics {
    @Id
    @GeneratedValue
    private long id;
    @Column
    int asian;
    @Column
    int black;
    @Column
    int natives;
    @Column
    int pacific;
    @Column
    int white;
    @Column
    int hispanic;
    @Column
    int otherRace;
    @Column
    int TP;
    @Column
    int VAP;
    @Column
    int CVAP;

    public Demographics(int asian, int black, int natives, int pacific, int white, int hispanic,
                        int otherRace, int TP, int VAP, int CVAP) {
        this.asian = asian;
        this.black = black;
        this.natives = natives;
        this.pacific = pacific;
        this.white = white;
        this.hispanic = hispanic;
        this.otherRace = otherRace;
        this.TP = TP;
        this.VAP = VAP;
        this.CVAP = CVAP;
    }

    public double getMinorityPercentage(MinorityPopulation minority) {
        // TODO: Fix to floating point division
        switch (minority) {
            case BLACK:
                return ((double)black / TP);
            case ASIAN:
                return ((double)asian / TP);
            case HISPANIC:
                return ((double)hispanic / TP);
            case NATIVE_AMERICAN:
                return ((double)natives / TP);
            default:
                return 0;
        }
    }
}
