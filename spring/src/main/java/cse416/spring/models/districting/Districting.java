package cse416.spring.models.districting;

import cse416.spring.enums.MinorityPopulation;
import cse416.spring.helperclasses.GeoJsonBuilder;
import cse416.spring.models.district.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.matching.HopcroftKarpMaximumCardinalityBipartiteMatching;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Entity(name = "Districtings")
@Getter
@Setter
@NoArgsConstructor
public class Districting {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private int jobID;
    @OneToOne(cascade = CascadeType.ALL)
    private DistrictingMeasures measures;
    @Transient
    private double ObjectiveFunctionScore;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<District> districts;

    public Districting(int jobID, ArrayList<District> districts, EnactedDistricting enactedDistricting) {
        this.jobID = jobID;
        this.measures = compileDistrictingMeasures(districts);
        this.districts = districts;
    }

    public String getGeoJson() throws IOException {
        GeoJsonBuilder geoJson = new GeoJsonBuilder().buildDistricts(districts).objectiveFunctionProperties(measures).name(Long.toString(id));
        return geoJson.toString();
    }

    private static double getIntersectionArea(District d1, District d2) throws IOException {
        Geometry g1 = d1.getGeometry();
        Geometry g2 = d2.getGeometry();
        return g1.intersection(g2).getArea();
    }

    private static SimpleDirectedWeightedGraph<District, Double> getBipartiteGraph(HashSet<District> districts1,
                                                                                   HashSet<District> districts2) throws IOException {

        SimpleDirectedWeightedGraph<District, Double> bipartiteGraph = new SimpleDirectedWeightedGraph<>(Double.class);

        for (District d : districts1)
            bipartiteGraph.addVertex(d);

        for (District d : districts2)
            bipartiteGraph.addVertex(d);

        // Add edges. An edge has a weight which is the area of the intersection between two districts
        for (District d1 : districts1) {
            for (District d2 : districts2) {
                double interArea = getIntersectionArea(d1, d2);
                bipartiteGraph.addEdge(d1, d2, interArea);
            }
        }

        return bipartiteGraph;
    }

    public void renumberDistricts(EnactedDistricting enactedDistricting) throws IOException {
        // Make a bipartite graph matching enacted districts to generated districts
        HashSet<District> enactedDistricts = new HashSet<>(enactedDistricting.getDistricts());
        HashSet<District> generatedDistricts = new HashSet<>(this.districts);
        SimpleDirectedWeightedGraph<District, Double> bipartiteGraph = getBipartiteGraph(enactedDistricts, generatedDistricts);

        // Match each enacted district with a generated district
        HopcroftKarpMaximumCardinalityBipartiteMatching<District, Double> matcher =
                new HopcroftKarpMaximumCardinalityBipartiteMatching<>(bipartiteGraph, enactedDistricts, generatedDistricts);

        Graph<District, Double> matching = matcher.getMatching().getGraph();
        for (District enactedDistrict : enactedDistricts) {
            District generatedDistrict = Graphs.neighborListOf(matching, enactedDistrict).get(0);
            generatedDistrict.setDistrictNumber(enactedDistrict.getDistrictNumber());
        }
    }

    public double getMMDistrictsCount(MinorityPopulation minority, double threshold) {
        int count = 0;
        for (District district : districts) {
            MajorityMinorityInfo mmInfo = district.getMeasures().getMajorityMinorityInfo();
            if (mmInfo.isMajorityMinorityDistrict(minority, threshold)) {
                count++;
            }
        }
        return count;
    }

    private static Compactness getAvgCompactness(ArrayList<District> districts) {
        double totalPolsbyPopperCompactness = 0;
        double totalPopulationFatnessCompactness = 0;
        double totalGraphCompactness = 0;

        int numDistricts = districts.size();

        for (District district : districts) {
            DistrictMeasures districtMeasures = district.getMeasures();
            totalPolsbyPopperCompactness += districtMeasures.getCompactness().getPolsbyPopper();
            totalPopulationFatnessCompactness += districtMeasures.getCompactness().getPopulationFatness();
            totalGraphCompactness += districtMeasures.getCompactness().getGraphCompactness();
        }

        return new Compactness(totalPolsbyPopperCompactness / numDistricts,
                totalPopulationFatnessCompactness / numDistricts,
                totalGraphCompactness / numDistricts);
    }

    private static double calculateSplitCountyScore() {
        // TODO: Implement
        return 0.0;
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

        Compactness compactnessAvg = getAvgCompactness(districts);

        double populationEqualityAvg = totalPopulationEquality / numDistricts;
        Deviation deviationFromEnactedAvg = totalDeviationFromEnacted.getAverage(numDistricts);
        Deviation deviationFromAverageAvg = totalDeviationFromAverage.getAverage(numDistricts);

        double splitCountyScore = calculateSplitCountyScore();

        return new DistrictingMeasures(compactnessAvg,
                populationEqualityAvg, splitCountyScore,
                deviationFromEnactedAvg, deviationFromAverageAvg);
    }
}
