package com.example.weatherviewerapp.listner;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

@WebListener
public class ThymeleafConfiguration implements ServletContextListener {

    public static final String TEMPLATE_ENGINE_ATTR = "com.huongdanjava.thymeleaf.TemplateEngineInstance";

    private ITemplateEngine templateEngine;

    private JakartaServletWebApplication application;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.application = JakartaServletWebApplication.buildApplication(sce.getServletContext());
        this.templateEngine = templateEngine(this.application);
        sce.getServletContext().setAttribute(TEMPLATE_ENGINE_ATTR, templateEngine);
    }

    private ITemplateEngine templateEngine(IWebApplication application) {
        TemplateEngine templateEngine = new TemplateEngine();

        WebApplicationTemplateResolver templateResolver = templateResolver(application);
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }

    private WebApplicationTemplateResolver templateResolver(IWebApplication application) {
        WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);

        // HTML is the default mode, but we will set it anyway for better understanding of code
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setPrefix("/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

}