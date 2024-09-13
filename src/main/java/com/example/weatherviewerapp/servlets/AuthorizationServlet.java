package com.example.weatherviewerapp.servlets;

import com.example.weatherviewerapp.dao.SessionDAO;
import com.example.weatherviewerapp.dao.UserDAO;
import com.example.weatherviewerapp.dto.UserRequestDTO;
import com.example.weatherviewerapp.dto.UserResponseDTO;
import com.example.weatherviewerapp.dto.UserSessionDTO;
import com.example.weatherviewerapp.entity.User;
import com.example.weatherviewerapp.exception.AuthenticatonException;
import com.example.weatherviewerapp.listner.ThymeleafConfiguration;
import com.example.weatherviewerapp.services.AuthorizationService;
import com.example.weatherviewerapp.services.CookieService;
import com.example.weatherviewerapp.utils.MappingUtils;
import com.example.weatherviewerapp.utils.UserMapper;
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
import java.sql.Timestamp;
import java.util.UUID;

import static com.example.weatherviewerapp.filters.CookiesFilter.ANSI_GREEN;
import static com.example.weatherviewerapp.filters.CookiesFilter.ANSI_RESET;


@WebServlet("/sign-in")
public class AuthorizationServlet extends HttpServlet {

private final AuthorizationService authorizationService = new AuthorizationService();
private final CookieService cookieService = new CookieService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .build();

        UserResponseDTO userResponseDTO = authorizationService.getUserDTO(userRequestDTO);

        if (userResponseDTO == null) {

            TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                    ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);
            IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                    .buildExchange(req, resp);

            WebContext context = new WebContext(webExchange);
            context.setVariable("error", "Неправильный логин или пароль");
            templateEngine.process("sign-in.html", context, resp.getWriter());
        } else {
            log(ANSI_GREEN + "Успешная авторизация, создание Куки" + ANSI_RESET);
            Cookie cookie = cookieService.getCookieForNewSession(userResponseDTO);

            resp.addCookie(cookie);
            resp.sendRedirect("index.html");
        }

    }

}
