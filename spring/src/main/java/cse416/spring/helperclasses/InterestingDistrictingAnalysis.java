package cse416.spring.helperclasses;

import cse416.spring.enums.HighlightTypes;
import cse416.spring.helperclasses.analysis.*;
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
        CloseToAverageHighlight closeToAverage = new CloseToAverageHighlight();
        MajorityMinorityHighlight majorityMinority = new MajorityMinorityHighlight(constraints.getMinorityPopulation());
        AreaPairDeviationHighlight areaPairDeviation = new AreaPairDeviationHighlight();
        for (DistrictingSummary summary : summaries) {
            closeToEnacted.insertIfFit(summary);
            closeToAverage.insertIfFit(summary);
            majorityMinority.insertIfFit(summary);
            areaPairDeviation.insertIfFit(summary);
        }
        closeToEnacted.getEntries().forEach(summary -> {summary.addTag(HighlightTypes.CLOSE_TO_ENACTED);});
        closeToAverage.getEntries().forEach(summary -> {summary.addTag(HighlightTypes.CLOSE_TO_AVERAGE);});
        majorityMinority.getEntries().forEach(summary -> {summary.addTag(HighlightTypes.MAJORITY_MINORITY);});
        areaPairDeviation.getEntries().forEach(summary -> {summary.addTag(HighlightTypes.AREA_PAIR_DEVIATION);});
    }
}
