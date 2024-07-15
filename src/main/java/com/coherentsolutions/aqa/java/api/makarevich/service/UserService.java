package com.coherentsolutions.aqa.java.api.makarevich.service;

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
import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.API_USER_OLDER_THAN_ENDPOINT;
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
        return getUsers().contains(user);
    }

    public User getRandomUser() {
        ArrayList<User> users = getUsers();
        if (users.isEmpty()) {
            log.info("User list is empty");
            return null;
        }
        return users.get(new Random().nextInt(users.size()));
    }

    public ArrayList<User> getUsers() {
        try {
            HttpResponseWrapper response = httpClientBase.get(API_USER_ENDPOINT);
            if (response.getStatusCode() != SC_OK) {
                throw new RuntimeException("Unexpected response status: " + response.getStatusCode());
            }
            return objectMapper.readValue(response.getResponseBody(), new TypeReference<ArrayList<User>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to get zip codes", e);
        }
    }

    public ArrayList<User> getUsers(Integer olderThan, String sex, Integer youngerThan) {
        try {
            // Construct the query parameters map
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

            // Call the get method with the parameters
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

    public ArrayList<User> getUsersOlderThan() {
        try {
            HttpResponseWrapper response = httpClientBase.get(API_USER_OLDER_THAN_ENDPOINT);
            if (response.getStatusCode() != SC_OK) {
                throw new RuntimeException("Unexpected response status: " + response.getStatusCode());
            }
            return objectMapper.readValue(response.getResponseBody(), new TypeReference<ArrayList<User>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to get zip codes", e);
        }
    }
}