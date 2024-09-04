package com.example.weatherviewerapp.servlets;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie [] cookies = request.getCookies();



        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        for (Cookie cookie: cookies){
            out.println("<h1>" + cookie.getName() +":"+cookie.getValue()+"</h1>");
        }


        out.println("</body></html>");
    }

}