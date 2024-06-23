package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpMethods;
import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpMethods.HttpResponseWrapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.API_ZIPCODES_ENDPOINT;
import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.API_ZIPCODES_EXPAND_ENDPOINT;
import static org.junit.jupiter.api.Assertions.*;

public class ZipCodeTest {
    private HttpMethods httpMethods = new HttpMethods();

    /*
    Scenario #1
    Given I am authorized user
    When I send GET request to /zip-codes endpoint
    Then I get 200 response code
    And I get all available zip codes in the application for now
    */

    @Test
    public void testGetZipCodes() throws IOException {
        HttpResponseWrapper response = httpMethods.get(API_ZIPCODES_ENDPOINT);
        ArrayList<String> arrayList = response.getReponseBodyAsArray();  //TODO:Create the bug: 201 status code instead 200
        assertEquals(201, response.getStatusCode(), "Expected status code to be 200");
        assertFalse(arrayList.isEmpty(), "List of zip codes is empty");
    }

    /*
    Scenario #2
    Given I am authorized user
    When I send POST request to /zip-codes/expand endpoint
    And Request body contains list of zip codes
    Then I get 201 response code
    And Zip codes from request body are added to available zip codes of application
    */

    @Test
    public void testAddZipCodes() throws IOException {
        String zipcode = "zipcode"; //TODO: Add timestamp
        HttpResponseWrapper response = httpMethods.post(API_ZIPCODES_EXPAND_ENDPOINT, zipcode);
        ArrayList<String> arrayList = response.getReponseBodyAsArray();
        assertEquals(201, response.getStatusCode(), "Expected status code to be 201");
        assertTrue(arrayList.contains(zipcode), "Zip code was not added");
    }

    /*
    Scenario #3
    Given I am authorized user
    When I send POST request to /zip-codes/expand endpoint
    And Request body contains list of zip codes
    And List of zip codes has duplications for available zip codes
    Then I get 201 response code
    And Zip codes from request body are added to available zip codes of application
    And There are no duplications in available zip codes
    Means there are duplications in the list of zip codes you are trying to add
    */

    /*
    Scenario #4
    Given I am authorized user
    When I send POST request to /zip-codes/expand endpoint
    And Request body contains list of zip codes
    And List of zip codes has duplications for already used zip codes
    Then I get 201 response code
    And Zip codes from request body are added to available zip codes of application
    And There are no duplications between available zip codes and already used zip codes
    Means you're trying to add zipcodes that are already present in DB
    */

}
