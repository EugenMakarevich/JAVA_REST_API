package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import io.qameta.allure.Issue;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.TEST_DATA_PATH;
import static com.coherentsolutions.aqa.java.api.makarevich.constants.GlobalConstants.UPLOAD_RESPONSE_BODY;
import static org.apache.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UploadUsersTest extends TestBase {
    @Test
    public void uploadValidUsersTest() {
        userService.createMultipleUsers();
        File json = new File(TEST_DATA_PATH + "validUsers.json");
        ValidatableResponse response = userService.uploadUser(json, SC_CREATED);
        ArrayList<User> usersFromJson = userService.getUsersFromJson(json);
        int numberOfUsersFromJson = usersFromJson.size();
        ArrayList<User> usersAfterUpload = userService.getUsers();
        assertEquals(usersFromJson, usersAfterUpload, "User lists are not equal");
        assertEquals(response.extract().body().asString(), UPLOAD_RESPONSE_BODY + numberOfUsersFromJson, "Response body is different from expected");
    }

    @Test
    @Issue("Unexpected response status: 500")
    public void uploadUsersWithUnavailableZipCodeTest() {
        userService.createMultipleUsers();
        ArrayList<User> usersBeforeUpload = userService.getUsers();
        File json = new File(TEST_DATA_PATH + "invalidZipCodeUsers.json");
        userService.uploadUser(json, SC_FAILED_DEPENDENCY);
        ArrayList<User> usersAfterUpload = userService.getUsers();
        assertEquals(usersBeforeUpload, usersAfterUpload, "User lists are not equal");
    }

    @Test
    @Issue("Unexpected response status: 500")
    public void uploadUsersWithMissedReqiuredFieldTest() {
        userService.createMultipleUsers();
        ArrayList<User> usersBeforeUpload = userService.getUsers();
        File json = new File(TEST_DATA_PATH + "missedRequiredFieldUsers.json");
        userService.uploadUser(json, SC_CONFLICT);
        ArrayList<User> usersAfterUpload = userService.getUsers();
        assertEquals(usersBeforeUpload, usersAfterUpload, "User lists are not equal");
    }
}