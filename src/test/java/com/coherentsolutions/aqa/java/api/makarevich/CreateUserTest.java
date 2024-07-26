package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.constants.UserSex;
import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.coherentsolutions.aqa.java.api.makarevich.factory.UserFactory.generateRandomUser;
import static com.coherentsolutions.aqa.java.api.makarevich.factory.UserFactory.generateRandomZipCode;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_FAILED_DEPENDENCY;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateUserTest extends TestBase {
    @Test
    @Issue("Unexpected response status: 201")
    public void createUserWithAllFieldsTest() {
        String zipcode = generateRandomZipCode();
        zipCodeService.addZipCode(zipcode);
        User user = generateRandomUser(zipcode);
        userService.createUser(user);
        ArrayList<String> zipCodesAfter = zipCodeService.getZipCodes();
        assertTrue(userService.isUserAdded(user), "User is not added");
        assertFalse(zipCodesAfter.contains(zipcode), "Zip code is not removed");
    }

    @Test
    public void createUserWithRequiredFieldsTest() {
        User user = generateRandomUser();
        userService.createUser(user);
        assertTrue(userService.isUserAdded(user), "User is not added");
    }

    @Test
    public void createUserWithUnavailableZipCodeTest() {
        String zipcode = generateRandomZipCode();
        User user = generateRandomUser(zipcode);
        userService.createUser(user, SC_FAILED_DEPENDENCY);
        assertFalse(userService.isUserAdded(user), "User is added");
    }

    @Test
    @Issue("Unexpected response status: 201")
    public void createUserWithExistingNameAndSexTest() {
        User randomUser = userService.getRandomUser();
        String name = randomUser.getName();
        UserSex sex = randomUser.getSex();
        User user = generateRandomUser(name, sex);
        userService.createUser(user, SC_BAD_REQUEST);
        assertFalse(userService.isUserAdded(user), "User is added");
    }
}