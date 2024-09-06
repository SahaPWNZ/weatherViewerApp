package com.example.weatherviewerapp.servlets;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/hell")
public class HelloServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.setAttribute("user_id","user_id_random");
        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setMaxAge(60*60);
        response.addCookie(cookie);

//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//        out.println("<html><body>");
//        out.println("<h1>Look in console</h1>");
//        out.println("</body></html>");
//        out.close();

        response.sendRedirect("https://www.google.by/?hl=ru");


    }

}