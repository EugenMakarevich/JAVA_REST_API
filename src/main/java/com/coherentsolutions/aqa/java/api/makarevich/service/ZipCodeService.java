package com.coherentsolutions.aqa.java.api.makarevich.service;

import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpClientBase;
import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpResponseWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.API_ZIPCODES_ENDPOINT;
import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.API_ZIPCODES_EXPAND_ENDPOINT;
import static org.apache.http.HttpStatus.SC_CREATED;

public class ZipCodeService {
    private HttpClientBase httpClientBase;
    private ObjectMapper objectMapper;

    public ZipCodeService() {
        httpClientBase = new HttpClientBase();
        objectMapper = new ObjectMapper();
    }

    public ArrayList<String> getZipCodes() {
        try {
            HttpResponseWrapper response = httpClientBase.get(API_ZIPCODES_ENDPOINT);
            if (response.getStatusCode() != SC_CREATED) {//TODO: change back to SC_OK
                throw new RuntimeException("Unexpected response status: " + response.getStatusCode());
            }
            return response.getReponseBodyAsArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to get zip codes", e);
        }
    }

    public HttpResponseWrapper addZipCode(String... zipCode) {
        try {
            String jsonArray = objectMapper.writeValueAsString(List.of(zipCode));
            HttpResponseWrapper response = httpClientBase.post(API_ZIPCODES_EXPAND_ENDPOINT, jsonArray);
            if (response.getStatusCode() != SC_CREATED) {
                throw new RuntimeException("Unexpected response status: " + response.getStatusCode());
            }
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert zip code to JSON", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}