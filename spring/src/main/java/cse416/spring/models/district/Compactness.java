package cse416.spring.models.district;

import cse416.spring.enums.CompactnessType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.algorithm.MinimumBoundingCircle;
import org.locationtech.jts.algorithm.construct.MaximumInscribedCircle;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * A data class to represent the three compactness measures of a district.
 */
@Entity(name = "Compactnesses")
@Getter
@Setter
@NoArgsConstructor
public class Compactness {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private double polsbyPopper;
    @Column
    private double populationFatness;
    @Column
    private double graphCompactness;

    public Compactness(double polsbyPopper, double populationFatness, double graphCompactness) {
        this.polsbyPopper = polsbyPopper;
        this.populationFatness = populationFatness;
        this.graphCompactness = graphCompactness;
    }

    public static double calculatePolsbyPopper(Geometry geometry) {
        double area = geometry.getArea();
        double perimeter = geometry.getLength();
        return (4 * Math.PI * area) / Math.pow(perimeter, 2);
    }

    public static double calculateFatness(Geometry geometry) {
        double inscribedRadius = MaximumInscribedCircle.getRadiusLine(geometry, 1).getLength();
        MinimumBoundingCircle boundingCircle = new MinimumBoundingCircle(geometry);
        double boundingRadius = boundingCircle.getRadius();

        /* Calculate the ratio of the areas of the two circles, which is just
           the ratios of the squares of the radii
         */
        return Math.pow(inscribedRadius, 2) / Math.pow(boundingRadius, 2);
    }

    public double getCompactness(CompactnessType type) {
        switch (type) {
            case POLSBY_POPPER:
                return polsbyPopper;
            case POPULATION_FATNESS:
                return populationFatness;
            default:
                return graphCompactness;
        }
    }
}
