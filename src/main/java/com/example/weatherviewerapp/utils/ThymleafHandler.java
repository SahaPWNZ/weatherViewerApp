package com.example.weatherviewerapp.utils;

import com.example.weatherviewerapp.listener.ThymeleafConfiguration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

public class ThymleafHandler {
    @Getter
    private final TemplateEngine templateEngine;
    private final ServletContext servletContext;

    public ThymleafHandler(ServletContext servletContext) {
        this.templateEngine = (TemplateEngine) servletContext.getAttribute(
                ThymeleafConfiguration.TEMPLATE_ENGINE_ATTR);
        this.servletContext = servletContext;

    }

    public WebContext createWebContext(HttpServletRequest req, HttpServletResponse resp) {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(servletContext)
                .buildExchange(req, resp);
        return new WebContext(webExchange);
    }
}
