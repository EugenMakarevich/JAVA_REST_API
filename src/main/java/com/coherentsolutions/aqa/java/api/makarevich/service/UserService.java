package com.coherentsolutions.aqa.java.api.makarevich.service;

import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpClientBase;
import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpResponseWrapper;
import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.*;
import static com.coherentsolutions.aqa.java.api.makarevich.factory.UserFactory.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

@Slf4j
public class UserService {
    private HttpClientBase httpClientBase;
    private ObjectMapper objectMapper;

    public UserService() {
        httpClientBase = new HttpClientBase();
        objectMapper = new ObjectMapper();
    }

    @Step("Get users")
    public ArrayList<User> getUsers() {
        return given()
                .header("Authorization", "Bearer " + TokenService.getInstance().getReadToken())
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
                .header("Authorization", "Bearer " + TokenService.getInstance().getReadToken())
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
    public HttpResponseWrapper createUser(User user) {
        try {
            String userJson = objectMapper.writeValueAsString(user);
            HttpResponseWrapper response = httpClientBase.post(API_USER_ENDPOINT, userJson);
            if (response.getStatusCode() != SC_CREATED) {
                throw new RuntimeException("Unexpected response status: " + response.getStatusCode());
            }
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert zip code to JSON", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to send POST request", e);
        }
    }

    @Step("Create user and verify specified status code")
    public HttpResponseWrapper createUser(User user, int statusCode) {
        try {
            String userJson = objectMapper.writeValueAsString(user);
            HttpResponseWrapper response = httpClientBase.post(API_USER_ENDPOINT, userJson);
            if (response.getStatusCode() != statusCode) {
                throw new RuntimeException("Unexpected response status: " + response.getStatusCode());
            }
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert zip code to JSON", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to send POST request", e);
        }
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

    @Step("Update user")
    public HttpResponseWrapper updateUser(User userNewValues, User userToChange, int statusCode) {
        try {
            String userJson = generateUserUpdateJson(userNewValues, userToChange);
            HttpResponseWrapper response = httpClientBase.put(API_USER_ENDPOINT, userJson);
            if (response.getStatusCode() != statusCode) {
                throw new RuntimeException("Unexpected response status: " + response.getStatusCode());
            }
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert to JSON", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to send PUT request", e);
        }
    }

    @Step("Delete user")
    public HttpResponseWrapper deleteUser(User user, int statusCode) {
        try {
            String userJson = objectMapper.writeValueAsString(user);
            HttpResponseWrapper response = httpClientBase.delete(API_USER_ENDPOINT, userJson);
            if (response.getStatusCode() != statusCode) {
                throw new RuntimeException("Unexpected response status: " + response.getStatusCode());
            }
            return response;
        } catch (IOException e) {
            throw new RuntimeException("Failed to get users", e);
        }
    }

    @Step("Upload user")
    public HttpResponseWrapper uploadUser(File users, int statusCode) {
        try {
            HttpResponseWrapper response = httpClientBase.post(API_USER_UPLOAD_ENDPOINT, users);
            if (response.getStatusCode() != statusCode) {
                throw new RuntimeException("Unexpected response status: " + response.getStatusCode());
            }
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert zip code to JSON", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to send POST request", e);
        }
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