package com.coherentsolutions.aqa.java.api.makarevich.httpClient;

import com.coherentsolutions.aqa.java.api.makarevich.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

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
        String token = tokenService.getReadToken();
        HttpGet httpGet = new HttpGet(API_REQUEST_URI + endpoint);
        httpGet.addHeader("Authorization", "Bearer " + token);
        return getResponse(httpGet);
    }

    public HttpResponseWrapper get(String endpoint, Map<String, String> queryParams) throws IOException, URISyntaxException {
        String token = tokenService.getReadToken();
        StringBuilder queryString = queryParamsBuilder(queryParams);
        HttpGet httpGet = new HttpGet(API_REQUEST_URI + endpoint + queryString);
        httpGet.addHeader("Authorization", "Bearer " + token);
        return getResponse(httpGet);
    }

    public HttpResponseWrapper post(String endpoint, String json) throws IOException {
        String token = tokenService.getWriteToken();
        HttpPost httpPost = new HttpPost(API_REQUEST_URI + endpoint);
        httpPost.addHeader("Authorization", "Bearer " + token);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(new StringEntity(json));
        return getResponse(httpPost);
    }

    public HttpResponseWrapper post(String endpoint, File users) throws IOException {
        String token = tokenService.getWriteToken();
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("file", users, ContentType.DEFAULT_BINARY, users.getName());
        HttpPost httpPost = new HttpPost(API_REQUEST_URI + endpoint);
        httpPost.addHeader("Authorization", "Bearer " + token);
        httpPost.setEntity(builder.build());
        return getResponse(httpPost);
    }

    public HttpResponseWrapper put(String endpoint, String json) throws IOException {
        String token = tokenService.getWriteToken();
        HttpPut httpPut = new HttpPut(API_REQUEST_URI + endpoint);
        httpPut.addHeader("Authorization", "Bearer " + token);
        httpPut.setHeader("Content-type", "application/json");
        httpPut.setEntity(new StringEntity(json));
        return getResponse(httpPut);
    }

    public HttpResponseWrapper delete(String endpoint, String json) throws IOException {
        String token = tokenService.getWriteToken();
        HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(API_REQUEST_URI + endpoint);
        httpDelete.addHeader("Authorization", "Bearer " + token);
        httpDelete.setHeader("Content-type", "application/json");
        httpDelete.setEntity(new StringEntity(json));
        return getResponseWithoutBody(httpDelete);
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

    private HttpResponseWrapper getResponseWithoutBody(HttpUriRequest request) throws IOException {
        try (CloseableHttpResponse response = client.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            return new HttpResponseWrapper(statusCode);
        } catch (IOException e) {
            log.error("Error executing request", e);
            throw e;
        }
    }

    private StringBuilder queryParamsBuilder(Map<String, String> queryParams) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("?");
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            if (queryString.length() != 0) {
                queryString.append("&");
            }
            queryString.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8))
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }
        return queryString;
    }
}