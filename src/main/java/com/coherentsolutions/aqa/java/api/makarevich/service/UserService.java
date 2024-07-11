package com.coherentsolutions.aqa.java.api.makarevich.service;

import com.coherentsolutions.aqa.java.api.makarevich.constants.UserSex;
import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpClientBase;
import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpResponseWrapper;
import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.API_USER_ENDPOINT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

@Slf4j
public class UserService {
    private HttpClientBase httpClientBase;
    private ObjectMapper objectMapper;
    private Faker faker;

    public UserService() {
        httpClientBase = new HttpClientBase();
        objectMapper = new ObjectMapper();
        faker = new Faker();
    }

    public String getUsers() {
        try {
            HttpResponseWrapper response = httpClientBase.get(API_USER_ENDPOINT);
            if (response.getStatusCode() != SC_OK) {
                throw new RuntimeException("Unexpected response status: " + response.getStatusCode());
            }
            return response.getResponseBody();
        } catch (IOException e) {
            throw new RuntimeException("Failed to get zip codes", e);
        }
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

    public boolean isUserAdded(User user) {
        String users = getUsers();
        try {
            ArrayList<User> userList = objectMapper.readValue(users, new TypeReference<ArrayList<User>>() {
            });
            return userList.contains(user);
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

    public User createRandomUser(String zipcode) {
        return generateUser(zipcode);
    }

    public User createRandomUser() {
        return generateUser();
    }

    private User generateUser(Integer age, String name, UserSex sex, String zipcode) {
        if (zipcode != null) {
            return new User(age, name, sex, zipcode);
        } else {
            return new User(age, name, sex);
        }
    }

    private User generateUser(String zipcode) {
        int age = faker.number().numberBetween(10, 100);
        String name = faker.name().fullName();
        UserSex sex = getRandomUserSex();
        return generateUser(age, name, sex, zipcode);
    }

    private User generateUser() {
        return generateUser(null);
    }

    private UserSex getRandomUserSex() {
        UserSex[] values = UserSex.values();
        int index = new Random().nextInt(values.length);
        return values[index];
    }
}