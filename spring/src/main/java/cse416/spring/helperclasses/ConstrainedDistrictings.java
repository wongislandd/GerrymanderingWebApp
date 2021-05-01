package cse416.spring.helperclasses;

import cse416.spring.models.districting.Districting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Collection;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConstrainedDistrictings {
    Collection<Districting> districtings;
    double[][] boxAndWhiskerData;
    ObjectiveFunctionWeights currentWeights;
    DistrictingConstraints constraints;
}
