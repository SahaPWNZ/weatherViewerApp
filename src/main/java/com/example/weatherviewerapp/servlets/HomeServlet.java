package com.example.weatherviewerapp.servlets;

import com.example.weatherviewerapp.dao.LocationsDAO;
import com.example.weatherviewerapp.services.CookieService;
import com.example.weatherviewerapp.services.LocationsService;
import com.example.weatherviewerapp.utils.ThymleafHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private ThymleafHandler thymleafHandler;
    private final LocationsService locationsService = new LocationsService(new LocationsDAO());
    private final CookieService cookieService = new CookieService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext context = thymleafHandler.createWebContext(req, resp);
        Cookie cookie = cookieService.getSessionCookie(req);
        var weatherCards = locationsService.findAllWeatherCards(cookieService.getUserForCookie(cookie).getId());

        context.setVariable("weatherCards", weatherCards);
        thymleafHandler.getTemplateEngine().process("main.html", context, resp.getWriter());
    }

    @Override
    public void init() {
        thymleafHandler = new ThymleafHandler(getServletContext());
    }
}
