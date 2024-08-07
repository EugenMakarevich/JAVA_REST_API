package com.coherentsolutions.aqa.java.api.makarevich.service;

import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpClientBase;
import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpResponseWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.API_ZIPCODES_ENDPOINT;
import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.API_ZIPCODES_EXPAND_ENDPOINT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public class ZipCodeService {
    private HttpClientBase httpClientBase;
    private ObjectMapper objectMapper;

    public ZipCodeService() {
        httpClientBase = new HttpClientBase();
        objectMapper = new ObjectMapper();
    }

    @Step("Get zip codes")
    public ArrayList<String> getZipCodes() {
        try {
            HttpResponseWrapper response = httpClientBase.get(API_ZIPCODES_ENDPOINT);
            if (response.getStatusCode() != SC_OK) {
                throw new RuntimeException("Unexpected response status: " + response.getStatusCode());
            }
            return response.getReponseBodyAsArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to get zip codes", e);
        }
    }

    @Step("Add zip codes")
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