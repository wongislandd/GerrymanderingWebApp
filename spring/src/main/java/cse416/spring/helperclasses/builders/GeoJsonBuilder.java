package cse416.spring.helperclasses.builders;

import cse416.spring.helperclasses.RGB;
import cse416.spring.models.county.County;
import cse416.spring.models.district.District;
import cse416.spring.models.districting.Districting;
import cse416.spring.models.districting.DistrictingMeasures;
import cse416.spring.models.precinct.Precinct;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class GeoJsonBuilder {
    public long id;
    public JSONObject geoJson;

    public GeoJsonBuilder() {
        geoJson = new JSONObject();
        geoJson.put("type", "FeatureCollection");
        geoJson.put("features", new JSONArray());
    }

    public GeoJsonBuilder buildCounties(Collection<County> counties) {
        geoJson = new JSONObject();
        geoJson.put("type", "FeatureCollection");
        geoJson.put("features", new JSONArray());

        // For each geometry
        for (County c : counties) {
            JSONObject feature = new JSONObject();
            feature.put("type", "Feature");
            feature.put("properties", createPropertiesJsonObject(c));
            feature.put("geometry", createGeometryJsonObject(c));
            geoJson.getJSONArray("features").put(feature);
        }
        return this;
    }

    public GeoJsonBuilder buildPrecincts(Collection<Precinct> precincts) {
        for (Precinct precinct : precincts) {
            put(new JSONObject(precinct.getGeoJson()));
        }
        return this;
    }

    public GeoJsonBuilder name(String name) {
        geoJson.put("name", name);
        return this;
    }

    public GeoJsonBuilder id(long id) {
        geoJson.put("id", id);
        return this;
    }

    private static Comparator<District> districtNumberComparator = new Comparator<District>() {
        @Override
        public int compare(District district, District district2) {
            if(district.getDistrictNumber() > district2.getDistrictNumber()) {
                return 1;
            } else if(district.getDistrictNumber() < district2.getDistrictNumber()) {
                return -1;
            } else {
                return 0;
            }
        }
    };


    public GeoJsonBuilder buildDistricts(Collection<District> districts) throws IOException {
        geoJson = new JSONObject();
        geoJson.put("type", "FeatureCollection");
        geoJson.put("features", new JSONArray());

        ArrayList<District> districtsOrdered = new ArrayList<>(districts);
        /* Sort the GeoJson by district number so that it's generated MapBox ID can line up with the number. */
        districtsOrdered.sort(districtNumberComparator);
        // For each geometry
        for (District d : districtsOrdered) {
            JSONObject feature = new JSONObject();
            feature.put("type", "Feature");
            feature.put("properties", createPropertiesJsonObject(d));
            feature.put("geometry", createGeometryJsonObject(d));
            geoJson.getJSONArray("features").put(feature);
        }
        return this;
    }

    private JSONObject createGeometryJsonObject(District d) throws IOException {
        Geometry g = d.getGeometry();
        Coordinate[] coords = g.getCoordinates();
        JSONArray innerCoordinates = new JSONArray();

        for (Coordinate coord : coords) {
            JSONArray entry = new JSONArray();
            entry.put(coord.x);
            entry.put(coord.y);
            innerCoordinates.put(entry);
        }

        JSONObject geometry = new JSONObject();
        JSONArray outerCoordinates = new JSONArray();
        geometry.put("type", "Polygon");
        outerCoordinates.put(innerCoordinates);
        geometry.put("coordinates", outerCoordinates);
        return geometry;
    }

    private RGB getRGBBasedOnDistrictNumber(int districtNumber) {
        switch(districtNumber) {
            case 1:
                return new RGB(47, 79, 79);
            case 2:
                return new RGB(139,69,19);
            case 3:
                return new RGB(34, 139, 34);
            case 4:
                return new RGB(75,0,130);
            case 5:
                return new RGB(255, 0,61);
            case 6:
                return  new RGB(255,215,0);
            case 7:
                return new RGB(0, 255, 176);
            case 8:
                return new RGB(0, 255, 255);
            case 9:
                return new RGB(0,0,255);
            case 10:
                return new RGB(255,0,255);
            case 11:
                return new RGB(100,149,237);
            case 12:
                return new RGB(245,222,179);
            default:
                return new RGB(255, 105, 180);
        }
    }

    private JSONObject createPropertiesJsonObject(District d) {
        JSONObject properties = new JSONObject();
        properties.put("id", d.getDistrictNumber());
        properties.put("TOTAL_POPULATION", d.getDemographics().getTP());
        properties.put("RACE_WHITE_COUNT", d.getDemographics().getWhite());
        properties.put("RACE_BLACK_COUNT", d.getDemographics().getBlack());
        properties.put("RACE_HISPANIC_COUNT", d.getDemographics().getHispanic());
        properties.put("RACE_ASIAN_COUNT", d.getDemographics().getAsian());
        properties.put("RACE_NATIVE_COUNT", d.getDemographics().getNatives());
        properties.put("RACE_PACIFIC_ISLANDER_COUNT", d.getDemographics().getPacific());
        properties.put("RACE_OTHER_COUNT", d.getDemographics().getOtherRace());
        properties.put("OBJECTIVE_FUNCTION_SCORE", d.getObjectiveFunctionScore());
        RGB coloring = getRGBBasedOnDistrictNumber(d.getDistrictNumber());
        properties.put("rgb-R", coloring.getR());
        properties.put("rgb-G", coloring.getG());
        properties.put("rgb-B", coloring.getB());
        return properties;
    }

    private JSONObject createGeometryJsonObject(County c) {
        Geometry g = c.getGeometry();
        Coordinate[] coords = g.getCoordinates();
        JSONArray innerCoordinates = new JSONArray();
        for (Coordinate coord : coords) {
            JSONArray entry = new JSONArray();
            entry.put(coord.x);
            entry.put(coord.y);
            innerCoordinates.put(entry);
        }
        JSONObject geometry = new JSONObject();
        JSONArray outerCoordinates = new JSONArray();
        geometry.put("type", "Polygon");
        outerCoordinates.put(innerCoordinates);
        geometry.put("coordinates", outerCoordinates);
        return geometry;
    }

    private JSONObject createPropertiesJsonObject(County c) {
        // TODO: Add properties
        JSONObject properties = new JSONObject();
        return properties;
    }

    public void put(JSONObject featureJSON) {
        geoJson.append("features", featureJSON);
    }

    public String toString() {
        return geoJson.toString();
    }

    public JSONObject getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(JSONObject geoJson) {
        this.geoJson = geoJson;
    }
}
