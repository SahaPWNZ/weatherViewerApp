package com.example.weatherviewerapp.servlets;

import com.example.weatherviewerapp.dao.SessionDAO;
import com.example.weatherviewerapp.dao.UserDAO;
import com.example.weatherviewerapp.dto.UserRequestDTO;
import com.example.weatherviewerapp.dto.UserSessionDTO;
import com.example.weatherviewerapp.entity.User;
import com.example.weatherviewerapp.exception.AuthenticatonException;
import com.example.weatherviewerapp.listner.ThymeleafConfiguration;
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


@WebServlet("/sign-in")
public class AuthorizationServlet extends HttpServlet {
    UserDAO userDAO = new UserDAO();
    SessionDAO sessionDAO = new SessionDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .build();
        //метод куда мы отправляем ДТО, а обратно получаем опшионал
        User user = UserMapper.INSTANCE.toUserEntityFromDTO(userRequestDTO);
        user = userDAO.findByLoginAndPass(user.getLogin(), user.getPassword()).orElse(null);


        if (user == null) {

            TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                    ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);

            IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                    .buildExchange(req, resp);

            WebContext context = new WebContext(webExchange);
            context.setVariable("error", "Неправильный логин или пароль");
            templateEngine.process("sign-in.html", context, resp.getWriter());
        } else {
            System.out.println("успешная авторизация");
            UserSessionDTO userSessionDTO = UserSessionDTO.builder()
                    .GUID(UUID.randomUUID().toString())
                    .userId(user.getId())
                    .timestamp(new Timestamp(System.currentTimeMillis() + 450000)) //текущая дата + час (3600000
                    .build();
            sessionDAO.save(MappingUtils.toUserSessionFromDTO(userSessionDTO));
            Cookie cookie = new Cookie("sessionId", userSessionDTO.getGUID() );
            cookie.setMaxAge(3600);
            resp.addCookie(cookie);
            //обновление сессии в бд
            resp.sendRedirect("index.html");
        }

    }

}
