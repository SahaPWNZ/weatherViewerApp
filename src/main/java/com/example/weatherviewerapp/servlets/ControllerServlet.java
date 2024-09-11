package com.example.weatherviewerapp.servlets;

import com.example.weatherviewerapp.dao.SessionDAO;
import com.example.weatherviewerapp.entity.UserSession;
import com.example.weatherviewerapp.utils.CookieValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    SessionDAO sessionDAO = new SessionDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookie = CookieValidator.CheckCookie(req);
        if (cookie == null) {
            System.out.println("Куки юзерАйди равен нулл");
            resp.sendRedirect("sign-in.html");
        } else {
            if(sessionDAO.findById(cookie.getValue()).isPresent()){

                UserSession userSession = sessionDAO.findById(cookie.getValue()).get();
                userSession.setTimestamp(new Timestamp(System.currentTimeMillis()+3600000));
                sessionDAO.save(userSession);
                resp.sendRedirect("main.html");
            }
            else {
                resp.sendRedirect("sign-on.html");
            }
            //метод на поиск куки в бд с сессиями и проверки его на время
            //или переход на старницу авторизации, или вход на главную +
            // обновление времени в бд
        }

    }
}
