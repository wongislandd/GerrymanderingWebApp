package cse416.spring.models.districting;


import cse416.spring.enums.MinorityPopulation;
import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.DistrictingSummary;
import cse416.spring.helperclasses.builders.GeoJsonBuilder;
import cse416.spring.models.district.Compactness;
import cse416.spring.models.district.Deviation;
import cse416.spring.models.district.District;
import cse416.spring.models.district.DistrictMeasures;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.decimal4j.util.DoubleRounder;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

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
    @Transient
    double objectiveFunctionScore;

    public EnactedDistricting(StateName state, Collection<District> districts) throws IOException {
        this.state = state;
        this.districts = districts;
        // Placeholder until we move the measure logic into the DistrictingMeasures constructor
        this.measures = new DistrictingMeasures(new ArrayList<>(districts));
    }

    public ArrayList<District> getMinorityOrderedDistricts(MinorityPopulation minority) {
        ArrayList<District> orderedDistricts = new ArrayList<>(districts);
        orderedDistricts.sort(new Comparator<>() {
            @Override
            public int compare(District d1, District d2) {
                if (d1.getDemographics().getMinorityPercentage(minority) > d2.getDemographics().getMinorityPercentage(minority)) {
                    return 1;
                } else if (d1.getDemographics().getMinorityPercentage(minority) < d2.getDemographics().getMinorityPercentage(minority)) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        return orderedDistricts;
    }

    public District getDistrictByNumber(int targetDistrictNumber) {
        for (District d : districts) {
            if (d.getDistrictNumber() == targetDistrictNumber) {
                return d;
            }
        }
        return null;
    }

    public ArrayList<Double> getMinorityPointData(MinorityPopulation minority) {
        ArrayList<Double> pointData = new ArrayList<>();
        ArrayList<District> orderedDistricts = getMinorityOrderedDistricts(minority);
        for (District orderedDistrict : orderedDistricts) {
            double minorityPercentage = orderedDistrict.getMeasures().getMajorityMinorityInfo().getMinorityPercentage(minority);
            pointData.add(DoubleRounder.round(minorityPercentage, 5));
        }
        return pointData;
    }

    public DistrictingSummary getSummary() {
        return new DistrictingSummary(this);
    }


    public String getGeoJson() throws IOException {
        GeoJsonBuilder geoJson = new GeoJsonBuilder().buildDistricts(districts).name("Enacted Districting");
        return geoJson.toString();
    }
}
