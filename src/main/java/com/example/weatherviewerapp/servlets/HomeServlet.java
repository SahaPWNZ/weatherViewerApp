package com.example.weatherviewerapp.servlets;

import com.example.weatherviewerapp.dao.LocationsDAO;
import com.example.weatherviewerapp.listener.ThymeleafConfiguration;
import com.example.weatherviewerapp.services.CookieService;
import com.example.weatherviewerapp.services.LocationsService;
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
    private final LocationsService locationsService = new LocationsService(new LocationsDAO());
    private final CookieService cookieService = new CookieService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        Cookie cookie = cookieService.getSessionCookie(req);
        var weatherCards = locationsService.findAllWeatherCards(cookieService.getUserForCookie(cookie).getId());

        context.setVariable("weatherCards", weatherCards);
        templateEngine.process("main.html", context, resp.getWriter());
    }
}
