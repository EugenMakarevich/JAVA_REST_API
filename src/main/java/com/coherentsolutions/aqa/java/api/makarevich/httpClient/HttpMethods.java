package com.coherentsolutions.aqa.java.api.makarevich.httpClient;

import com.coherentsolutions.aqa.java.api.makarevich.service.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.API_REQUEST_URI;

@Slf4j
public class HttpMethods {
    private static final int HTTP_OK_MIN = 200;
    private static final int HTTP_OK_MAX = 299;
    private final TokenService tokenService;
    private CloseableHttpClient client;

    public HttpMethods() {
        HttpClient httpClient = HttpClient.getInstance();
        client = httpClient.getHttpClient();
        tokenService = TokenService.getInstance();
    }

    public HttpResponseWrapper get(String endpoint) throws IOException {
        String token = tokenService.extractToken(tokenService.getReadToken());

        HttpGet httpGet = new HttpGet(API_REQUEST_URI + endpoint);
        httpGet.addHeader("Authorization", "Bearer " + token);

        try (CloseableHttpResponse response = client.execute(httpGet)) {
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());

            if (statusCode >= HTTP_OK_MIN && statusCode <= HTTP_OK_MAX) {
                return new HttpResponseWrapper(statusCode, responseBody);
            } else {
                String errorMessage = "Failed to make GET request. Status code: " + statusCode;
                log.error(errorMessage);
                throw new IOException(errorMessage);
            }
        } catch (IOException e) {
            log.error("Error executing GET request", e);
            throw e;
        }
    }

    public HttpResponseWrapper post(String endpoint, String json) throws IOException {
        String token = tokenService.extractToken(tokenService.getWriteToken());

        HttpPost httpPost = new HttpPost(API_REQUEST_URI + endpoint);
        httpPost.addHeader("Authorization", "Bearer " + token);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(new StringEntity(json));

        try (CloseableHttpResponse response = client.execute(httpPost)) {
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());
            if (statusCode >= HTTP_OK_MIN && statusCode <= HTTP_OK_MAX) {
                return new HttpResponseWrapper(statusCode, responseBody);
            } else {
                String errorMessage = "Failed to make POST request. Status code: " + statusCode;
                log.error(errorMessage);
                throw new IOException(errorMessage);
            }
        } catch (IOException e) {
            log.error("Error executing POST request", e);
            throw e;
        }
    }

    public static class HttpResponseWrapper {
        private final int statusCode;
        private final String responseBody;

        public HttpResponseWrapper(int statusCode, String responseBody) {
            this.statusCode = statusCode;
            this.responseBody = responseBody;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getResponseBody() {
            return responseBody;
        }

        public ArrayList<String> getReponseBodyAsArray() {
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayList<String> arrayList;

            try {
                arrayList = objectMapper.readValue(responseBody, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, String.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return arrayList;
        }
    }
}

