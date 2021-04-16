package cse416.spring.models;

import javax.persistence.*;

@Entity
public class Demographics {
    private Long id;
    int democrats;
    int republicans;
    int otherParty;
    int asian;
    int black;
    int natives;
    int whiteHispanic;
    int whiteNonHispanic;
    int otherRace;
    int TVP;
    int VAP;
    int CVAP;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Demographics() {

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
        return whiteHispanic;
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
    public int getTVP() {
        return TVP;
    }

    public void setTVP(int TVP) {
        this.TVP = TVP;
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
