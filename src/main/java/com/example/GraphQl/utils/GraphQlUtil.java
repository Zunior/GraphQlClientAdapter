package com.example.GraphQl.utils;

import com.example.GraphQl.enums.GraphQlOperation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GraphQlUtil {

    public static <T> List<T> getDataFromGraphQlQuery(String actualResponse, Class<T> clazz) throws JSONException, JsonProcessingException {
        JSONObject responseObject = new JSONObject(actualResponse);
        JSONObject responseArrayObject = responseObject.optJSONObject("data");
        Iterator<String> keys = responseArrayObject.keys();
        if(keys.hasNext()){
            String firstKey = keys.next(); // First key in json object
            if (responseArrayObject.optJSONArray(firstKey) != null) {
                JSONArray finalResponse = responseArrayObject.optJSONArray(firstKey);
                return createListFromJsonArray(finalResponse, clazz);
            } else if (responseArrayObject.optJSONObject(firstKey) != null) {
                JSONObject finalResponse = responseArrayObject.optJSONObject(firstKey);
                return createSingletonListFromJsonObject(finalResponse, clazz);
            } else {
                return null;
            }
        } else {
            return Collections.emptyList();
        }
    }

    public static <T> List<T> createListFromJsonArray(JSONArray jsonArray, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);

        return mapper.readValue(jsonArray.toString(), type);
    }

    public static <T> List<T> createSingletonListFromJsonObject(JSONObject object, Class<T> clazz) {
        Gson gson = new Gson();
        return Collections.singletonList(gson.fromJson(object.toString(), clazz));
    }

    public static HttpResponse callGraphQLQuery(String url, String query, GraphQlOperation operation, CredentialsProvider provider)
            throws URISyntaxException, IOException {
        HttpClient client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();
        HttpPost request = new HttpPost(url);
        URI uri = new URIBuilder(request.getURI())
                .addParameter(operation.getValue(), query)
                .build();
        request.setURI(uri);
        return client.execute(request);
    }
}
