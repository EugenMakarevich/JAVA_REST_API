package com.coherentsolutions.aqa.java.api.makarevich.service;

import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.*;
import static com.coherentsolutions.aqa.java.api.makarevich.factory.UserFactory.*;
import static com.coherentsolutions.aqa.java.api.makarevich.service.TokenService.getReadToken;
import static com.coherentsolutions.aqa.java.api.makarevich.service.TokenService.getWriteToken;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

@Slf4j
public class UserService {
    private ObjectMapper objectMapper;

    public UserService() {
        objectMapper = new ObjectMapper();
    }

    @Step("Get users")
    public ArrayList<User> getUsers() {
        return given()
                .header("Authorization", "Bearer " + getReadToken())
                .when()
                .get(API_REQUEST_URI + API_USER_ENDPOINT)
                .then()
                .statusCode(SC_OK)
                .extract()
                .body()
                .as(new TypeRef<ArrayList<User>>() {
                });
    }

    @Step("Get users")
    public ArrayList<User> getUsers(String key, String value) {
        return given()
                .header("Authorization", "Bearer " + getReadToken())
                .param(key, value)
                .when()
                .get(API_REQUEST_URI + API_USER_ENDPOINT)
                .then()
                .statusCode(SC_OK)
                .extract()
                .body()
                .as(new TypeRef<ArrayList<User>>() {
                });
    }

    @Step("Get random user from database")
    public User getRandomUser() {
        ArrayList<User> users = getUsers();
        if (users.isEmpty()) {
            log.info("User list is empty");
            return null;
        }
        return users.get(new Random().nextInt(users.size()));
    }

    @Step("Check if user is added")
    public boolean isUserAdded(User user) {
        return getUsers().contains(user);
    }

    @Step("Create user")
    public ValidatableResponse createUser(User user) {
        return given()
                .header("Authorization", "Bearer " + getWriteToken())
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(API_REQUEST_URI + API_USER_ENDPOINT)
                .then()
                .statusCode(SC_CREATED);
    }

    @Step("Create user")
    public ValidatableResponse createUser(User user, int statusCode) {
        return given()
                .header("Authorization", "Bearer " + getWriteToken())
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(API_REQUEST_URI + API_USER_ENDPOINT)
                .then()
                .statusCode(statusCode);
    }

    @Step("Create multiple users")
    public ArrayList<User> createMultipleUsers() {
        ArrayList<User> users = new ArrayList<>();
        int numOfUsers = new Random().nextInt(2, 5);
        for (int i = 0; i < numOfUsers; i++) {
            User user = generateRandomUser();
            createUser(user);
            users.add(user);
        }
        return users;
    }

    @Step("Update User")
    public ValidatableResponse updateUser(User userNewValues, User userToChange, int statusCode) {
        return given()
                .header("Authorization", "Bearer " + getWriteToken())
                .contentType(ContentType.JSON)
                .body(generateUserUpdateJson(userNewValues, userToChange))
                .when()
                .patch(API_REQUEST_URI + API_USER_ENDPOINT)
                .then()
                .statusCode(statusCode);
    }

    @Step("Delete user")
    public ValidatableResponse deleteUser(User user, int statusCode) {
        return given()
                .header("Authorization", "Bearer " + getWriteToken())
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .delete(API_REQUEST_URI + API_USER_ENDPOINT)
                .then()
                .statusCode(statusCode);
    }

    @Step("Upload User")
    public ValidatableResponse uploadUser(File file, int statusCode) {
        return given()
                .header("Authorization", "Bearer " + getWriteToken())
                .contentType(ContentType.MULTIPART)
                .multiPart(file)
                .when()
                .post(API_REQUEST_URI + API_USER_UPLOAD_ENDPOINT)
                .then()
                .statusCode(statusCode);
    }

    @Step("Change random required user value")
    public User changeRandomUserValue(User user) {
        User modifiedUser = user.clone();
        int attributeToChange = new Random().nextInt(3);
        switch (attributeToChange) {
            case 0:
                modifiedUser.setAge(generateRandomAge());
                break;
            case 1:
                modifiedUser.setName(generateRandomName());
                break;
            case 2:
                modifiedUser.setSex(getOppositeUserSex(user.getSex()));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + attributeToChange);
        }
        return modifiedUser;
    }

    @Step("Change user zip code to random")
    public User changeUserZipCode(User user) {
        User modifiedUser = user.clone();
        String zipCode = generateRandomZipCode();
        modifiedUser.setZipCode(zipCode);
        return modifiedUser;
    }

    @Step("Create user without required field")
    public User createUserWithoutRequiredField(User user) {
        User newUser = new User();
        int attributeToChange = new Random().nextInt(2);
        switch (attributeToChange) {
            case 0:
                newUser.setAge(user.getAge());
                newUser.setName(user.getName());
                break;
            case 1:
                newUser.setAge(user.getAge());
                newUser.setSex(user.getSex());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + attributeToChange);
        }
        return newUser;
    }

    @Step("Get array of users from json file")
    public ArrayList<User> getUsersFromJson(File json) {
        try {
            List<User> users = objectMapper.readValue(json, new TypeReference<List<User>>() {
            });
            return new ArrayList<>(users);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Step("Generate string to update user value")
    private String generateUserUpdateJson(User userWithNewValues, User userToBeChanged) {
        try {
            Object combined = new Object() {
                public User userNewValues = userWithNewValues;
                public User userToChange = userToBeChanged;
            };
            return objectMapper.writeValueAsString(combined);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert to JSON", e);
            return null;
        }
    }
}