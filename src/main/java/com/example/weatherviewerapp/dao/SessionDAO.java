package com.example.weatherviewerapp.dao;


import com.example.weatherviewerapp.entity.UserSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class SessionDAO extends BaseDAO<UserSession, String> {
    public SessionDAO() {
        super();
    }

    public SessionDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
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
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
            return entity;
        } catch (
                HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }


    public void delete(String id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            UserSession userSession = session.get(UserSession.class, id);
            if (userSession != null) {
                session.remove(userSession);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

}
