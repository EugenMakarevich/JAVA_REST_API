package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.coherentsolutions.aqa.java.api.makarevich.factory.UserFactory.generateRandomUser;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateUserTest extends TestBase {
    //What if pack to users in ArrayList<User>
    //And then turn them into an json?
    @Test
    public void updateUserTest() {
        //Add new user to the DB (old user)
        User user = generateRandomUser();
        userService.createUser(user);
        System.out.println(user);
        //Create new user, based on the old one
        User updUser = user.clone();
        updUser.setName("Updated User");
        //Put two users into ArrayList<User>
        ArrayList<User> users = new ArrayList<>();
        users.add(updUser);
        users.add(user);
        System.out.println(users);
        ArrayList<User> allUsers = userService.getUsers();
        System.out.println(allUsers)
        //Update user
        userService.updateUser(users, SC_OK);
        ArrayList<User> allUsers = userService.getUsers();
        System.out.println(allUsers);
        assertTrue(allUsers.contains(updUser), "User is not present");
        assertFalse(allUsers.contains(user), "User is present");
    }
}