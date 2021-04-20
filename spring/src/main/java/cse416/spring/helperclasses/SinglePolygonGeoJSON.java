package cse416.spring.helperclasses;

import com.vividsolutions.jts.geom.Coordinate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;

public class SinglePolygonGeoJSON {
    /* We put strings in of this JSON format */
    //        "type": "Feature",
    //        "geometry": {
    //        "type": "Polygon",
    //        "coordinates": [...],
    //        "properties": {...}


    JSONObject json;
    public SinglePolygonGeoJSON(Coordinate[] coordinates) {
        /* Root */
        this.json = new JSONObject();
        this.json.put("type", "Feature");

        /* Geometry */
        JSONObject geometry = new JSONObject();
        geometry.put("type", "Polygon");

        /* Not sure why GeoJSON needs it but it's there */
        JSONArray coordsWrapper = new JSONArray();
        JSONArray coords = new JSONArray();
        /* For each coordinate */
        for (int i=0;i<coordinates.length;i++) {
            JSONArray coord = new JSONArray();
            coord.put(coordinates[i].x);
            coord.put(coordinates[i].y);
            coords.put(coord);
        }
        coordsWrapper.put(coords);
        geometry.put("coordinates", coordsWrapper);
        this.json.put("geometry", geometry);

        /* Properties */
        JSONObject properties = new JSONObject();
        this.json.put("properties", properties);
    }

    public JSONObject getJSON() {
        return json;
    }
}
