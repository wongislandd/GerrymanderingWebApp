package cse416.spring.infowrappers;

public class Demographics {
    int Democrats;
    int Republicans;
    int OtherParty;
    int Asian;
    int Black;
    int Native;
    int White;
    int OtherRace;
    int TotalPopulation;

    public int getDemocrats() {
        return Democrats;
    }

    public void setDemocrats(int democrats) {
        Democrats = democrats;
    }

    public int getRepublicans() {
        return Republicans;
    }

    public void setRepublicans(int republicans) {
        Republicans = republicans;
    }

    public int getOtherParty() {
        return OtherParty;
    }

    public void setOtherParty(int otherParty) {
        OtherParty = otherParty;
    }

    public int getAsian() {
        return Asian;
    }

    public void setAsian(int asian) {
        Asian = asian;
    }

    public int getBlack() {
        return Black;
    }

    public void setBlack(int black) {
        Black = black;
    }

    public int getNative() {
        return Native;
    }

    public void setNative(int aNative) {
        Native = aNative;
    }

    public int getWhite() {
        return White;
    }

    public void setWhite(int white) {
        White = white;
    }

    public int getOtherRace() {
        return OtherRace;
    }

    public void setOtherRace(int otherRace) {
        OtherRace = otherRace;
    }

    public int getTotalPopulation() {
        return TotalPopulation;
    }

    public void setTotalPopulation(int totalPopulation) {
        TotalPopulation = totalPopulation;
    }

    public int getVotingAgePopulation() {
        return VotingAgePopulation;
    }

    public void setVotingAgePopulation(int votingAgePopulation) {
        VotingAgePopulation = votingAgePopulation;
    }

    public int getCitizenVotingAgePopulation() {
        return CitizenVotingAgePopulation;
    }

    public void setCitizenVotingAgePopulation(int citizenVotingAgePopulation) {
        CitizenVotingAgePopulation = citizenVotingAgePopulation;
    }

    public Demographics(int democrats, int republicans, int otherParty, int asian, int black, int aNative, int white, int otherRace, int totalPopulation, int votingAgePopulation, int citizenVotingAgePopulation) {
        Democrats = democrats;
        Republicans = republicans;
        OtherParty = otherParty;
        Asian = asian;
        Black = black;
        Native = aNative;
        White = white;
        OtherRace = otherRace;
        TotalPopulation = totalPopulation;
        VotingAgePopulation = votingAgePopulation;
        CitizenVotingAgePopulation = citizenVotingAgePopulation;
    }

    int VotingAgePopulation;
    int CitizenVotingAgePopulation;
}
