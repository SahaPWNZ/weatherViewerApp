package com.example.weatherviewerapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/cook")
public class TestCookiesServl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] reqCookies = req.getCookies();

        if (reqCookies!=null){
            for (Cookie cookie: reqCookies){
                System.out.println(cookie.getName() +":::"+cookie.getValue());
            }
            req.getSession().getAttribute("user_id");
        }
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>Look in console12345</h1>");
        out.println("</body></html>");
        out.close();

        super.doGet(req, resp);
    }
}
