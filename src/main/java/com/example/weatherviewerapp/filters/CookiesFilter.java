package com.example.weatherviewerapp.filters;

import com.example.weatherviewerapp.services.CookieService;
import com.example.weatherviewerapp.utils.ConfigUtil;
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
@WebFilter(urlPatterns = {"/index.html"})
public class CookiesFilter extends HttpFilter {
    private final CookieService cookieService = new CookieService();
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        log.info(ANSI_GREEN + "Запуск фильтра Куки" + ANSI_RESET);
        Cookie cookie = cookieService.getCookie(req);
       log.info(cookie.getValue());

        log.info(ConfigUtil.getApiKey());
        if (!cookieService.isCookieInDB(cookie)) {
            log.info(ANSI_GREEN + "Нужных куки нет, перенаправление на sign-in" + ANSI_RESET);
            res.sendRedirect("sign-in.html");
        } else {
            log.info(ANSI_GREEN + "Куки есть - обновляю время жизни куки" + ANSI_RESET);
            cookieService.updateUserSession(cookie);
            super.doFilter(req, res, chain);

        }
    }
}
