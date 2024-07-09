package com.coherentsolutions.aqa.java.api.makarevich.httpClient;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.*;

public class HttpClient {
    private static HttpClient instance;
    private CloseableHttpClient client;

    private HttpClient() {
        initHttpClient();
    }

    public static synchronized HttpClient getInstance() {
        if (instance == null) {
            instance = new HttpClient();
        }
        return instance;
    }

    private void initHttpClient() {
        HttpHost targetHost = new HttpHost(API_HOSTNAME, API_PORT, API_SCHEME);

        BasicCredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(new AuthScope(targetHost), new UsernamePasswordCredentials(API_USERNAME, API_PASSWORD));

        client = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();
    }

    public CloseableHttpClient getHttpClient() {
        return client;
    }
}