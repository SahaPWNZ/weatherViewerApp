package com.example.weatherviewerapp.filters;

import com.example.weatherviewerapp.exception.CustomException;
import com.example.weatherviewerapp.utils.ThymleafHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebFilter(urlPatterns = {"/home", "/getLocations", "/deleteWeatherCard"})
public class ServiceExceptionsHandlerFilter extends HttpFilter {
    private ThymleafHandler thymleafHandler;

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            super.doFilter(req, res, chain);
        } catch (CustomException e) {
            WebContext context = thymleafHandler.createWebContext(req, res);

            context.setVariable("error", e.getMessage());
            thymleafHandler.getTemplateEngine().process("exception.html", context, res.getWriter());
        }
    }

    @Override
    public void init() {
        thymleafHandler = new ThymleafHandler(getServletContext());
    }
}
