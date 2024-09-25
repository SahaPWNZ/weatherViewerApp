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
@WebFilter(urlPatterns = {"/main.html",})
public class CookiesFilter extends HttpFilter {
    private final OpenWeatherService openWeatherService = new OpenWeatherService();
    private final CookieService cookieService = new CookieService();


    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(req, res);

        WebContext context = new WebContext(webExchange);

        log.info(req.getRequestURI());
        String path = req.getRequestURI();
        Cookie cookie = cookieService.getCookie(req);
        if (path.equals("/main.html")) {
            log.info("Запуск фильтра Куки для /main.html");
            if (cookie != null && cookieService.isCookieInDB(cookie)) {
                log.info("Куки есть - обновляю время жизни куки");
                cookieService.updateUserSession(cookie);

                log.info(String.valueOf(cookieService.getUserIdForCookie(cookie).getId()));
                var weatherCards = openWeatherService.findAllWeatherCards(cookieService.getUserIdForCookie(cookie).getId());
                context.setVariable("weatherCards", weatherCards);
                templateEngine.process("main.html", context, res.getWriter());

                //                res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//                res.setHeader("Pragma", "no-cache");
//                res.setDateHeader("Expires", 0);
//                chain.doFilter(req, res);
            } else {
                log.info("куки равны нал, или их нет в бд, перенаправляю на sign-in");
                res.sendRedirect("/sign-in.html");
            }
        }

    }
}
