package cse416.spring.helperclasses;

import cse416.spring.helperclasses.analysis.CloseToEnacted;
import cse416.spring.helperclasses.analysis.HighScoringMajorityMinority;
import cse416.spring.helperclasses.analysis.TopAreaPairDeviation;
import cse416.spring.helperclasses.analysis.TopScoring;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
public class InterestingDistrictingAnalysis {
    TopScoring topOFScoring;
    CloseToEnacted closeToEnacted;
    HighScoringMajorityMinority highScoringWithMajorityMinority;
    TopAreaPairDeviation topAreaPairDeviation;
}
