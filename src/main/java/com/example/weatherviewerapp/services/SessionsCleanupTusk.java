package com.example.weatherviewerapp.services;

import com.example.weatherviewerapp.dao.SessionDAO;
import com.example.weatherviewerapp.entity.UserSession;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.List;
@Slf4j
public class SessionsCleanupTusk implements Runnable{
    private final SessionDAO sessionDAO;

    public SessionsCleanupTusk(SessionDAO sessionDao) {
        this.sessionDAO = sessionDao;
    }

    @Override
    public void run() {
        List<UserSession> allSessions = sessionDAO.findAll();
        allSessions.stream().filter(session -> session.getTimestamp().before(new Timestamp(System.currentTimeMillis())))
                .forEach(session -> sessionDAO.delete(session.getId()));
        log.info("все устаревшие сессии удалены");
    }
}
