package cse416.spring.helperclasses;

import cse416.spring.helperclasses.analysis.CloseToEnacted;
import cse416.spring.helperclasses.analysis.HighScoringMajorityMinority;
import cse416.spring.helperclasses.analysis.TopAreaPairDeviation;
import cse416.spring.helperclasses.analysis.TopScoring;
import cse416.spring.models.districting.Districting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;

@Getter
@Setter
public class InterestingDistrictingAnalysis {
    ArrayList<DistrictingSummary> topOFScoring;
    ArrayList<DistrictingSummary> closeToEnacted;
    ArrayList<DistrictingSummary> highScoringWithMajorityMinority;
    ArrayList<DistrictingSummary> topAreaPairDeviation;

    public InterestingDistrictingAnalysis(TopScoring topOFScoringContainer, CloseToEnacted closeToEnactedContainer, HighScoringMajorityMinority highScoringMajorityMinorityContainer, TopAreaPairDeviation topAreaPairDeviationContainer) {
        this.topOFScoring = new ArrayList<>();
        this.closeToEnacted = new ArrayList<>();
        this.highScoringWithMajorityMinority = new ArrayList<>();
        this.topAreaPairDeviation = new ArrayList<>();
        for (Districting d : topOFScoringContainer.getEntries()) {
            this.topOFScoring.add(new DistrictingSummary(d));
        }
        for (Districting d : closeToEnactedContainer.getEntries()) {
            this.closeToEnacted.add(new DistrictingSummary(d));
        }
        for (Districting d : highScoringMajorityMinorityContainer.getEntries()) {
            this.highScoringWithMajorityMinority.add(new DistrictingSummary(d));
        }
        for (Districting d : topAreaPairDeviationContainer.getEntries()) {
            this.topAreaPairDeviation.add(new DistrictingSummary(d));
        }
    }

}
