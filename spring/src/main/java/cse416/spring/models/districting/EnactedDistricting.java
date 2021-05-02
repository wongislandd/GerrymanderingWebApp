package cse416.spring.models.districting;


import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.GeoJsonBuilder;
import cse416.spring.models.district.Compactness;
import cse416.spring.models.district.Deviation;
import cse416.spring.models.district.District;
import cse416.spring.models.district.DistrictMeasures;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "EnactedDistrictings")
@Getter
@Setter
@NoArgsConstructor
public class EnactedDistricting {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private StateName state;
    @OneToOne(cascade = CascadeType.ALL)
    DistrictingMeasures measures;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<District> districts;

    public EnactedDistricting(StateName state, Collection<District> districts) {
        this.state = state;
        this.districts = districts;
        // Placeholder until we move the measure logic into the DistrictingMeasures constructor
        this.measures = compileDistrictingMeasures(new ArrayList<>(districts));

        for (District district : this.districts) {
            String districtKey = district.getDistrictReference().getDistrictKey();
            district.setDistrictNumber(Integer.parseInt(districtKey));
        }
    }

    public DistrictingMeasures compileDistrictingMeasures(ArrayList<District> districts) {
        // TODO Put this in the constructor of DistrictingMeasures
        double totalPopulationEquality = 0;
        Deviation totalDeviationFromEnacted = new Deviation();
        Deviation totalDeviationFromAverage = new Deviation();

        int numDistricts = districts.size();

        for (District district : districts) {
            DistrictMeasures districtMeasures = district.getMeasures();

            totalPopulationEquality += districtMeasures.getPopulationEquality();
            totalDeviationFromEnacted.add(districtMeasures.getDeviationFromEnacted());
            totalDeviationFromAverage.add(districtMeasures.getDeviationFromAverage());
        }

        Compactness compactnessAvg = new Compactness(.3,.4,.5);

        double populationEqualityAvg = totalPopulationEquality / numDistricts;
        Deviation deviationFromEnactedAvg = totalDeviationFromEnacted.getAverage(numDistricts);
        Deviation deviationFromAverageAvg = totalDeviationFromAverage.getAverage(numDistricts);

        double splitCountyScore = .5;

        return new DistrictingMeasures(compactnessAvg,
                populationEqualityAvg, splitCountyScore,
                deviationFromEnactedAvg, deviationFromAverageAvg);
    }

    public String getGeoJson() throws IOException {
        GeoJsonBuilder geoJson = new GeoJsonBuilder().buildDistricts(districts).objectiveFunctionProperties(measures).name("Enacted Districting");
        return geoJson.toString();
    }
}
