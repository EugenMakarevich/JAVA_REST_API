package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.constants.UserSex;
import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class GetUsersTest extends TestBase {
    @Test
    public void getUsersTest() {
        ArrayList<User> users = userService.getUsers();
        System.out.println(users);
        assertFalse(users.isEmpty(), "List of the Users is empty");
    }

    @Test
    public void getUsersOlderThanTest() {
        ArrayList<User> users = userService.getUsers(80, null, null);
        System.out.println(users);
        assertFalse(users.isEmpty(), "List of the Users is empty");
    }

    @Test
    public void getUserBySex() {
        ArrayList<User> users = userService.getUsers(null, String.valueOf(UserSex.MALE), null);
        System.out.println(users);
        assertFalse(users.isEmpty(), "List of the Users is empty");
    }
}
