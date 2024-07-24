package com.coherentsolutions.aqa.java.api.makarevich.service;

import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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

import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.API_REQUEST_URI;
import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.API_TOKEN_ENDPOINT;
import static org.apache.http.HttpStatus.SC_MULTIPLE_CHOICES;
import static org.apache.http.HttpStatus.SC_OK;

@Slf4j
public class TokenService {
    private static TokenService instance;
    private final CloseableHttpClient client;
    private final ObjectMapper objectMapper;

    private TokenService() {
        HttpClient httpClient = HttpClient.getInstance();
        client = httpClient.getHttpClient();
        objectMapper = new ObjectMapper();
    }

    public static synchronized TokenService getInstance() {
        if (instance == null) {
            instance = new TokenService();
        }
        return instance;
    }

    public String getWriteToken() {
        try {
            return getToken("write");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getReadToken() {
        try {
            return getToken("read");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getToken(String scope) throws IOException {
        HttpPost httpPost = new HttpPost(API_REQUEST_URI + API_TOKEN_ENDPOINT);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", "client_credentials"));
        params.add(new BasicNameValuePair("scope", scope));

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        try (CloseableHttpResponse response = client.execute(httpPost)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= SC_OK && statusCode < SC_MULTIPLE_CHOICES) {
                return extractToken(EntityUtils.toString(response.getEntity()));
            } else {
                throw new IOException("Failed to get token, status code: " + statusCode);
            }
        }
    }

    private String extractToken(String responseBody) {
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            log.error("Failed to extract token", e);
            throw new RuntimeException("Failed to extract token", e);
        }
        return jsonNode.get("access_token").asText();
    }
}

