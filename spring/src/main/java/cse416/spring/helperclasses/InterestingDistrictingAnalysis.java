package cse416.spring.helperclasses;

import cse416.spring.helperclasses.analysis.CloseToEnacted;
import cse416.spring.helperclasses.analysis.HighScoringMajorityMinority;
import cse416.spring.helperclasses.analysis.TopAreaPairDeviation;
import cse416.spring.helperclasses.analysis.TopScoring;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
public class InterestingDistrictingAnalysis {
    TopScoring topOFScoring;
    CloseToEnacted closeToEnacted;
    HighScoringMajorityMinority highScoringWithMajorityMinority;
    TopAreaPairDeviation topAreaPairDeviation;
    private long id;

    public InterestingDistrictingAnalysis() {
    }

    public InterestingDistrictingAnalysis(TopScoring topOFScoring, CloseToEnacted closeToEnacted, HighScoringMajorityMinority highScoringWithMajorityMinority, TopAreaPairDeviation topAreaPairDeviation) {
        this.topOFScoring = topOFScoring;
        this.closeToEnacted = closeToEnacted;
        this.highScoringWithMajorityMinority = highScoringWithMajorityMinority;
        this.topAreaPairDeviation = topAreaPairDeviation;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public TopScoring getTopOFScoring() {
        return topOFScoring;
    }

    public void setTopOFScoring(TopScoring topOFScoring) {
        this.topOFScoring = topOFScoring;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public CloseToEnacted getCloseToEnacted() {
        return closeToEnacted;
    }

    public void setCloseToEnacted(CloseToEnacted closeToEnacted) {
        this.closeToEnacted = closeToEnacted;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public HighScoringMajorityMinority getHighScoringWithMajorityMinority() {
        return highScoringWithMajorityMinority;
    }

    public void setHighScoringWithMajorityMinority(HighScoringMajorityMinority highScoringWithMajorityMinority) {
        this.highScoringWithMajorityMinority = highScoringWithMajorityMinority;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public TopAreaPairDeviation getTopAreaPairDeviation() {
        return topAreaPairDeviation;
    }

    public void setTopAreaPairDeviation(TopAreaPairDeviation topAreaPairDeviation) {
        this.topAreaPairDeviation = topAreaPairDeviation;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
