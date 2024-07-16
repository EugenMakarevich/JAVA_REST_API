package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.constants.UserSex;
import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.coherentsolutions.aqa.java.api.makarevich.factory.UserFactory.generateRandomAge;
import static com.coherentsolutions.aqa.java.api.makarevich.factory.UserFactory.getRandomUserSex;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GetUsersTest extends TestBase {
    @Test
    public void getUsersTest() {
        ArrayList<User> users = userService.getUsers();
        assertFalse(users.isEmpty(), "List of the Users is empty");
    }

    @Test
    public void getUsersOlderThanTest() {
        int age = generateRandomAge();
        ArrayList<User> users = userService.getUsersOlderThan(age);
        assertFalse(users.isEmpty(), "List of the Users is empty");
    }

    @Test
    public void getUsersYoungerThanTest() {
        int age = generateRandomAge();
        ArrayList<User> users = userService.getUsersYoungerThan(age);
        assertFalse(users.isEmpty(), "List of the Users is empty");
    }

    @Test
    public void getUsersBySex() {
        UserSex sex = getRandomUserSex();
        ArrayList<User> users = userService.getUsersBySex(sex);
        assertFalse(users.isEmpty(), "List of the Users is empty");
    }
}