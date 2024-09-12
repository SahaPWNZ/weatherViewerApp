package com.example.weatherviewerapp.services;

import com.example.weatherviewerapp.dao.SessionDAO;
import com.example.weatherviewerapp.dto.UserSessionDTO;
import com.example.weatherviewerapp.entity.UserSession;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CookieService {
    private static final SessionDAO sessionDAO = new SessionDAO();
    private static final int COOKIE_LIFETIME_EXT = 1800000;
    public void addCookie(UserSessionDTO userSessionDTO) {
//добавляет куки авторизованному пользователюэ
    }

    public Cookie getCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        } else {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("sessionId"))
                    .findFirst()
                    .orElse(null);
        }
    }

    public boolean isCookieInDB(Cookie cookie) {
        return sessionDAO.findById(cookie.getValue()).isPresent();
    }

    public void updateUserSession(Cookie cookie) {
        UserSession userSession = sessionDAO.findById(cookie.getValue()).get();
        userSession.setTimestamp(new Timestamp(System.currentTimeMillis() + COOKIE_LIFETIME_EXT));
        sessionDAO.save(userSession);
    }

    public void deliteCookie(Cookie cookie){
        sessionDAO.delete(cookie.getValue());
    }

}
