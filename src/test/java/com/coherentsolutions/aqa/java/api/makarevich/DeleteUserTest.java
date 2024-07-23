package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.coherentsolutions.aqa.java.api.makarevich.factory.UserFactory.generateRandomUser;
import static com.coherentsolutions.aqa.java.api.makarevich.factory.UserFactory.generateRandomZipCode;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteUserTest extends TestBase {
    @Test
    public void deleteUserTest() {
        //Generate random zipcode and add it to the list of available zip codes
        String zipcode = generateRandomZipCode();
        zipCodeService.addZipCode(zipcode);
        ArrayList<String> zipCodes = zipCodeService.getZipCodes();
        assertTrue(zipCodes.contains(zipcode), "Zip code was not added");
        //Generate random user and add it with zip code
        User user = generateRandomUser(zipcode);
        userService.createUser(user);
        System.out.println(userService.getUsers());
        assertTrue(userService.isUserAdded(user), "User is not added");
        assertFalse(zipCodeService.getZipCodes().contains(zipcode), "Zip code is not removed");
        //Delete user
        userService.deleteUser(user, SC_NO_CONTENT);
        assertFalse(userService.isUserAdded(user), "User is not deleted");
        assertTrue(zipCodes.contains(zipcode), "Zip code was not added");
    }
}
