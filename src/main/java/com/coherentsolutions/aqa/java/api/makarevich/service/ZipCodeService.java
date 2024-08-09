package com.coherentsolutions.aqa.java.api.makarevich.service;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;

import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.*;
import static com.coherentsolutions.aqa.java.api.makarevich.service.TokenService.getReadToken;
import static com.coherentsolutions.aqa.java.api.makarevich.service.TokenService.getWriteToken;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public class ZipCodeService {
    @Step("Get zip codes")
    public ArrayList<String> getZipCodes() {
        return given()
                .header("Authorization", "Bearer " + getReadToken())
                .when()
                .get(API_REQUEST_URI + API_ZIPCODES_ENDPOINT)
                .then()
                .statusCode(SC_OK)
                .extract()
                .body()
                .as(new TypeRef<ArrayList<String>>() {
                });
    }

    @Step("Add zip codes")
    public Response addZipCode(String... zipCode) {
        return given()
                .header("Authorization", "Bearer " + getWriteToken())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(zipCode)
                .when()
                .post(API_REQUEST_URI + API_ZIPCODES_EXPAND_ENDPOINT)
                .then()
                .statusCode(SC_CREATED)
                .extract()
                .response();
    }
}