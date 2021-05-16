package cse416.spring.models.districting;

import cse416.spring.enums.MinorityPopulation;
import cse416.spring.helperclasses.ObjectiveFunctionWeights;
import cse416.spring.helperclasses.builders.GeoJsonBuilder;
import cse416.spring.models.district.Compactness;
import cse416.spring.models.district.Deviation;
import cse416.spring.models.district.District;
import cse416.spring.models.district.MajorityMinorityInfo;
import cse416.spring.models.job.Job;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.decimal4j.util.DoubleRounder;
import org.jgrapht.alg.matching.GreedyWeightedMatching;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;
import java.io.IOException;
import java.sql.SQLOutput;
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
    private NormalizedDistrictingMeasures normalizedMeasures;
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
            this.geoJson = new GeoJsonBuilder()
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

    static class Edge {
        public int enactedNum;
        public District generatedDistrict;

        public Edge(int enactedNum, District generatedDistrict, double weight) {
            this.enactedNum = enactedNum;
            this.generatedDistrict = generatedDistrict;
        }
    }

    private static SimpleWeightedGraph<District, Edge> getBipartiteGraph(Collection<District> districts1,
                                                                           Collection<District> districts2) throws IOException {

        SimpleWeightedGraph<District, Edge> bipartiteGraph = new SimpleWeightedGraph<>(Edge.class);
        for (District d : districts1)
            bipartiteGraph.addVertex(d);
        for (District d : districts2)
            bipartiteGraph.addVertex(d);

        // Add edges. An edge has a weight which is the area of the intersection between two districts
        for (District d1 : districts1) {
            int districtNumber = Integer.parseInt(d1.getDistrictReference().getDistrictKey());

            for (District d2 : districts2) {
                double interArea = getIntersectionArea(d1, d2);
                Edge edge = new Edge(districtNumber, d2, interArea);
                bipartiteGraph.addEdge(d1, d2, edge);
                bipartiteGraph.setEdgeWeight(d1, d2, interArea);
            }
        }
        return bipartiteGraph;
    }

    public void renumberDistricts(EnactedDistricting enactedDistricting) throws IOException {
        // Make a bipartite graph matching enacted districts to generated districts
        HashSet<District> enactedDistricts = new HashSet<>(enactedDistricting.getDistricts());
        HashSet<District> generatedDistricts = new HashSet<>(this.districts);
        List<District> districtsThatNeedHomes = new ArrayList<>();
        SimpleWeightedGraph<District, Edge> bipartiteGraph = getBipartiteGraph(enactedDistricts, generatedDistricts);

        // Match each enacted district with a generated district
        GreedyWeightedMatching<District, Edge> matcher =
                new GreedyWeightedMatching<>(bipartiteGraph, false);

        Set<Edge> matching = matcher.getMatching().getEdges();
//        System.out.println(" NUMBER OF EDGES IS " + bipartiteGraph.edgeSet().size());
//        System.out.println(" SIZE OF MATCHING IS " + matching.size());
        for (Edge e : matching) {
            District generatedDistrict = e.generatedDistrict;
            generatedDistrict.setDistrictNumber(e.enactedNum);
        }
        HashSet<Integer> numbersSeen = new HashSet<>();
        for (District d : generatedDistricts) {
            if (numbersSeen.contains(d.getDistrictNumber())) {
                districtsThatNeedHomes.add(d);
            } else {
                numbersSeen.add(d.getDistrictNumber());
            }
        }
        /* Fill in the blanks */
        if (!districtsThatNeedHomes.isEmpty()) {
            List<Integer> numbersNotSeen = new ArrayList<>();
            for (int i=1;i<=generatedDistricts.size();i++) {
                if (!numbersSeen.contains(i)) {
                    numbersNotSeen.add(i);
                }
            }
            for (int i=0;i<districtsThatNeedHomes.size();i++) {
                districtsThatNeedHomes.get(i).setDistrictNumber(numbersNotSeen.get(i));
            }
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

    public double getMaxDeviationFromIdeal() {
        double highestScore = 0;
        for (District d : districts) {
            if (Math.abs(d.getMeasures().getPopulationDiffFromIdeal()) > highestScore) {
                highestScore = Math.abs(d.getMeasures().getPopulationDiffFromIdeal());
            }
        }
        return highestScore;
    }

    public double getAverageDeviationFromIdeal() {
        double total = 0;
        for (District d : districts) {
            total += Math.abs(d.getMeasures().getPopulationDiffFromIdeal());
        }
        return total/districts.size();
    }

    public void assignObjectiveFunctionScores(ObjectiveFunctionWeights weights) {
        double populationEquality = measures.getPopulationEqualityAvg();
        double splitCountiesScore = measures.getSplitCountiesScore();
        Deviation deviationFromAverage = measures.getDeviationFromAverageAvg();
        double areaDeviationAverage = deviationFromAverage.getAreaDev();
        double populationDeviationAverage = deviationFromAverage.getPopulationDev();

        Deviation deviationFromEnacted = measures.getDeviationFromEnactedAvg();
        double areaDeviationEnacted = deviationFromEnacted.getAreaDev();
        double populationDeviationEnacted = deviationFromEnacted.getPopulationDev();

        Compactness compactness = measures.getCompactnessAvg();
        double polsbyPopper = compactness.getPolsbyPopper();
        double populationFatness = compactness.getPopulationFatness();
        double graphCompactness = compactness.getGraphCompactness();

        double populationEqualityWeight = weights.getPopulationEquality();
        double splitCountiesScoreWeight = weights.getSplitCounties();
        double deviationAverageWeight = weights.getDeviationFromAverage();
        double deviationEnactedWeight = weights.getDeviationFromEnacted();
        double compactnessWeight = weights.getCompactness();

        double objectiveFunctionResult = (populationEqualityWeight * populationEquality) + (splitCountiesScoreWeight * splitCountiesScore) +
                (deviationAverageWeight * areaDeviationAverage) + (deviationAverageWeight * populationDeviationAverage) +
                (deviationEnactedWeight * areaDeviationEnacted) + (deviationEnactedWeight * populationDeviationEnacted) +
                (compactnessWeight * polsbyPopper) + (compactnessWeight * populationFatness) + (compactnessWeight * graphCompactness);
        this.objectiveFunctionScore = objectiveFunctionResult;
    }
}
