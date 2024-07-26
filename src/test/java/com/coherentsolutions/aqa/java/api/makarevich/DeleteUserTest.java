package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.Test;

import static com.coherentsolutions.aqa.java.api.makarevich.factory.UserFactory.generateRandomUser;
import static com.coherentsolutions.aqa.java.api.makarevich.factory.UserFactory.generateRandomZipCode;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteUserTest extends TestBase {
    @Test
    @Issue("Unexpected response status: 201")
    public void deleteUserWithAllFieldsTest() {
        String zipcode = generateRandomZipCode();
        zipCodeService.addZipCode(zipcode);
        User user = generateRandomUser(zipcode);
        userService.createUser(user);
        userService.deleteUser(user, SC_NO_CONTENT);
        assertFalse(userService.isUserAdded(user), "User is not deleted");
        assertTrue(zipCodeService.getZipCodes().contains(zipcode), "Zip code is not added");
    }

    @Test
    @Issue("User is not deleted ==> expected: <false> but was: <true>")
    public void deleteUserWithRequiredFieldsTest() {
        String zipcode = generateRandomZipCode();
        zipCodeService.addZipCode(zipcode);
        User user = generateRandomUser(zipcode);
        userService.createUser(user);
        User userWithoutZipCode = user.clone();
        userWithoutZipCode.setZipCode(null);
        userService.deleteUser(userWithoutZipCode, SC_NO_CONTENT);
        assertFalse(userService.isUserAdded(user), "User is not deleted");
        assertTrue(zipCodeService.getZipCodes().contains(zipcode), "Zip code is not added");
    }

    @Test
    public void deleteUserWithoutRequiredFieldTest() {
        User user = generateRandomUser();
        userService.createUser(user);
        User userWithoutRequiredField = userService.createUserWithoutRequiredField(user);
        userService.deleteUser(userWithoutRequiredField, SC_CONFLICT);
        assertTrue(userService.isUserAdded(user), "User is deleted");
    }
}