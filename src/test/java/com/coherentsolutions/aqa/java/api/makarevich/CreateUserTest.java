package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.constants.UserSex;
import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import org.junit.jupiter.api.Test;

public class CreateUserTest extends TestBase {
    @Test
    public void createUserTest() {
        User user = new User(21, "Peter", UserSex.MALE, "12345");
        // 1. Create fake user
        // 2. Convert User object to Json object using jackson
        // 3. Send post request with the User as Json
        // 4. Verify the request is 201
        // 5. Send get request to get all users
        // 6. Verify the User is added
    }
}