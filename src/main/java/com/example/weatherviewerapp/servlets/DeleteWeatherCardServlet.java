package com.example.weatherviewerapp.servlets;

import com.example.weatherviewerapp.dao.LocationsDAO;
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

        locationsDAO.delete(Long.valueOf(req.getParameter("locationId")));
        resp.sendRedirect("/home");
    }
}
