package cse416.spring.helperclasses;

import cse416.spring.models.county.County;
import cse416.spring.models.district.District;
import cse416.spring.models.districting.DistrictingMeasures;
import cse416.spring.models.precinct.Precinct;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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

    public GeoJsonBuilder objectiveFunctionProperties(DistrictingMeasures dm) {
        JSONObject properties = new JSONObject();
        // TODO Replace these with actual values
        properties.put("AVG_COMPACTNESS", Math.random());
        properties.put("AVG_POPULATION_EQUALITY", Math.random());
        properties.put("SPLIT_COUNTY_SCORE", Math.random());
        properties.put("TOTAL_MAJORITY_MINORITY_DISTRICTS", Math.random());
        properties.put("AVG_DEVIATION_FROM_ENACTED_DISTRICTING", Math.random());
        properties.put("AVG_DEVIATION_FROM_AVG_DISTRICTING", Math.random());
        geoJson.put("objectivefunc", properties);
        return this;
    }

    public GeoJsonBuilder buildDistricts(Collection<District> districts) throws IOException {
        geoJson = new JSONObject();
        geoJson.put("type", "FeatureCollection");
        geoJson.put("features", new JSONArray());

        // For each geometry
        for (District d : districts) {
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

    private JSONObject createPropertiesJsonObject(District d) {
        // TODO: Add properties
        JSONObject properties = new JSONObject();
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
