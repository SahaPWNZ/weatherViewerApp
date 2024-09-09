package com.example.weatherviewerapp.servlets;

import com.example.weatherviewerapp.dao.UserDAO;
import com.example.weatherviewerapp.dto.UserRequestDTO;
import com.example.weatherviewerapp.entity.User;
import com.example.weatherviewerapp.exception.AuthenticatonException;
import com.example.weatherviewerapp.listner.ThymeleafConfiguration;
import com.example.weatherviewerapp.utils.UserMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;


import java.io.IOException;



@WebServlet("/sign-in")
public class AuthorizationServlet extends HttpServlet {
    UserDAO userDAO = new UserDAO();
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
//                ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);
//
//        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
//                .buildExchange(req, resp);
//
//        WebContext context = new WebContext(webExchange);
//
//        templateEngine.process("sign-in.html", context, resp.getWriter());
//    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .build();

        System.out.println(userRequestDTO);
        User user = UserMapper.INSTANCE.toUserEntityFromDTO(userRequestDTO);
        System.out.println(user);

        if (userDAO.findByLoginAndPass(userRequestDTO.getLogin(), userRequestDTO.getPassword()).isEmpty()){

            TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                    ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);

            IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                    .buildExchange(req, resp);

            WebContext context = new WebContext(webExchange);

//            resp.setStatus(HttpServletResponse.SC_FOUND); // Установка статуса перенаправления
//            resp.setHeader("Location", "/sign-in.html"); // Установка URL перенаправления
            context.setVariable("error", "Неправильный логин или пароль");
            templateEngine.process("sign-in.html", context, resp.getWriter());
        }
        else {
            System.out.println("успешная авторизация");
            //обновление сессии в бд
            resp.sendRedirect("/test1.html");
        }

    }

}
