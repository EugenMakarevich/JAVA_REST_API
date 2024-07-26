package com.coherentsolutions.aqa.java.api.makarevich.httpClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class HttpResponseWrapper {
    private int statusCode;
    private String responseBody;

    public HttpResponseWrapper(int statusCode) {
        this.statusCode = statusCode;
    }

    public HttpResponseWrapper(int statusCode, String responseBody) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    @Step("Get response body as array")
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
