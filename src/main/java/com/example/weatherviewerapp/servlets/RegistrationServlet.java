package com.example.weatherviewerapp.servlets;

import com.example.weatherviewerapp.dao.UserDAO;
import com.example.weatherviewerapp.dto.UserRequestDTO;
import com.example.weatherviewerapp.entity.User;
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

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    UserDAO userDAO = new UserDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pass = req.getParameter("password");
        String confirmPass = req.getParameter("confirm_password");
        System.out.println(pass);
        System.out.println(confirmPass);
        System.out.println(pass.equals(confirmPass));
        if(!pass.equals(confirmPass)){
            TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                    ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);
            IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                    .buildExchange(req, resp);
            WebContext context = new WebContext(webExchange);
            context.setVariable("error", "Пароли не одинаковые!");
            templateEngine.process("sign-on.html", context, resp.getWriter());
        }
        else {
            UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                    .login(req.getParameter("login"))
                    .password(req.getParameter("password"))
                    .build();

            User user = UserMapper.INSTANCE.toUserEntityFromDTO(userRequestDTO);
            userDAO.save(user); // обработки ошибок (на повторение логина, проблемы с бд)
            resp.sendRedirect("test1.html");
        }


        //добавляем в бд, (тут если пытаются добавить с таким же логином -
        // то ловим исключение и выводим пользователю что с таким логином уже есть

        //редиректим на мэйн страницу
    }
}
