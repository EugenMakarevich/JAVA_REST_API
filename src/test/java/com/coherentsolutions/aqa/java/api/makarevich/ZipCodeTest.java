package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.httpClient.HttpMethods;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.coherentsolutions.aqa.java.api.makarevich.configuration.Configuration.API_ZIPCODES_ENDPOINT;

public class ZipCodeTest {
    private HttpMethods httpMethods = new HttpMethods();

    @Test()//Add group parameter for Zip Codes
    public void testGetZipCodes() throws IOException {
        String zipcodes = httpMethods.get(API_ZIPCODES_ENDPOINT);
        //Need to verify that response status 200 then do one more verification
        System.out.println(zipcodes);
    }


}
