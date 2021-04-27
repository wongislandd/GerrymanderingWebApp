package cse416.spring.models.precinct;

import cse416.spring.enums.MinorityPopulation;

import javax.persistence.*;

@Entity
public class Demographics {
    private long id;
    int asian;
    int black;
    int natives;
    int pacific;
    int white;
    int hispanic;
    int otherRace;
    int TP;
    int VAP;
    int CVAP;

    public Demographics() {
    }

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

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column
    public int getPacific() {
        return pacific;
    }

    public void setPacific(int pacific) {
        this.pacific = pacific;
    }

    @Column
    public int getAsian() {
        return asian;
    }

    public void setAsian(int asian) {
        this.asian = asian;
    }

    @Column
    public int getBlack() {
        return black;
    }

    @Column
    public void setBlack(int black) {
        this.black = black;
    }

    @Column
    public int getNatives() {
        return natives;
    }

    public void setNatives(int natives) {
        this.natives = natives;
    }

    @Column
    public int getWhite() {
        return white;
    }

    public void setWhite(int white) {
        this.white = white;
    }

    @Column
    public int getHispanic() {
        return hispanic;
    }

    public void setHispanic(int hispanic) {
        this.hispanic = hispanic;
    }

    @Column
    public int getOtherRace() {
        return otherRace;
    }

    public void setOtherRace(int otherRace) {
        this.otherRace = otherRace;
    }

    @Column
    public int getTP() {
        return TP;
    }

    public void setTP(int TVP) {
        this.TP = TVP;
    }

    @Column
    public int getVAP() {
        return VAP;
    }

    public void setVAP(int VAP) {
        this.VAP = VAP;
    }

    @Column
    public int getCVAP() {
        return CVAP;
    }

    public void setCVAP(int CVAP) {
        this.CVAP = CVAP;
    }

    public boolean isMajorityMinorityDistrict(MinorityPopulation p) {
        switch (p) {
            case BLACK:
                //return (black / VAP) > .5;
                return (Math.random()) > .5;
            case ASIAN:
                //return (asian / VAP) > .5;
                return (Math.random()) > .5;
            case HISPANIC:
                //return (whiteHispanic / VAP) > .5;
                return (Math.random()) > .5;
            case NATIVE_AMERICAN:
                //return (natives / VAP) > .5;
                return (Math.random()) > .5;
            default:
                return false;
        }
    }
}
