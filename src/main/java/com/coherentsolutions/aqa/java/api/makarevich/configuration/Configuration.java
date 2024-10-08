package com.coherentsolutions.aqa.java.api.makarevich.configuration;

import com.coherentsolutions.aqa.java.api.makarevich.utils.ConfigUtils;

public class Configuration {
    public static final String API_HOSTNAME = ConfigUtils.getProperty("api.hostname");
    public static final Integer API_PORT = Integer.valueOf(ConfigUtils.getProperty("api.port"));
    public static final String API_SCHEME = ConfigUtils.getProperty("api.scheme");
    public static final String API_REQUEST_URI = API_SCHEME + "://" + API_HOSTNAME + ":" + API_PORT;
    public static final String API_USERNAME = ConfigUtils.getProperty("api.username");
    public static final String API_PASSWORD = ConfigUtils.getProperty("api.password");
    public static final String API_TOKEN_ENDPOINT = ConfigUtils.getProperty("api.token.endpoint");
    public static final String API_ZIPCODES_ENDPOINT = ConfigUtils.getProperty("api.zipcodes.endpoint");
    public static final String API_ZIPCODES_EXPAND_ENDPOINT = API_ZIPCODES_ENDPOINT + ConfigUtils.getProperty("api.zipcodes.expand.endpoint");
    public static final String API_USER_ENDPOINT = ConfigUtils.getProperty("api.users.endpoint");
    public static final String API_USER_UPLOAD_ENDPOINT = API_USER_ENDPOINT + ConfigUtils.getProperty("api.users.upload.endpoint");
    public static final String TEST_DATA_PATH = ConfigUtils.getProperty("testdata.path");
    public static final String OLDER_THAN_PARAM = ConfigUtils.getProperty("api.users.olderthan.param");
    public static final String YOUNGER_THAN_PARAM = ConfigUtils.getProperty("api.users.youngerthan.param");
    public static final String SEX_PARAM = ConfigUtils.getProperty("api.users.sex.param");
}