package com.isaacandrade.urlshortenerservice.utils;

import org.springframework.stereotype.Component;

@Component
public class EnvUtil {

    public static String getString(String name) {
        return System.getenv(name);
    }

    public static Integer getInteger(String name) {
        String value = System.getenv(name);
        return value != null ? Integer.valueOf(value) : null;
    }
}