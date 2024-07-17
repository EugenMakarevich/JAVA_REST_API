package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.coherentsolutions.aqa.java.api.makarevich.factory.UserFactory.generateRandomUser;
import static org.apache.http.HttpStatus.SC_OK;
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
}