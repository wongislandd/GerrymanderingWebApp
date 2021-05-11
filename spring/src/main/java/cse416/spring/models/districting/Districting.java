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
import org.decimal4j.util.DoubleRounder;
import org.hibernate.annotations.Fetch;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.matching.HopcroftKarpMaximumCardinalityBipartiteMatching;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

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
    @Transient
    private GeoJsonBuilder geoJson = null;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<District> districts;

    public Districting(Job job, ArrayList<District> districts) throws IOException {
        this.job = job;
        this.measures = new DistrictingMeasures(districts);
        this.districts = districts;
    }

    public GeoJsonBuilder getGeoJson() throws IOException {
        if (geoJson == null) {
            geoJson = new GeoJsonBuilder()
                    .buildDistricts(districts)
                    .id(id);
        }
        return geoJson;
    }

    private static double getIntersectionArea(District d1, District d2) throws IOException {
        Geometry g1 = d1.getGeometry();
        Geometry g2 = d2.getGeometry();
        return g1.intersection(g2).getArea();
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
       // System.out.println("Renumbered districts: ");

        for (District enactedDistrict : enactedDistricts) {
            List<District> neighborList = Graphs.neighborListOf(matching, enactedDistrict);
            District generatedDistrict = neighborList.get(0);
            int districtNumber = Integer.parseInt(enactedDistrict.getDistrictReference().getDistrictKey());
            generatedDistrict.setDistrictNumber(districtNumber);
            //System.out.println("    " + districtNumber);
        }
    }

    public int getMMDistrictsCount(MinorityPopulation minority, double threshold) {
        int count = 0;
        for (District district : districts) {
            MajorityMinorityInfo mmInfo = district.getMeasures().getMajorityMinorityInfo();
            if (mmInfo.isMajorityMinorityDistrict(minority, threshold)) {
                count++;
                district.getMeasures().setMajorityMinority(true);
            } else {
                district.getMeasures().setMajorityMinority(false);
            }
        }
        return count;
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

    public ArrayList<Double> getMinorityPointData(MinorityPopulation minority) {
        ArrayList<Double> pointData = new ArrayList<>();
        ArrayList<District> orderedDistricts = getMinorityOrderedDistricts(minority);
        for (District orderedDistrict : orderedDistricts) {
            double minorityPercentage = orderedDistrict.getMeasures().getMajorityMinorityInfo().getMinorityPercentage(minority);
            pointData.add(DoubleRounder.round(minorityPercentage, 5));
        }
        return pointData;
    }


    public void assignObjectiveFunctionScores(ObjectiveFunctionWeights weights) {
        double totalObjectiveFunctionScore = 0;
        for (District d : districts) {
            d.assignObjectiveFunctionScore(weights);
            totalObjectiveFunctionScore += d.getObjectiveFunctionScore();
        }
        this.objectiveFunctionScore = totalObjectiveFunctionScore / districts.size();
    }
}
