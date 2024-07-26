package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.coherentsolutions.aqa.java.api.makarevich.factory.UserFactory.generateRandomUser;
import static org.apache.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateUserTest extends TestBase {
    @Test
    public void updateUserTest() {
        User userToChange = generateRandomUser();
        userService.createUser(userToChange);
        User userNewValues = userService.changeRandomUserValue(userToChange);
        userService.updateUser(userNewValues, userToChange, SC_OK);
        ArrayList<User> users = userService.getUsers();
        assertTrue(users.contains(userNewValues), "User is not present");
        assertFalse(users.contains(userToChange), "User is present");
    }

    @Test
    @Issue("User is not present ==> expected: <true> but was: <false>")
    public void updateUserWithUnavailableZipCode() {
        User userToChange = generateRandomUser();
        userService.createUser(userToChange);
        User userNewValues = userService.changeUserZipCode(userToChange);
        userService.updateUser(userNewValues, userToChange, SC_FAILED_DEPENDENCY);
        ArrayList<User> users = userService.getUsers();
        assertTrue(users.contains(userToChange), "User is not present");
        assertFalse(users.contains(userNewValues), "User is present");
    }

    @Test
    @Issue("User is not present ==> expected: <true> but was: <false>")
    public void updateUserWithMissingRequiredFields() {
        User userToChange = generateRandomUser();
        userService.createUser(userToChange);
        User userNewValues = userService.createUserWithoutRequiredField(userToChange);
        userService.updateUser(userNewValues, userToChange, SC_CONFLICT);
        ArrayList<User> users = userService.getUsers();
        assertTrue(users.contains(userToChange), "User is not present");
        assertFalse(users.contains(userNewValues), "User is present");
    }
}