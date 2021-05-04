package cse416.spring.models.districting;

import cse416.spring.enums.MinorityPopulation;
import cse416.spring.helperclasses.ObjectiveFunctionWeights;
import cse416.spring.helperclasses.builders.GeoJsonBuilder;
import cse416.spring.models.district.*;
import cse416.spring.models.job.Job;
import cse416.spring.models.precinct.Precinct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.matching.HopcroftKarpMaximumCardinalityBipartiteMatching;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

@Entity(name = "Districtings")
@Getter
@Setter
@NoArgsConstructor
public class Districting {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Job job;
    @OneToOne(cascade = CascadeType.ALL)
    private DistrictingMeasures measures;
    @Transient
    private double objectiveFunctionScore;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<District> districts;

    public Districting(Job job, ArrayList<District> districts) {
        this.job = job;
        this.measures = new DistrictingMeasures(districts);
        this.districts = districts;
    }

    public String getGeoJson() throws IOException {
        GeoJsonBuilder geoJson = new GeoJsonBuilder()
                .buildDistricts(districts)
                .objectiveFunctionProperties(measures)
                .name(Long.toString(id));
        return geoJson.toString();
    }

    private static double getIntersectionArea(District d1, District d2) throws IOException {
        Geometry g1 = d1.getGeometry();
        Geometry g2 = d2.getGeometry();
        double area = g1.intersection(g2).getArea();
        return area;
    }

    private static SimpleWeightedGraph<District, Double> getBipartiteGraph(Collection<District> districts1,
                                                                           Collection<District> districts2) throws IOException {

        SimpleWeightedGraph<District, Double> bipartiteGraph = new SimpleWeightedGraph<>(Double.class);
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
        SimpleWeightedGraph<District, Double> bipartiteGraph = getBipartiteGraph(enactedDistricts, generatedDistricts);

        // Match each enacted district with a generated district
        HopcroftKarpMaximumCardinalityBipartiteMatching<District, Double> matcher =
                new HopcroftKarpMaximumCardinalityBipartiteMatching<>(bipartiteGraph, enactedDistricts, generatedDistricts);

        Graph<District, Double> matching = matcher.getMatching().getGraph();
        for (District enactedDistrict : enactedDistricts) {
            District generatedDistrict = Graphs.neighborListOf(matching, enactedDistrict).get(0);
            int districtNumber = Integer.parseInt(enactedDistrict.getDistrictReference().getDistrictKey());
            generatedDistrict.setDistrictNumber(districtNumber);
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

    public void assignObjectiveFunctionScores(ObjectiveFunctionWeights weights) {
        double totalObjectiveFunctionScore = 0;
        for (District d : districts) {
            d.assignObjectiveFunctionScore(weights);
            totalObjectiveFunctionScore += d.getObjectiveFunctionScore();
        }
        this.objectiveFunctionScore = totalObjectiveFunctionScore / districts.size();
    }


    private static HashMap<Precinct, District> getPrecinctToDistrictMap(ArrayList<District> districts) throws IOException {
        HashMap<Precinct, District> map = new HashMap<>();

        for (District d : districts) {
            for (Precinct p : d.getPrecincts()) {
                map.put(p, d);
            }
        }
        return map;
    }

}
