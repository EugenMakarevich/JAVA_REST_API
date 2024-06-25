package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpMethods;
import com.coherentsolutions.aqa.java.api.makarevich.service.ZipCodeService;

public class TestBase {
    protected HttpMethods httpMethods;
    protected ZipCodeService zipCodeService;

    public TestBase() {
        httpMethods = new HttpMethods();
        zipCodeService = new ZipCodeService();
    }
}
