package com.coherentsolutions.aqa.java.api.makarevich.httpClient;

import com.coherentsolutions.aqa.java.api.makarevich.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

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

    public String get(String endpoint) throws IOException {
        String token;
        try {
            token = tokenService.extractToken(tokenService.getReadToken());
        } catch (IOException e) {
            log.error("Failed to extract token", e);
            throw new RuntimeException("Failed to extract token", e);
        }

        HttpGet httpGet = new HttpGet(API_REQUEST_URI + endpoint);
        httpGet.addHeader("Authorization", "Bearer " + token);

        try (CloseableHttpResponse response = client.execute(httpGet)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= HTTP_OK_MIN && statusCode <= HTTP_OK_MAX) {
                return EntityUtils.toString(response.getEntity());
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
}
