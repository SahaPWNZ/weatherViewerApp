package com.example.weatherviewerapp.services;

import com.example.weatherviewerapp.dao.SessionDAO;
import com.example.weatherviewerapp.dto.UserResponseDTO;
import com.example.weatherviewerapp.dto.UserSessionDTO;
import com.example.weatherviewerapp.entity.User;
import com.example.weatherviewerapp.entity.UserSession;
import com.example.weatherviewerapp.utils.MappingUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.UUID;

public class CookieService {
    private static final SessionDAO sessionDAO = new SessionDAO();
    private static final int COOKIE_LIFETIME_EXT = 1800000;

    public Cookie getSessionCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;

        } else {
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName() + "   " + cookie.getValue());
            }
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("sessionId"))
                    .findFirst()
                    .orElse(null);
        }
    }

    public boolean isCookieInDB(Cookie cookie) {
        return sessionDAO.findById(cookie.getValue()).isPresent();
    }

    public User getUserIdForCookie(Cookie cookie) {
        return sessionDAO.findById(cookie.getValue()).get().getUser();
    }

    public void updateUserSession(Cookie cookie) {
        UserSession userSession = sessionDAO.findById(cookie.getValue()).get();
        userSession.setTimestamp(new Timestamp(System.currentTimeMillis() + COOKIE_LIFETIME_EXT));
        sessionDAO.save(userSession);
    }

    public void deliteCookie(Cookie cookie) {
        sessionDAO.delete(cookie.getValue());
    }

    public Cookie getCookieForNewSession(UserResponseDTO userResponseDTO) {
        UserSession userSession = AddUserSession(userResponseDTO.getId());
        Cookie cookie = new Cookie("sessionId", userSession.getId());
        cookie.setMaxAge(1800);
        return cookie;
    }

    private UserSession AddUserSession(Long id) {
        UserSessionDTO userSessionDTO = UserSessionDTO.builder()
                .GUID(UUID.randomUUID().toString())
                .userId(id)
                .timestamp(new Timestamp(System.currentTimeMillis() + 1800000)) //текущая дата + час (3600000
                .build();
        return sessionDAO.save(MappingUtils.toUserSessionFromDTO(userSessionDTO));
    }

}
