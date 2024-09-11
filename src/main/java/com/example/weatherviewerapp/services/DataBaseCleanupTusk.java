package com.example.weatherviewerapp.services;

import com.example.weatherviewerapp.dao.SessionDAO;
import com.example.weatherviewerapp.entity.UserSession;

import java.sql.Timestamp;
import java.util.List;

public class DataBaseCleanupTusk implements Runnable{
    @Override
    public void run() {
        SessionDAO sessionDAO = new SessionDAO();
        List<UserSession> allSessions = sessionDAO.findAll();
        allSessions.stream().filter(session -> session.getTimestamp().before(new Timestamp(System.currentTimeMillis())))
                .forEach(session -> sessionDAO.delete(session.getId()));
        System.out.println("все устаревшие сессии удалены");
    }
}
