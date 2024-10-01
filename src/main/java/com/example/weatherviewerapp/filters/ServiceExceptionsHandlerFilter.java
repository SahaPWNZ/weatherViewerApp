package com.example.weatherviewerapp.filters;

import com.example.weatherviewerapp.exception.InvalidStatusCodeException;
import com.example.weatherviewerapp.exception.OpenWeatherApiException;
import com.example.weatherviewerapp.listner.ThymeleafConfiguration;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebFilter(urlPatterns = {"/home", "/getLocations", "/deleteWeatherCard"})
public class ServiceExceptionsHandlerFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            super.doFilter(req, res, chain);
        } catch (InvalidStatusCodeException | OpenWeatherApiException e) {

            TemplateEngine templateEngine = (TemplateEngine) getServletContext().getAttribute(
                    ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);
            IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                    .buildExchange(req, res);

            WebContext context = new WebContext(webExchange);

            context.setVariable("error", e.getMessage());
            templateEngine.process("exception.html", context, res.getWriter());
        }
    }
}
