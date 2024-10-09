package com.example.weatherviewerapp.servlets;

import com.example.weatherviewerapp.dao.UserModelDAO;
import com.example.weatherviewerapp.dto.UserRequestDTO;
import com.example.weatherviewerapp.entity.User;
import com.example.weatherviewerapp.exception.CustomException;
import com.example.weatherviewerapp.utils.MappingUtils;
import com.example.weatherviewerapp.utils.ThymleafHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@Slf4j
@WebServlet("/sign-on")
public class RegistrationServlet extends HttpServlet {
    private ThymleafHandler thymleafHandler;
    private final UserModelDAO userDAO = new UserModelDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/sign-on.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext context = thymleafHandler.createWebContext(req, resp);
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

                throw new CustomException("The entered passwords are not equal");
            }
        } catch (CustomException e) {
            log.error("The entered passwords are not equal", e);
            context.setVariable("error", e.getMessage());
            thymleafHandler.getTemplateEngine().process("sign-on.html", context, resp.getWriter());
        }
    }

    @Override
    public void init() {
        thymleafHandler = new ThymleafHandler(getServletContext());
    }
}
