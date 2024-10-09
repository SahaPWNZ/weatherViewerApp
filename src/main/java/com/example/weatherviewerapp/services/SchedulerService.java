package com.example.weatherviewerapp.services;


import com.example.weatherviewerapp.dao.SessionModelDAO;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
@Slf4j
public class SchedulerService implements Runnable {
    private final SessionModelDAO sessionDAO;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public SchedulerService(SessionModelDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    public void startCleanUpTusk() {
        scheduler.scheduleAtFixedRate(this, 0, 5, TimeUnit.MINUTES);

    }

    public void stopCleanupTask() {
        scheduler.shutdown();
    }

    @Override
    public void run() {
        sessionDAO.getAll()
                .stream()
                .filter(session -> session.getActiveTime().before(new Timestamp(System.currentTimeMillis())))
                .forEach(session -> sessionDAO.delete(session.getId()));
        log.info("Scheduler finish work");
    }
}



