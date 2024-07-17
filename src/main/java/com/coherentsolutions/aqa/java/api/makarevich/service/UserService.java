package com.coherentsolutions.aqa.java.api.makarevich.service;

import com.coherentsolutions.aqa.java.api.makarevich.constants.UserSex;
import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpClientBase;
import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpResponseWrapper;
import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.API_USER_ENDPOINT;
import static com.coherentsolutions.aqa.java.api.makarevich.factory.UserFactory.*;
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

    public ArrayList<User> getUsers(Integer olderThan, String sex, Integer youngerThan) {
        try {
            Map<String, String> queryParams = queryParamsConstructor(olderThan, sex, youngerThan);

            HttpResponseWrapper response = httpClientBase.get(API_USER_ENDPOINT, queryParams);
            if (response.getStatusCode() != SC_OK) {
                throw new RuntimeException("Unexpected response status: " + response.getStatusCode());
            }
            return objectMapper.readValue(response.getResponseBody(), new TypeReference<ArrayList<User>>() {
            });
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to get users", e);
        }
    }

    public ArrayList<User> getUsers() {
        return getUsers(null, null, null);
    }

    public ArrayList<User> getUsersOlderThan(Integer olderThan) {
        return getUsers(olderThan, null, null);
    }

    public ArrayList<User> getUsersYoungerThan(Integer youngerThan) {
        return getUsers(null, null, youngerThan);
    }

    public ArrayList<User> getUsersBySex(UserSex sex) {
        return getUsers(null, String.valueOf(sex), null);
    }

    public User getRandomUser() {
        ArrayList<User> users = getUsers();
        if (users.isEmpty()) {
            log.info("User list is empty");
            return null;
        }
        return users.get(new Random().nextInt(users.size()));
    }

    public boolean isUserAdded(User user) {
        return getUsers().contains(user);
    }

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

    private Map<String, String> queryParamsConstructor(Integer olderThan, String sex, Integer youngerThan) {
        Map<String, String> queryParams = new HashMap<>();
        if (olderThan != null) {
            queryParams.put("olderThan", String.valueOf(olderThan));
        }
        if (sex != null) {
            queryParams.put("sex", sex);
        }
        if (youngerThan != null) {
            queryParams.put("youngerThan", String.valueOf(youngerThan));
        }
        return queryParams;
    }
}