package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.constants.UserSex;
import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.coherentsolutions.aqa.java.api.makarevich.factory.UserFactory.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetUsersTest extends TestBase {
    @Test
    public void getUsersTest() {
        ArrayList<User> users = userService.getUsers();
        assertFalse(users.isEmpty(), "List of the Users is empty");
    }

    @Test
    public void getUsersOlderThanTest() {
        int age = generateRandomAge();
        int oneYear = 1;
        int olderAge = age + oneYear;
        int youngerAge = age - oneYear;
        User userOlderThan = generateRandomUser(olderAge);
        User userYoungerThan = generateRandomUser(youngerAge);
        userService.createUser(userOlderThan);
        userService.createUser(userYoungerThan);
        ArrayList<User> users = userService.getUsersOlderThan(age);
        assertTrue(users.contains(userOlderThan), "User is not present");
        assertFalse(users.contains(userYoungerThan), "User is present");
    }

    @Test
    public void getUsersYoungerThanTest() {
        int age = generateRandomAge();
        int oneYear = 1;
        int olderAge = age + oneYear;
        int youngerAge = age - oneYear;
        User userOlderThan = generateRandomUser(olderAge);
        User userYoungerThan = generateRandomUser(youngerAge);
        userService.createUser(userOlderThan);
        userService.createUser(userYoungerThan);
        ArrayList<User> users = userService.getUsersYoungerThan(age);
        assertTrue(users.contains(userYoungerThan), "User is not present");
        assertFalse(users.contains(userOlderThan), "User is present");
    }

    @Test
    public void getUsersBySex() {
        UserSex sex = getRandomUserSex();
        UserSex oppositeSex = getOppositeUserSex(sex);
        User userWithDefinedSex = generateRandomUser(sex);
        User userWithOppositeSex = generateRandomUser(oppositeSex);
        userService.createUser(userWithDefinedSex);
        userService.createUser(userWithOppositeSex);
        ArrayList<User> users = userService.getUsersBySex(sex);
        assertTrue(users.contains(userWithDefinedSex), "User is not present");
        assertFalse(users.contains(userWithOppositeSex), "User is present");
    }
}