package cse416.spring.models.district;

import cse416.spring.enums.MinorityPopulation;
import cse416.spring.enums.StateName;
import cse416.spring.helperclasses.FileReader;
import cse416.spring.helperclasses.ObjectiveFunctionWeights;
import cse416.spring.helperclasses.builders.UnionBuilder;
import cse416.spring.helperclasses.constants.IdealPopulation;
import cse416.spring.models.districting.DistrictingMeasures;
import cse416.spring.models.precinct.Demographics;
import cse416.spring.models.precinct.Precinct;
import cse416.spring.singletons.PrecinctHashSingleton;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.json.JSONArray;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * A class to represent a congressional district.
 */
@Entity(name = "Districts")
@Getter
@Setter
@NoArgsConstructor
public class District {
    @Id
    @GeneratedValue
    private long id;
    /**
     * District number that corresponds to the district numbering of the
     * enacted districting based on the Gill metric.
     */
    @Column
    private int districtNumber;
    @Column
    double area;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Demographics demographics;
    @OneToOne(cascade = CascadeType.ALL)
    private DistrictMeasures measures;
    @OneToOne(cascade = CascadeType.ALL)
    private DistrictReference districtReference;
    @Transient
    private Collection<Precinct> precincts;
    @Transient
    private Geometry geometry;
    @Transient
    private double objectiveFunctionScore;

    public District(Collection<Precinct> precincts, StateName stateName, DistrictReference districtReference) throws IOException {
        this.demographics = compileDemographics(precincts);
        this.districtReference = districtReference;
        this.geometry = getGeometry();
        this.area = geometry.getArea();
        // TODO This should get overwritten by renumbering, remove once renumbering is right
        districtNumber = Integer.parseInt(districtReference.getDistrictKey());

        JSONArray precinctKeysArr = new JSONArray();
        for (Precinct p : precincts) {
            precinctKeysArr.put(p.getId());
        }
        int idealPopulation = IdealPopulation.getIdealPopulation(stateName);
        double populationEquality = this.calculatePopulationEquality(idealPopulation);
        MajorityMinorityInfo majorityMinorityInfo = compileMinorityInfo(demographics);
        Compactness compactness = calculateCompactness(geometry);
        double populationDiffFromIdeal = (double) (demographics.getTP() - idealPopulation) / idealPopulation;
        this.measures = new DistrictMeasures(populationEquality, populationDiffFromIdeal, majorityMinorityInfo,
                compactness);
    }

    private void generatePrecinctsList() throws IOException {
        HashMap<Integer, Precinct> precinctHash = PrecinctHashSingleton.getPrecinctHash(districtReference.getState());
        this.precincts = new HashSet<>();
        JSONArray precinctIdsInDistrict = FileReader.getDistrictsPrecinctsFromJsonFile(districtReference);
        for (int i = 0; i < precinctIdsInDistrict.length(); i++) {
            int targetPrecinctId = precinctIdsInDistrict.getInt(i);
            this.precincts.add(precinctHash.get(targetPrecinctId));
        }
    }

    public Collection<Precinct> getPrecincts() throws IOException {
        if (this.precincts == null)
            this.generatePrecinctsList();
        return this.precincts;
    }

    private void generateGeometry() throws IOException {
        this.geometry = UnionBuilder.getUnion(this.getPrecincts());
    }

    public Geometry getGeometry() throws IOException {
        if (geometry == null)
            this.generateGeometry();
        return this.geometry;
    }


    // TODO: Finish methods to calculate district measures
    public Deviation calculateDeviationFrom(District other) throws IOException {
        // For the enacted districting
        if (other == null) {
            return new Deviation(0,0);
        }
        double thisPopulation = demographics.getTP();
        double otherPopulation = other.getDemographics().getTP();
        double popPctChange = (thisPopulation - otherPopulation) / otherPopulation;
        double thisArea = getArea();
        double otherArea = other.getArea();
        double areaPctChange = (thisArea - otherArea) / otherArea;
        return new Deviation(areaPctChange, popPctChange);
    }

    private double calculatePopulationEquality(int idealPopulation) {
        double popRatio = (double) this.demographics.getTP() / idealPopulation;
        return (Math.pow((popRatio - 1), 2));
    }



    private static Compactness calculateCompactness(Geometry geometry) {
        return new Compactness(
                Compactness.calculatePolsbyPopper(geometry),
                Compactness.calculateFatness(geometry),
                0
        );
    }

    private static Demographics compileDemographics(Collection<Precinct> precincts) {
        int total_asian = 0;
        int total_black = 0;
        int total_natives = 0;
        int total_pacific = 0;
        int total_white = 0;
        int total_hispanic = 0;
        int total_otherRace = 0;
        int total_TP = 0;
        int total_VAP = 0;
        int total_CVAP = 0;

        for (Precinct precinct : precincts) {
            Demographics currentPrecinctDemographics = precinct.getDemographics();
            total_asian += currentPrecinctDemographics.getAsian();
            total_black += currentPrecinctDemographics.getBlack();
            total_natives += currentPrecinctDemographics.getNatives();
            total_pacific += currentPrecinctDemographics.getPacific();
            total_white += currentPrecinctDemographics.getWhite();
            total_hispanic += currentPrecinctDemographics.getHispanic();
            total_otherRace += currentPrecinctDemographics.getOtherRace();
            total_TP += currentPrecinctDemographics.getTP();
            total_VAP += currentPrecinctDemographics.getVAP();
            total_CVAP += currentPrecinctDemographics.getCVAP();
        }

        return new Demographics(total_asian, total_black,
                total_natives, total_pacific, total_white, total_hispanic, total_otherRace,
                total_TP, total_VAP, total_CVAP);
    }

    // TODO calculate the percentage population
    private MajorityMinorityInfo compileMinorityInfo(Demographics demographics) {
        return (new MajorityMinorityInfo(
                demographics.getMinorityPercentage(MinorityPopulation.BLACK),
                demographics.getMinorityPercentage(MinorityPopulation.HISPANIC),
                demographics.getMinorityPercentage(MinorityPopulation.ASIAN),
                demographics.getMinorityPercentage(MinorityPopulation.NATIVE_AMERICAN)));
    }
}
