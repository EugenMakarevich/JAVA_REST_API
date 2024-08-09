package com.coherentsolutions.aqa.java.api.makarevich.service;

import com.coherentsolutions.aqa.java.api.makarevich.constants.Scope;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.*;
import static com.coherentsolutions.aqa.java.api.makarevich.constants.Scope.READ;
import static com.coherentsolutions.aqa.java.api.makarevich.constants.Scope.WRITE;
import static io.restassured.RestAssured.given;

@Slf4j
public class TokenService {
    @Step("Get write token")
    public static String getWriteToken() {
        return getBearerToken(WRITE);
    }

    @Step("Get read token")
    public static String getReadToken() {
        return getBearerToken(READ);
    }

    @Step("Get response with token")
    private static Response getToken(Scope scope) {
        return given()
                .param("grant_type", "client_credentials")
                .param("scope", scope.name().toLowerCase())
                .auth()
                .basic(API_USERNAME, API_PASSWORD)
                .when()
                .post(API_REQUEST_URI + API_TOKEN_ENDPOINT);
    }

    private static String getBearerToken(Scope scope) {
        return extractToken(getToken(scope).getBody().asString());
    }

    @Step("Extract token from response body")
    private static String extractToken(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
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