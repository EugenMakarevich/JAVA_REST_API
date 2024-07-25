package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpResponseWrapper;
import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.TEST_DATA_PATH;
import static com.coherentsolutions.aqa.java.api.makarevich.constants.GlobalConstants.UPLOAD_RESPONSE_BODY;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UploadUsersTest extends TestBase {
    @Test
    public void uploadValidUsersTest() {
        userService.createMultipleUsers();
        File json = new File(TEST_DATA_PATH + "validUsers.json");
        HttpResponseWrapper response = userService.uploadUser(json, SC_CREATED);
        ArrayList<User> usersFromJson = userService.getUsersFromJson(json);
        int numberOfUsersFromJson = usersFromJson.size();
        ArrayList<User> usersAfterUpload = userService.getUsers();
        assertEquals(usersFromJson, usersAfterUpload, "User lists are not equal");
        assertEquals(response.getResponseBody(), UPLOAD_RESPONSE_BODY + numberOfUsersFromJson, "Response body is different from expected");
    }
}