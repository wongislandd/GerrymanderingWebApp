package cse416.spring.models.districting;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NormalizedDistrictingMeasures {
    double compactness;
    double populationEquality;
    double deviationFromEnacted;
    double deviationFromAverage;
    double splitCountyScore;
}
