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
        int numberOfUsersUploaded = 3;
        int numberOfUsersCreated = 3;
        ArrayList<User> usersBeforeUpload = userService.createMultipleUsers(numberOfUsersCreated);
        System.out.println(usersBeforeUpload);
        File json = new File(TEST_DATA_PATH + "validUsers.json");
        HttpResponseWrapper response = userService.uploadUser(json, SC_CREATED);
        //Create method that parse the json file
        // and returns the ArrayList<User>
        System.out.println(userService.getUsers());
        assertEquals(response.getResponseBody(), UPLOAD_RESPONSE_BODY + numberOfUsersUploaded);
    }
}