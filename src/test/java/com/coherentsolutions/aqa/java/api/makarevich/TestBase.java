package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.service.ZipCodeService;

public class TestBase {
    protected ZipCodeService zipCodeService;

    public TestBase() {
        zipCodeService = new ZipCodeService();
    }
}
