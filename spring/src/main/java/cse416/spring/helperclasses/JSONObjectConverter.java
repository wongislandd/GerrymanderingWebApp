package cse416.spring.helperclasses;


import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

// https://ilhicas.com/2019/04/26/Persisting-JSONObject-Using-JPA.html

@Converter
public class JSONObjectConverter implements AttributeConverter<JSONObject, String> {

    @Override
    public String convertToDatabaseColumn(JSONObject jsonData) {
        String json;
        try{
            json = jsonData.toString();
        }
        catch (NullPointerException ex)
        {
            //extend error handling here if you want
            json = "";
        }
        return json;
    }

    @Override
    public JSONObject convertToEntityAttribute(String jsonDataAsJson) {
        JSONObject jsonData;
        try {
            jsonData = new JSONObject(jsonDataAsJson);
        } catch (JSONException ex) {
            //extend error handling here if you want
            jsonData = null;
        }
        return jsonData;
    }
}
