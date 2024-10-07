package com.example.weatherviewerapp.servlets;

import com.example.weatherviewerapp.dao.LocationsDAO;
import com.example.weatherviewerapp.exception.CustomException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/deleteWeatherCard")
public class DeleteWeatherCardServlet extends HttpServlet {

    private final LocationsDAO locationsDAO = new LocationsDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String locationId = req.getParameter("locationId");

        validate(locationId);
        locationsDAO.delete(Long.valueOf(locationId));
        resp.sendRedirect("/home");
    }

    private void validate(String value) {
        if (value == null || value.isEmpty() || !value.matches("\\d+")) {
            throw new CustomException("Invalid weatherCard Id parameter");
        }
    }
}
