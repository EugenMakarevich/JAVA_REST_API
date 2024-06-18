package com.coherentsolutions.aqa.java.api.makarevich.service;

import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.API_TOKEN_URL;

public class TokenService {
    private static TokenService instance;
    private final CloseableHttpClient client;
    private final String tokenUrl;
    private final ObjectMapper objectMapper;

    private TokenService() {
        HttpClient httpClient = HttpClient.getInstance();
        client = httpClient.getHttpClient();
        tokenUrl = API_TOKEN_URL;
        objectMapper = new ObjectMapper();
    }

    public static synchronized TokenService getInstance() {
        if (instance == null) {
            instance = new TokenService();
        }
        return instance;
    }

    public String getWriteToken() throws IOException {
        return getToken("write");
    }

    public String getReadToken() throws IOException {
        return getToken("read");
    }

    private String getToken(String scope) throws IOException {
        HttpPost httpPost = new HttpPost(tokenUrl);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", "client_credentials"));
        params.add(new BasicNameValuePair("scope", scope));

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        try (CloseableHttpResponse response = client.execute(httpPost)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode < 300) {
                return EntityUtils.toString(response.getEntity());
            } else {
                throw new IOException("Failed to get token, status code: " + statusCode);
            }
        }
    }

    public String extractToken(String responseBody) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }
}

