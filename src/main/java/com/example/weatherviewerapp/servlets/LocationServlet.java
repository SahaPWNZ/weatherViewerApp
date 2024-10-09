package com.example.weatherviewerapp.servlets;

import com.example.weatherviewerapp.dao.LocationsModelDAO;
import com.example.weatherviewerapp.entity.Location;
import com.example.weatherviewerapp.exception.CustomException;
import com.example.weatherviewerapp.services.CookieService;
import com.example.weatherviewerapp.services.LocationsService;
import com.example.weatherviewerapp.services.OpenWeatherService;
import com.example.weatherviewerapp.utils.ThymleafHandler;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@Slf4j
@WebServlet("/getLocations")
public class LocationServlet extends HttpServlet {
    private ThymleafHandler thymleafHandler;
    private final CookieService cookieService = new CookieService();
    private final OpenWeatherService openWeatherService = new OpenWeatherService();
    private final LocationsService locationsService = new LocationsService(new LocationsModelDAO());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext context = thymleafHandler.createWebContext(req, resp);
        var responseDTOList = openWeatherService.getLocationsHttpMethod(req.getParameter("locationName"));

        context.setVariable("locations", responseDTOList);
        thymleafHandler.getTemplateEngine().process("locations.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String lat = req.getParameter("lat");
        String lon = req.getParameter("lon");
        String name = req.getParameter("name");

        stringValidate(name);
        longValidate(lat);
        longValidate(lon);

        Cookie cookie = cookieService.getSessionCookie(req);
        if (cookie == null || !cookieService.isCookieInDB(cookie)) {
            resp.sendRedirect("/sign.html");
        } else {
            var location = Location.builder()
                    .lat(Double.valueOf(lat))
                    .lon(Double.valueOf(lon))
                    .name(name)
                    .build();

            locationsService.addLocationToUser(cookieService.getUserForCookie(cookie), location);
            resp.sendRedirect("/home");
        }
    }

    @Override
    public void init() {
        thymleafHandler = new ThymleafHandler(getServletContext());
    }

    private void longValidate(String value) {
        if (value == null || value.isEmpty()) {
            throw new CustomException("Invalid lat or lon location parameter");
        }
    }

    private void stringValidate(String str) {
        if (str == null || str.isEmpty()) {
            throw new CustomException("Name cannot be empty!");
        }
    }

}
