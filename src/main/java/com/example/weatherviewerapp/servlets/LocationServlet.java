package com.example.weatherviewerapp.servlets;

import com.example.weatherviewerapp.dto.api.LocationResponseDTO;
import com.example.weatherviewerapp.entity.Location;
import com.example.weatherviewerapp.listner.ThymeleafConfiguration;
import com.example.weatherviewerapp.services.CookieService;
import com.example.weatherviewerapp.services.OpenWeatherService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;

@Slf4j
@WebServlet("/getLocations")
public class LocationServlet extends HttpServlet {
    private final CookieService cookieService = new CookieService();
    private final OpenWeatherService openWeatherService = new OpenWeatherService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);

        //валидация введеного названия города

        List<LocationResponseDTO> responseDTOList = openWeatherService.getLocationsHttpMethod(req.getParameter("locationName"));
        context.setVariable("locations", responseDTOList);
        templateEngine.process("locations.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Cookie cookie = cookieService.getCookie(req);
        if (cookie == null || !cookieService.isCookieInDB(cookie)) {
            resp.sendRedirect("/sign.html");
        } else {
            log.info(req.getParameter("lat"));
            log.info(req.getParameter("lon"));
            log.info(req.getParameter("name"));
            var location = Location.builder()
                    .lat(Double.valueOf(req.getParameter("lat")))
                    .lon(Double.valueOf(req.getParameter("lon")))
                    .name(req.getParameter("name"))
                    .build();
            openWeatherService.addLocationToUser(cookieService.getUserIdForCookie(cookie), location);
            resp.sendRedirect("/main.html");
        }
    }
}
