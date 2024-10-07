package com.example.weatherviewerapp.utils;


import com.example.weatherviewerapp.dao.SessionDAO;
import com.example.weatherviewerapp.services.SessionsCleanupTusk;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void startCleanUpTusk() {
        scheduler.scheduleAtFixedRate(new SessionsCleanupTusk(new SessionDAO()), 0, 3, TimeUnit.MINUTES);
    }

    public void stopCleanupTask() {
        scheduler.shutdown();
    }
}
