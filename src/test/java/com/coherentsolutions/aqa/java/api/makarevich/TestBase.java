package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpClientBase;
import com.coherentsolutions.aqa.java.api.makarevich.service.ZipCodeService;

public class TestBase {
    protected HttpClientBase httpClientBase;
    protected ZipCodeService zipCodeService;

    public TestBase() {
        httpClientBase = new HttpClientBase();
        zipCodeService = new ZipCodeService();
    }
}
