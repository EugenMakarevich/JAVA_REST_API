package com.coherentsolutions.aqa.java.api.makarevich.configuration;

import com.coherentsolutions.aqa.java.api.makarevich.utils.ConfigUtils;

public class Configuration {
    public static final String API_HOSTNAME = ConfigUtils.getProperty("api.hostname");
    public static final Integer API_PORT = Integer.valueOf(ConfigUtils.getProperty("api.port"));
    public static final String API_SCHEME = ConfigUtils.getProperty("api.scheme");
    public static final String API_USERNAME = ConfigUtils.getProperty("api.username");
    public static final String API_PASSWORD = ConfigUtils.getProperty("api.password");
    public static final String API_TOKEN_URL = ConfigUtils.getProperty("api.tokenUrl");
}
