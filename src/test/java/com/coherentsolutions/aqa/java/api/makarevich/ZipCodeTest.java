package com.coherentsolutions.aqa.java.api.makarevich;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.lang.System.currentTimeMillis;
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
        String zipcode = String.valueOf(currentTimeMillis());
        zipCodeService.addZipCode(zipcode);
        ArrayList<String> zipCodes = zipCodeService.getZipCodes();
        assertTrue(zipCodes.contains(zipcode), "Zip code was not added");
    }

    @Test
    public void addAvailableDuplicatedZipCodesTest() {
        String dupZipcode = String.valueOf(currentTimeMillis());
        String uniqueZipcode = String.valueOf(currentTimeMillis() + 1);
        zipCodeService.addZipCode(dupZipcode, uniqueZipcode, dupZipcode);
        ArrayList<String> zipCodes = zipCodeService.getZipCodes();
        assertTrue(zipCodes.contains(uniqueZipcode), "Unique zip code was not added");
        assertTrue(zipCodes.contains(dupZipcode), "Duplicated zip code was not added");
        assertFalse(zipCodeService.isDuplicatedZipcodesPresent(zipCodes, dupZipcode), "Duplicated zip codes present");
    }

    @Test
    public void addAlreadyExistsZipCodesTest() {
        String existZipCode = String.valueOf(currentTimeMillis());
        zipCodeService.addZipCode(existZipCode);
        ArrayList<String> beforeZipCodes = zipCodeService.getZipCodes();
        assertTrue(beforeZipCodes.contains(existZipCode), "Zip code was not added");

        zipCodeService.addZipCode(existZipCode);
        ArrayList<String> afterZipCodes = zipCodeService.getZipCodes();
        assertFalse(zipCodeService.isDuplicatedZipcodesPresent(afterZipCodes, existZipCode), "Duplicated zip codes present");
    }
}