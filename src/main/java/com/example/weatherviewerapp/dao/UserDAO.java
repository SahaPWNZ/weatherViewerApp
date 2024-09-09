package com.example.weatherviewerapp.dao;

import com.example.weatherviewerapp.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class UserDAO extends BaseDAO<User> {
    @Override
    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select u from User u", User.class).list();
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(User.class, id));
        }
        //ошибка
    }

    @Override
    public User save(User entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }

    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User match = session.get(User.class, id);
            if (match != null) {
                session.remove(match);
            }

            session.getTransaction().commit();
        }
        //catch
    }
public Optional<User> findByLoginAndPass(String login, String password){
        try(Session session = sessionFactory.openSession()){
            return Optional.ofNullable(session.createQuery("FROM User u " +
                    "WHERE u.login = :login AND u.password = :password", User.class)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .uniqueResult());
        }
}

}
