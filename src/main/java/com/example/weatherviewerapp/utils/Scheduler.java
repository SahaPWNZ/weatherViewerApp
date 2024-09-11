package com.example.weatherviewerapp.utils;


import com.example.weatherviewerapp.services.DataBaseCleanupTusk;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void startCleanUpTusk(){
        System.out.println("шедулер запущ");
        scheduler.scheduleAtFixedRate(new DataBaseCleanupTusk(), 0, 5, TimeUnit.MINUTES);
    }
    public void stopCleanupTask() {
        System.out.println("шедулер остановлен");
        scheduler.shutdown();
    }
}
