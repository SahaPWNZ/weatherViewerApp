package com.example.weatherviewerapp.filters;

import com.example.weatherviewerapp.services.CookieService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = {"/sign-in.html", "/sign-on.html"})
public class SignInOnFilter extends HttpFilter {
    private final CookieService cookieService = new CookieService();

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        log.info(req.getRequestURI());
        log.info("Запуск фильтра авторизации/регистрации");
        Cookie cookie = cookieService.getCookie(req);
        if (cookie == null) {
            log.info("куки равны налл");
            chain.doFilter(req, res);
        } else {
            if (!cookieService.isCookieInDB(cookie)) {
                log.info("Нужных куки нет");
                chain.doFilter(req, res);
            } else {
                log.info("Куки есть - ПЕРЕКИДЫВАЮ на main.html");
                res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                res.setHeader("Pragma", "no-cache");
                res.setDateHeader("Expires", 0);
                res.sendRedirect("main.html");
            }
        }
    }
}
