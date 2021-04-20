package cse416.spring.models;

import javax.persistence.*;

@Entity
public class Demographics {
    private long id;
    int democrats;
    int republicans;
    int otherParty;
    int asian;
    int black;
    int natives;
    int pacific;
    int whiteHispanic;
    int whiteNonHispanic;
    int otherRace;
    int TP;
    int VAP;
    int CVAP;

    public Demographics(int democrats, int republicans, int otherParty, int asian, int black, int natives, int pacific, int whiteHispanic, int whiteNonHispanic, int otherRace, int TP, int VAP, int CVAP) {
        this.democrats = democrats;
        this.republicans = republicans;
        this.otherParty = otherParty;
        this.asian = asian;
        this.black = black;
        this.natives = natives;
        this.pacific = pacific;
        this.whiteHispanic = whiteHispanic;
        this.whiteNonHispanic = whiteNonHispanic;
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

    public Demographics() {

    }

    @Column
    public int getPacific() {
        return pacific;
    }

    public void setPacific(int pacific) {
        this.pacific = pacific;
    }

    @Column
    public int getDemocrats() {
        return democrats;
    }

    public void setDemocrats(int democrats) {
        this.democrats = democrats;
    }
    @Column
    public int getRepublicans() {
        return republicans;
    }

    public void setRepublicans(int republicans) {
        this.republicans = republicans;
    }
    @Column
    public int getOtherParty() {
        return otherParty;
    }

    public void setOtherParty(int otherParty) {
        this.otherParty = otherParty;
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
    public int getWhiteNonHispanic() {
        return whiteNonHispanic;
    }

    public void setWhiteNonHispanic(int whiteNonHispanic) {
        this.whiteNonHispanic = whiteNonHispanic;
    }

    @Column
    public int getWhiteHispanic() {
        return whiteHispanic;
    }

    public void setWhiteHispanic(int whiteHispanic) {
        this.whiteHispanic = whiteHispanic;
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







}
