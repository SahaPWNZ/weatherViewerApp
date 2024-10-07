package com.example.weatherviewerapp.filters.cookiesFilters;

import com.example.weatherviewerapp.services.CookieService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = {"/home", "/getLocations", "/"})
public class CookiesFilter extends HttpFilter {
    private final CookieService cookieService = new CookieService();

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        String path = req.getRequestURI();
        Cookie cookie = cookieService.getSessionCookie(req);

        if (cookie == null || !cookieService.isCookieInDB(cookie)) {
            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            res.setHeader("Pragma", "no-cache");
            res.setDateHeader("Expires", 0);
            res.sendRedirect("/sign-in");

        } else
            switch (path) {
                case "/home" -> {
                    log.info("Run the Cookie filter for /home");
                    cookieService.updateUserSession(cookie);
                    chain.doFilter(req, res);
                }
                case "/getLocations" -> {
                    log.info("Run the Cookie filter for /getLocations");
                    cookieService.updateUserSession(cookie);
                    chain.doFilter(req, res);
                }
                case "/" -> {
                    log.info("Run the Cookie filter for /");
                    res.sendRedirect("/home");
                }
            }
    }
}
