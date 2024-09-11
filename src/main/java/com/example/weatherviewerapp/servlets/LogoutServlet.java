package com.example.weatherviewerapp.servlets;

import com.example.weatherviewerapp.dao.SessionDAO;
import com.example.weatherviewerapp.utils.CookieValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    SessionDAO sessionDAO = new SessionDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookie = CookieValidator.CheckCookie(req);
        if(cookie!=null){
            sessionDAO.delete(cookie.getValue());
        }
        resp.sendRedirect("sign-in.html");
    }
}
