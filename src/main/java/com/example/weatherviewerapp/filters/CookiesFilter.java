package com.example.weatherviewerapp.filters;

import com.example.weatherviewerapp.services.CookieService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/index.html"})
public class CookiesFilter extends HttpFilter {
    private final CookieService cookieService = new CookieService();

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        Cookie cookie = cookieService.getCookie(req);

        if (cookie == null || !cookieService.isCookieInDB(cookie)) {
            res.sendRedirect("sign-in.html");
        } else {
            cookieService.updateUserSession(cookie);
            doFilter(req, res, chain);

        }
    }
}
