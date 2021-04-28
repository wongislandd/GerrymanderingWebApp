package cse416.spring.models.district;

import org.locationtech.jts.algorithm.MinimumBoundingCircle;
import org.locationtech.jts.algorithm.construct.MaximumInscribedCircle;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;

/**
 * A data class to represent the three compactness measures of a district.
 */
@Entity
public class Compactness {
    private double polsbyPopper;
    private double populationFatness;
    private double graphCompactness;
    private long id;

    public Compactness() {
    }

    public Compactness(double polsbyPopper, double populationFatness, double graphCompactness) {
        this.polsbyPopper = polsbyPopper;
        this.populationFatness = populationFatness;
        this.graphCompactness = graphCompactness;
    }

    @Column
    public double getPolsbyPopper() {
        return polsbyPopper;
    }

    public void setPolsbyPopper(double polsbyPopper) {
        this.polsbyPopper = polsbyPopper;
    }

    @Column
    public double getPopulationFatness() {
        return populationFatness;
    }

    public void setPopulationFatness(double populationFatness) {
        this.populationFatness = populationFatness;
    }

    @Column
    public double getGraphCompactness() {
        return graphCompactness;
    }

    public void setGraphCompactness(double graphCompactness) {
        this.graphCompactness = graphCompactness;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static double calculatePolsbyPopper(Geometry geometry) {
        double area = geometry.getArea();
        double perimeter = geometry.getLength();
        return (4 * Math.PI * area) / Math.pow(perimeter, 2);
    }

    public static double calculateFatness(Geometry geometry) {
        double inscribedRadius = MaximumInscribedCircle.getRadiusLine(
                geometry, 0).getLength();

        MinimumBoundingCircle boundingCircle = new MinimumBoundingCircle(
                geometry);
        double boundingRadius = boundingCircle.getRadius();

        /* Calculate the ratio of the areas of the two circles, which is just
           the ratios of the squares of the radii
         */
        return Math.pow(inscribedRadius, 2) / Math.pow(boundingRadius, 2);
    }
}
