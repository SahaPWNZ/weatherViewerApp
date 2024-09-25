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
@WebFilter(urlPatterns = {"/sign-in.html", "/sign-on.html"})
public class AuthenticationFilter extends HttpFilter {

    private final CookieService cookieService = new CookieService();
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        log.info("Запуск фильтра Куки для /sign-in or /sign-on");
        Cookie cookie = cookieService.getCookie(req);
        
        if (cookie != null && cookieService.isCookieInDB(cookie)) {
            log.info("Куки в бд - редирект на main.html");
            res.sendRedirect("main.html");
        } else {
            log.info("Нужных куки нет или они равны налл");
            chain.doFilter(req, res);
        }
    }
}
