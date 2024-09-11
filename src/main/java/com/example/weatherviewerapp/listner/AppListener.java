package com.example.weatherviewerapp.listner;

import com.example.weatherviewerapp.utils.Scheduler;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppListener implements ServletContextListener {
    private final Scheduler scheduler = new Scheduler();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler.startCleanUpTusk();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.stopCleanupTask();
    }
}
