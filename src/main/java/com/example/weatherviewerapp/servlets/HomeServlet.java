package com.example.weatherviewerapp.servlets;

import com.example.weatherviewerapp.listner.ThymeleafConfiguration;
import com.example.weatherviewerapp.services.CookieService;
import com.example.weatherviewerapp.services.OpenWeatherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private final OpenWeatherService openWeatherService = new OpenWeatherService();
    private final CookieService cookieService = new CookieService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        Cookie cookie = cookieService.getSessionCookie(req);

        var weatherCards = openWeatherService.findAllWeatherCards(cookieService.getUserIdForCookie(cookie).getId());
        context.setVariable("weatherCards", weatherCards);
        templateEngine.process("main.html", context, resp.getWriter());
    }
}
