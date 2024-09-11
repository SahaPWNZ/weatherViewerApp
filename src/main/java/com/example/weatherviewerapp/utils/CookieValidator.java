package com.example.weatherviewerapp.utils;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

public class CookieValidator {
    public static Cookie CheckCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        } else {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("userID"))
                    .findFirst()
                    .orElse(null);
        }
    }
}
