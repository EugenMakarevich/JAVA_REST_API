package com.coherentsolutions.aqa.java.api.makarevich.service;

import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpClientBase;
import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpResponseWrapper;
import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.API_USER_ENDPOINT;
import static org.apache.http.HttpStatus.SC_OK;

@Slf4j
public class UserService {
    private HttpClientBase httpClientBase;
    private ObjectMapper objectMapper;

    public UserService() {
        httpClientBase = new HttpClientBase();
        objectMapper = new ObjectMapper();
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

    public boolean isUserAdded(User user) {
        String users = getUsers();
        try {
            ArrayList<User> userList = objectMapper.readValue(users, new TypeReference<ArrayList<User>>() {
            });
            return userList.contains(user);
        } catch (Exception e) {
            log.error("Failed to verify if user is added", e);
            return false;
        }
    }

    public User getRandomUser() {
        String users = getUsers();
        try {
            ArrayList<User> userList = objectMapper.readValue(users, new TypeReference<ArrayList<User>>() {
            });
            if (userList.isEmpty()) {
                log.info("User list is empty");
                return null;
            }
            return userList.get(new Random().nextInt(userList.size()));
        } catch (Exception e) {
            log.error("Failed to get random user", e);
            return null;
        }
    }

    private String getUsers() {
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
}