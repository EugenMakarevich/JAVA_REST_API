package com.coherentsolutions.aqa.java.api.makarevich;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.coherentsolutions.aqa.java.api.makarevich.factory.UserFactory.generateRandomZipCode;
import static com.coherentsolutions.aqa.java.api.makarevich.factory.UserFactory.isDuplicatedZipcodesPresent;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZipCodeTest extends TestBase {
    @Test
    public void getZipCodesTest() {
        ArrayList<String> zipCodes = zipCodeService.getZipCodes();
        assertFalse(zipCodes.isEmpty(), "List of zip codes is empty");
    }

    @Test
    public void addZipCodeTest() {
        String zipcode = generateRandomZipCode();
        zipCodeService.addZipCode(zipcode);
        ArrayList<String> zipCodes = zipCodeService.getZipCodes();
        assertTrue(zipCodes.contains(zipcode), "Zip code was not added");
    }

    @Test
    public void addAvailableDuplicatedZipCodesTest() {
        String zipCode = generateRandomZipCode();
        zipCodeService.addZipCode(zipCode, zipCode);
        ArrayList<String> zipCodes = zipCodeService.getZipCodes();
        assertFalse(isDuplicatedZipcodesPresent(zipCodes, zipCode), "Duplicated zip codes present");
    }

    @Test
    public void addAlreadyExistsZipCodesTest() {
        String zipCode = generateRandomZipCode();
        zipCodeService.addZipCode(zipCode);
        zipCodeService.addZipCode(zipCode);
        ArrayList<String> zipCodes = zipCodeService.getZipCodes();
        assertFalse(isDuplicatedZipcodesPresent(zipCodes, zipCode), "Duplicated zip codes present");
    }
}