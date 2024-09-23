package com.example.weatherviewerapp.filters;

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
@WebFilter(urlPatterns = {"/main.html", "/", "/sign-in.html", "/sign-on.html"})
public class CookiesFilter extends HttpFilter {
    private final CookieService cookieService = new CookieService();
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        log.info(req.getRequestURI());
        String path = req.getRequestURI();
        Cookie cookie = cookieService.getCookie(req);
        if (path.equals("/main.html")) {
            log.info(ANSI_GREEN + "Запуск фильтра Куки для /main.html" + ANSI_RESET);
            if (cookie != null && cookieService.isCookieInDB(cookie)) {
                log.info(ANSI_GREEN + "Куки есть - обновляю время жизни куки" + ANSI_RESET);
                cookieService.updateUserSession(cookie);
                res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                res.setHeader("Pragma", "no-cache");
                res.setDateHeader("Expires", 0);
                chain.doFilter(req, res);
            } else {
                log.info("куки равны нал, или их нет в бд, перенаправляю на sign-in");
                res.sendRedirect("/sign-in.html");
            }
        } else if (path.equals("/")) {
            log.info(ANSI_GREEN + "Запуск фильтра Куки для /" + ANSI_RESET);
            if (cookie != null && cookieService.isCookieInDB(cookie)) {
                log.info(ANSI_GREEN + "Куки есть - обновляю время жизни куки" + ANSI_RESET);
                cookieService.updateUserSession(cookie);
                res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                res.setHeader("Pragma", "no-cache");
                res.setDateHeader("Expires", 0);
                res.sendRedirect("main.html");
            } else {
                log.info("куки равны нал, или их нет в бд, перенаправляю на sign-in");
                res.sendRedirect("/sign-in.html");
            }
        } else if (path.equals("/sign-in.html") || path.equals("/sign-on.html")) {
            log.info(ANSI_GREEN + "Запуск фильтра Куки для /sign-in or /sign-on" + ANSI_RESET);
            if (cookie != null && cookieService.isCookieInDB(cookie)) {
                log.info("Куки есть - ПЕРЕКИДЫВАЮ на main.html");
                res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                res.setHeader("Pragma", "no-cache");
                res.setDateHeader("Expires", 0);
                res.sendRedirect("main.html");
            } else {
                log.info("Нужных куки нет или они равны налл");
                chain.doFilter(req, res);
            }
        }

    }
}
