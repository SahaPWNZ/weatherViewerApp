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
@WebFilter(urlPatterns = {"/sign-in", "/sign-on"})
public class AuthenticationFilter extends HttpFilter {

    private final CookieService cookieService = new CookieService();

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        log.info("Run the Cookie filter for /sign-in or /sign-on");
        Cookie cookie = cookieService.getSessionCookie(req);

        if (cookie != null && cookieService.isCookieInDB(cookie)) {
            log.info("Cookies in the database - redirect to /home");
            res.sendRedirect("/home");
        } else {
            log.info("There are no cookies or they are null");
            chain.doFilter(req, res);
        }
    }
}
