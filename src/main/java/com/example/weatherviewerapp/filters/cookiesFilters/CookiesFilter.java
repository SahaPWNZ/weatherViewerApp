package com.example.weatherviewerapp.filters.cookiesFilters;

import com.example.weatherviewerapp.dto.UserRequestDTO;
import com.example.weatherviewerapp.dto.api.LocationResponseDTO;
import com.example.weatherviewerapp.listner.ThymeleafConfiguration;
import com.example.weatherviewerapp.services.CookieService;
import com.example.weatherviewerapp.services.OpenWeatherService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;


@Slf4j
@WebFilter(urlPatterns = {"/home", "/getLocations"})
public class CookiesFilter extends HttpFilter {
    private final CookieService cookieService = new CookieService();

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        String path = req.getRequestURI();
        Cookie cookie = cookieService.getCookie(req);

        if (cookie== null ||!cookieService.isCookieInDB(cookie)) {
            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            res.setHeader("Pragma", "no-cache");
            res.setDateHeader("Expires", 0);
            res.sendRedirect("/sign-in");
        } else {
            if (path.equals("/home")) {
                log.info("Запуск фильтра Куки для /home");
                cookieService.updateUserSession(cookie);
                chain.doFilter(req, res);

            } else if (path.equals("/getLocations")) {
                log.info("Запуск фильтра Куки для /getLoctions");
                cookieService.updateUserSession(cookie);
                chain.doFilter(req, res);
            }
        }
    }
}
