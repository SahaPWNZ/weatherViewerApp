package com.example.weatherviewerapp.servlets;

import com.example.weatherviewerapp.dto.api.LocationResponseDTO;
import com.example.weatherviewerapp.dto.UserRequestDTO;
import com.example.weatherviewerapp.dto.UserResponseDTO;
import com.example.weatherviewerapp.listner.ThymeleafConfiguration;
import com.example.weatherviewerapp.services.AuthorizationService;
import com.example.weatherviewerapp.services.CookieService;
import com.example.weatherviewerapp.services.OpenWeatherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




@WebServlet("/sign-in")
public class AuthorizationServlet extends HttpServlet {

    private final OpenWeatherService openWeatherService = new OpenWeatherService();
    private final AuthorizationService authorizationService = new AuthorizationService();
    private final CookieService cookieService = new CookieService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/sign-in.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .build();


        UserResponseDTO userResponseDTO = authorizationService.getUserDTO(userRequestDTO);

        if (userResponseDTO == null) {

            context.setVariable("error", "Неправильный логин или пароль");
            templateEngine.process("sign-in.html", context, resp.getWriter());

        } else {
            log("Успешная авторизация, создание Куки");
            Cookie cookie = cookieService.getCookieForNewSession(userResponseDTO);

            resp.addCookie(cookie);
            resp.sendRedirect("/home");
        }

    }

}
