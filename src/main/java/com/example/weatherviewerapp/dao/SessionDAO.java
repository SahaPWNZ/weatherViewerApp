package com.example.weatherviewerapp.dao;

import com.example.weatherviewerapp.entity.UserSession;
import com.example.weatherviewerapp.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class SessionDAO implements BaseDAO<UserSession, String> {
    private final SessionFactory sessionFactory;

    public SessionDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
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
