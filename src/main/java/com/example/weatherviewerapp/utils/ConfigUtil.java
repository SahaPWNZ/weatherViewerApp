package com.example.weatherviewerapp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalArgumentException("Config file not found");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed to load configuration file");
        }
    }

    public static String getApiKey() {
        return properties.getProperty("api.id");
    }

    public static int getCookieLifetimeExt() {
        return Integer.parseInt(properties.getProperty("COOKIE.LIFETIME.EXT"));
    }

    public static int getCookieAge(){
        return Integer.parseInt(properties.getProperty("COOKIE.AGE"));
    }
//    public static String getDbHost() {
//        return properties.getProperty("DB.HOST");
//    }
//    public static String getDbPort() {
//        return properties.getProperty("DB.PORT");
//    }
//    public static String getDbUser() {
//        return properties.getProperty("DB.USER");
//    }
//    public static String getDbPass() {
//        return properties.getProperty("DB.PASSWORD");
//    }
//    public static String getDbName() {
//        return properties.getProperty("DB.NAME");
//    }
}
