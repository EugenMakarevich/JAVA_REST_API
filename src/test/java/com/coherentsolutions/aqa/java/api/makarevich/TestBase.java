package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.service.UserService;
import com.coherentsolutions.aqa.java.api.makarevich.service.ZipCodeService;

public class TestBase {
    protected ZipCodeService zipCodeService;
    protected UserService userService;

    public TestBase() {
        zipCodeService = new ZipCodeService();
        userService = new UserService();
    }
}