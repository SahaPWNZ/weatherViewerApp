package com.example.weatherviewerapp.utils;


import com.example.weatherviewerapp.dao.SessionDAO;

import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler implements Runnable {
    private final SessionDAO sessionDAO;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public Scheduler(SessionDAO sessionDAO) {
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
        sessionDAO.findAll()
                .stream()
                .filter(session -> session.getActiveTime().before(new Timestamp(System.currentTimeMillis())))
                .forEach(session -> sessionDAO.delete(session.getId()));
    }
}



