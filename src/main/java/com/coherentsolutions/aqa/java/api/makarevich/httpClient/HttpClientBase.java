package com.coherentsolutions.aqa.java.api.makarevich.httpClient;

import com.coherentsolutions.aqa.java.api.makarevich.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.API_REQUEST_URI;

@Slf4j
public class HttpClientBase {
    private final TokenService tokenService;
    private CloseableHttpClient client;

    public HttpClientBase() {
        HttpClient httpClient = HttpClient.getInstance();
        client = httpClient.getHttpClient();
        tokenService = TokenService.getInstance();
    }

    public HttpResponseWrapper get(String endpoint) throws IOException {
        String token = tokenService.extractToken(tokenService.getReadToken());
        HttpGet httpGet = new HttpGet(API_REQUEST_URI + endpoint);
        httpGet.addHeader("Authorization", "Bearer " + token);
        return getResponse(httpGet);
    }

    public HttpResponseWrapper post(String endpoint, String json) throws IOException {
        String token = tokenService.extractToken(tokenService.getWriteToken());
        HttpPost httpPost = new HttpPost(API_REQUEST_URI + endpoint);
        httpPost.addHeader("Authorization", "Bearer " + token);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(new StringEntity(json));
        return getResponse(httpPost);
    }

    private HttpResponseWrapper getResponse(HttpUriRequest request) throws IOException {
        try (CloseableHttpResponse response = client.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());
            return new HttpResponseWrapper(statusCode, responseBody);
        } catch (IOException e) {
            log.error("Error executing request", e);
            throw e;
        }
    }
}