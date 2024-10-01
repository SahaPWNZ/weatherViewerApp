package com.example.weatherviewerapp.dao;


import com.example.weatherviewerapp.entity.UserSession;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class SessionDAO extends BaseDAO<UserSession, String> {
    public SessionDAO() {
        super();
    }

    public List<UserSession> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select u from UserSession u", UserSession.class).list();
        }
    }


    public Optional<UserSession> findById(String id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(UserSession.class, id));
        }
    }


    public UserSession save(UserSession entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
            return entity;
        }
    }


    public void delete(String id) {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            UserSession userSession = session.get(UserSession.class, id);
            if (userSession != null) {
                session.remove(userSession);
            }
            session.getTransaction().commit();
        }
    }

}
