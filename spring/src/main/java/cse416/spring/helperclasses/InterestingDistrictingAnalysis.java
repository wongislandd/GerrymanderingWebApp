package cse416.spring.helperclasses;

import cse416.spring.enums.HighlightTypes;
import cse416.spring.helperclasses.analysis.*;
import cse416.spring.models.districting.Districting;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class InterestingDistrictingAnalysis {
    ArrayList<DistrictingSummary> summaries;
    public InterestingDistrictingAnalysis(TopScoring topOFScoringContainer, DistrictingConstraints constraints) {
        summaries = topOFScoringContainer.getEntries();
        CloseToEnactedHighlight closeToEnacted = new CloseToEnactedHighlight();
        MajorityMinorityNewHighlight majorityMinority = new MajorityMinorityNewHighlight(constraints.getMinorityPopulation());
        AreaPairDeviationHighlight areaPairDeviation = new AreaPairDeviationHighlight();
        for (DistrictingSummary summary : summaries) {
            closeToEnacted.insertIfFit(summary);
            majorityMinority.insertIfFit(summary);
            areaPairDeviation.insertIfFit(summary);
        }
        closeToEnacted.getEntries().forEach(summary -> {summary.addTag(HighlightTypes.CLOSE_TO_ENACTED);});
        majorityMinority.getEntries().forEach(summary -> {summary.addTag(HighlightTypes.MAJORITY_MINORITY);});
        areaPairDeviation.getEntries().forEach(summary -> {summary.addTag(HighlightTypes.AREA_PAIR_DEVIATION);});
    }
}
