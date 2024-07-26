package com.coherentsolutions.aqa.java.api.makarevich;

import io.qameta.allure.Issue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.coherentsolutions.aqa.java.api.makarevich.factory.UserFactory.generateRandomZipCode;
import static com.coherentsolutions.aqa.java.api.makarevich.factory.UserFactory.isDuplicatedZipcodesPresent;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZipCodeTest extends TestBase {
    @Test
    @Issue("Unexpected response status: 201")
    public void getZipCodesTest() {
        ArrayList<String> zipCodes = zipCodeService.getZipCodes();
        assertFalse(zipCodes.isEmpty(), "List of zip codes is empty");
    }

    @Test
    @Issue("Unexpected response status: 201")
    public void addZipCodeTest() {
        String zipcode = generateRandomZipCode();
        zipCodeService.addZipCode(zipcode);
        ArrayList<String> zipCodes = zipCodeService.getZipCodes();
        assertTrue(zipCodes.contains(zipcode), "Zip code was not added");
    }

    @Test
    @Issue("Unexpected response status: 201")
    public void addAvailableDuplicatedZipCodesTest() {
        String zipCode = generateRandomZipCode();
        zipCodeService.addZipCode(zipCode, zipCode);
        ArrayList<String> zipCodes = zipCodeService.getZipCodes();
        assertFalse(isDuplicatedZipcodesPresent(zipCodes, zipCode), "Duplicated zip codes present");
    }

    @Test
    @Issue("Unexpected response status: 201")
    public void addAlreadyExistsZipCodesTest() {
        String zipCode = generateRandomZipCode();
        zipCodeService.addZipCode(zipCode);
        zipCodeService.addZipCode(zipCode);
        ArrayList<String> zipCodes = zipCodeService.getZipCodes();
        assertFalse(isDuplicatedZipcodesPresent(zipCodes, zipCode), "Duplicated zip codes present");
    }
}