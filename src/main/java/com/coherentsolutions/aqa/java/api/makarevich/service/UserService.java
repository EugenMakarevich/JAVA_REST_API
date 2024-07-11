package com.coherentsolutions.aqa.java.api.makarevich.service;

import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpClientBase;
import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpResponseWrapper;
import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.API_USER_ENDPOINT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public class UserService {
    private HttpClientBase httpClientBase;
    private ObjectMapper objectMapper;

    public UserService() {
        httpClientBase = new HttpClientBase();
        objectMapper = new ObjectMapper();
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
            // 2. Convert User object to Json object using jackson
            // 3. Send post request with the User as Json
            String userJson = objectMapper.writeValueAsString(user);
            HttpResponseWrapper response = httpClientBase.post(API_USER_ENDPOINT, userJson);
            // 4. Verify the request is 201
            if (response.getStatusCode() != SC_CREATED) {
                throw new RuntimeException("Unexpected response status: " + response.getStatusCode());
            }
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert zip code to JSON", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isUserAdded(User user) {
        String users = getUsers();
        //convert String users to the User objects and put it in ArrayList<User>
        //ArrayList<User> users contains the user
        try {
            ArrayList<User> userList = objectMapper.readValue(users, new TypeReference<ArrayList<User>>() {
            });
            return userList.contains(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

/*    public void createUser(User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Convert User object to JSON string
            String userJson = objectMapper.writeValueAsString(user);

            // Create HTTP POST request
            HttpPost httpPost = new HttpPost(USER_API_URL);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(userJson));

            // Send the request and get the response
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getCode();
                String responseBody = new String(response.getEntity().getContent().readAllBytes());

                // Process the response (optional)
                System.out.println("Response status: " + statusCode);
                System.out.println("Response body: " + responseBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}

