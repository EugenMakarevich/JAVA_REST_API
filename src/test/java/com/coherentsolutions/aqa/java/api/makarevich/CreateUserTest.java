package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateUserTest extends TestBase {
    @Test
    public void createUserTest() {
        //Add new zip code to available zip codes
        String zipcode = zipCodeService.getRandomZipCode();
        zipCodeService.addZipCode(zipcode);
        ArrayList<String> zipCodes = zipCodeService.getZipCodes();
        assertTrue(zipCodes.contains(zipcode), "Zip code is not added");

        //Create random user
        User user = userService.createRandomUser(zipcode);
        userService.createUser(user);
        userService.getUsers();
        assertTrue(userService.isUserAdded(user), "User is not added");

        //Verify specified zip code is removed from available zip codes
        ArrayList<String> zipCodesAfter = zipCodeService.getZipCodes();
        assertFalse(zipCodesAfter.contains(zipcode), "Zip code is not removed");
    }
}