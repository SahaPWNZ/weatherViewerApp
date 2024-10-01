package com.example.weatherviewerapp.servlets;

import com.example.weatherviewerapp.dao.UserDAO;
import com.example.weatherviewerapp.dto.UserRequestDTO;
import com.example.weatherviewerapp.dto.UserResponseDTO;
import com.example.weatherviewerapp.listner.ThymeleafConfiguration;
import com.example.weatherviewerapp.services.AuthorizationService;
import com.example.weatherviewerapp.services.CookieService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@Slf4j
@WebServlet("/sign-in")
public class AuthorizationServlet extends HttpServlet {

    private final AuthorizationService authorizationService = new AuthorizationService(new UserDAO());
    private final CookieService cookieService = new CookieService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);
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

        try {
            UserResponseDTO userResponseDTO = authorizationService.getUserDtoIfExist(userRequestDTO);
            Cookie cookie = cookieService.getCookieForNewSession(userResponseDTO);
            resp.addCookie(cookie);
            resp.sendRedirect("/home");

        } catch (Exception e) {

            context.setVariable("error", e.getMessage());
            templateEngine.process("sign-in.html", context, resp.getWriter());
        }
    }

}
