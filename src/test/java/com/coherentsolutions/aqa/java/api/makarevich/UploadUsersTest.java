package com.coherentsolutions.aqa.java.api.makarevich;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.apache.http.HttpStatus.SC_CREATED;

public class UploadUsersTest extends TestBase {
    @Test
    public void uploadValidUsersTest() {
        File json = new File("src/test/resources/validUsers.json"); //Verify the file was picked up
        userService.uploadUser(json, SC_CREATED);
        System.out.println(userService.getUsers());
    }
}
