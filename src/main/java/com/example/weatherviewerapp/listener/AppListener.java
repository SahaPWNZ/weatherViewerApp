package com.example.weatherviewerapp.listener;

import com.example.weatherviewerapp.dao.SessionModelDAO;
import com.example.weatherviewerapp.services.SchedulerService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebListener
public class AppListener implements ServletContextListener {
    private final SchedulerService scheduler = new SchedulerService(new SessionModelDAO());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Start scheduler in listener");
        scheduler.startCleanUpTusk();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.stopCleanupTask();
    }
}
