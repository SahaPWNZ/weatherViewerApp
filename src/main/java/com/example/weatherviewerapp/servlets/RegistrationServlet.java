package com.example.weatherviewerapp.servlets;

import com.example.weatherviewerapp.dao.UserDAO;
import com.example.weatherviewerapp.dto.UserRequestDTO;
import com.example.weatherviewerapp.entity.User;
import com.example.weatherviewerapp.exception.RegistrationException;
import com.example.weatherviewerapp.listener.ThymeleafConfiguration;
import com.example.weatherviewerapp.utils.MappingUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@Slf4j
@WebServlet("/sign-on")
public class RegistrationServlet extends HttpServlet {
    UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/sign-on.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(req, resp);

        String pass = req.getParameter("password");
        String confirmPass = req.getParameter("confirm_password");
        try {
            if (pass.equals(confirmPass)) {

                UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                        .login(req.getParameter("login"))
                        .password(BCrypt.hashpw(pass, BCrypt.gensalt()))
                        .build();

                User user = MappingUtils.toUserFromDTO(userRequestDTO);
                userDAO.save(user);
                resp.sendRedirect("/sign-in");

            } else {

                throw new RegistrationException("The entered passwords are not equal");
            }
        } catch (RegistrationException e) {
            log.error("The entered passwords are not equal", e);
            WebContext context = new WebContext(webExchange);
            context.setVariable("error", e.getMessage());
            templateEngine.process("sign-on.html", context, resp.getWriter());
        }

    }
}
