package com.example.weatherviewerapp.dao;

import com.example.weatherviewerapp.entity.User;
import com.example.weatherviewerapp.exception.RegistrationException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;
import java.util.Optional;

public class UserDAO extends BaseDAO<User, Long> {
    public UserDAO() {
        super();
    }

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

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

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
        } catch (ConstraintViolationException e) {
            throw new RegistrationException("There is already a user with this login");
        }

    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }

            session.getTransaction().commit();
        }
        //catch
    }

    public Optional<User> findByLogin(String login) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.createQuery("FROM User u " +
                            "WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .uniqueResult());
        }
    }

}
