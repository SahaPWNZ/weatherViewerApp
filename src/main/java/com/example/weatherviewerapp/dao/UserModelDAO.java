package com.example.weatherviewerapp.dao;

import com.example.weatherviewerapp.dao.baseDAO.UserDAO;
import com.example.weatherviewerapp.entity.User;
import com.example.weatherviewerapp.exception.CustomException;
import com.example.weatherviewerapp.utils.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Optional;

@Slf4j
public class UserModelDAO implements UserDAO {
    private final SessionFactory sessionFactory;
    private final static String FIND_BY_ID = "SELECT DISTINCT u FROM User u " +
            "left join fetch u.locations WHERE u.id = :id";
    private final static String FIND_BY_LOGIN = "SELECT DISTINCT u FROM User u " +
            "left join fetch u.locations WHERE u.login = :login";

    public UserModelDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public UserModelDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<User> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.createQuery(FIND_BY_ID, User.class)
                    .setParameter("id", id)
                    .uniqueResult());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CustomException("DataBase Exception!");
        }
    }

    @Override
    public User save(User entity) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
        } catch (ConstraintViolationException e) {
            throw new CustomException("There is already a user with this login");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CustomException("DataBase Exception!");
        }

    }

    public Optional<User> findByLogin(String login) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.createQuery(FIND_BY_LOGIN, User.class)
                    .setParameter("login", login)
                    .uniqueResult());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CustomException("DataBase Exception!");
        }
    }
}
