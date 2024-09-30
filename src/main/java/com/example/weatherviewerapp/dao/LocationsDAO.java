package com.example.weatherviewerapp.dao;

import com.example.weatherviewerapp.entity.Location;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationsDAO extends BaseDAO<Location, Long> {
    public LocationsDAO() {
        super();
    }

    public LocationsDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Location> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select l from Location l", Location.class).list();
        }
    }

    @Override
    public Optional<Location> findById(Long id) {
        try(Session session = sessionFactory.openSession()){
            return Optional.ofNullable(session.get(Location.class, id));
        }
    }

    @Override
    public Location save(Location entity) {
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

            Location match = session.get(Location.class, id);
            if (match != null) {
                session.remove(match);
            }

            session.getTransaction().commit();
        }
    }

    public List<Location> findAllbyUserId(Long id){
        try(Session session = sessionFactory.openSession()){
         return session.createQuery("Select l From Location l Where l.user.id = :id", Location.class).setParameter("id", id).list();
        }
    }

}
