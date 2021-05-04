package cse416.spring.helperclasses;

import cse416.spring.models.district.Compactness;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
public class ObjectiveFunctionWeights {
    double populationEquality;
    double splitCounties;
    double deviationFromAverage;
    double deviationFromEnacted;
    double compactness;
}
