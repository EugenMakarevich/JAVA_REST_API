package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.constants.UserSex;
import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.lang.System.currentTimeMillis;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateUserTest extends TestBase {
    @Test
    public void createUserTest() {
        //Add new zip code and save it to the variable
        String zipcode = String.valueOf(currentTimeMillis());
        zipCodeService.addZipCode(zipcode);
        ArrayList<String> zipCodes = zipCodeService.getZipCodes();
        System.out.println(zipCodes);
        assertTrue(zipCodes.contains(zipcode), "Zip code was not added");

        //Create and add user, using zipcode
        // 1. Create fake user
        User user = new User(22, "Peter Parker", UserSex.FEMALE, zipcode);
        userService.createUser(user);
        // 5. Send get request to get all users
        userService.getUsers();
        System.out.println(userService.getUsers());
        System.out.println(user);
        // 6. Verify the User is added
        assertTrue(userService.isUserAdded(user));

        // 7. Verify specified zip code is removed from available zip codes
        ArrayList<String> zipCodesAfter = zipCodeService.getZipCodes();
        System.out.println(zipCodesAfter);
        assertFalse(zipCodesAfter.contains(zipcode), "Zip code was not removed");
    }
}