package com.example.weatherviewerapp.servlets;

import com.example.weatherviewerapp.dto.UserRequestDTO;
import com.example.weatherviewerapp.utils.HibernateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;

import java.io.IOException;



@WebServlet("/authorization")
public class AuthorizationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .build();
        System.out.println(userRequestDTO);

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            System.out.println("Транзакция успешно открыта");
            session.getTransaction().commit();
        }
    }

}
