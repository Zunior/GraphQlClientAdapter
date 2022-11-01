package com.example.GraphQl.adapters;

import com.example.GraphQl.enums.GraphQlOperation;
import com.example.GraphQl.utils.GraphQlUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.CredentialsProvider;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GraphQlFetchAdapter {

    public <T> List<T> parseGraphQlResponse(Class<T> clazz, String serviceUrl, String graphQlQuery, GraphQlOperation operation, CredentialsProvider provider) throws URISyntaxException, IOException, JSONException {
        HttpResponse httpResponse = GraphQlUtil.callGraphQLQuery(serviceUrl, addBrackets(graphQlQuery), operation, provider);
        String actualResponse = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8); // Todo: import charset

        List<T> finalList = GraphQlUtil.getDataFromGraphQlQuery(actualResponse, clazz);

        return finalList;
    }

    private String addBrackets(String query) {
        return "{" + query + "}";
    }

//    private String buildGraphQlQuery(String graphQlQueryMethod, List<String> graphQlQueryAttributes) {
//        StringBuilder graphQlQueryBuilder = new StringBuilder();
//        graphQlQueryBuilder.append("{\n");
//        graphQlQueryBuilder.append("\t");
//        graphQlQueryBuilder.append(graphQlQueryMethod);
//        graphQlQueryBuilder.append(" {\n");
//        for(String attribute : graphQlQueryAttributes) {
//            graphQlQueryBuilder.append("\t");
//            graphQlQueryBuilder.append(attribute);
//            graphQlQueryBuilder.append("\n");
//        }
//        graphQlQueryBuilder.append("\t}\n");
//        graphQlQueryBuilder.append("}");
//
//        return graphQlQueryBuilder.toString();
//    }

}
