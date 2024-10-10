package com.example.weatherviewerapp.dao;

import com.example.weatherviewerapp.dao.baseDAO.SessionDAO;
import com.example.weatherviewerapp.entity.UserSession;
import com.example.weatherviewerapp.exception.CustomException;
import com.example.weatherviewerapp.utils.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

@Slf4j
public class SessionModelDAO implements SessionDAO {
    private final SessionFactory sessionFactory;
    private final static String GET_ALL = "from UserSession";
    private final static String FIND_BY_ID = "select u from UserSession u where u.id = :id";

    public SessionModelDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<UserSession> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(GET_ALL, UserSession.class).list();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CustomException("DataBase Exception!");
        }
    }


    public Optional<UserSession> findById(String id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.createQuery(FIND_BY_ID, UserSession.class)
                    .setParameter("id", id)
                    .uniqueResult());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CustomException("DataBase Exception!");
        }
    }

    public UserSession save(UserSession entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CustomException("DataBase Exception!");
        }
    }

    public void delete(String id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Optional<UserSession> userSession = findById(id);
            userSession.ifPresent(session::remove);

            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CustomException("DataBase Exception!");
        }
    }
}
