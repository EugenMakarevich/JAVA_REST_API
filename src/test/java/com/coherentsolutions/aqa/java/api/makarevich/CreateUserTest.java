package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.constants.UserSex;
import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateUserTest extends TestBase {
    @Test
    public void createUserWithAllFieldsTest() {
        String zipcode = zipCodeService.getRandomZipCode();
        zipCodeService.addZipCode(zipcode);
        ArrayList<String> zipCodes = zipCodeService.getZipCodes();
        assertTrue(zipCodes.contains(zipcode), "Zip code is not added");

        User user = userService.createRandomUser(zipcode);
        userService.createUser(user, SC_CREATED);
        assertTrue(userService.isUserAdded(user), "User is not added");

        ArrayList<String> zipCodesAfter = zipCodeService.getZipCodes();
        assertFalse(zipCodesAfter.contains(zipcode), "Zip code is not removed");
    }

    @Test
    public void createUserWithRequiredFieldsTest() {
        User user = userService.createRandomUser();
        userService.createUser(user, SC_CREATED);
        assertTrue(userService.isUserAdded(user), "User is not added");
    }

    @Test
    public void createUserWithUnavailableZipCodeTest() {
        String zipcode = zipCodeService.getRandomZipCode();
        ArrayList<String> zipCodes = zipCodeService.getZipCodes();
        assertFalse(zipCodes.contains(zipcode), "Zip code is available");

        User user = userService.createRandomUser(zipcode);
        userService.createUser(user, SC_FAILED_DEPENDENCY);
        assertFalse(userService.isUserAdded(user), "User is added");
    }

    @Test
    public void createUserWithExistingNameAndSexTest() {
        User randomUser = userService.getRandomUser();
        String name = randomUser.getName();
        UserSex sex = randomUser.getSex();

        User user = userService.createRandomUser(name, sex);
        userService.createUser(user, SC_BAD_REQUEST);
        assertFalse(userService.isUserAdded(user), "User is added");
    }
}