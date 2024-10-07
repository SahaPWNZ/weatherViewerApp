package com.example.weatherviewerapp.dao;

import com.example.weatherviewerapp.entity.Location;
import com.example.weatherviewerapp.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class LocationsDAO implements BaseDAO<Location, Long> {
    private final SessionFactory sessionFactory;

    public LocationsDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<Location> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select l from Location l", Location.class).list();
        }
    }

    @Override
    public Optional<Location> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Location.class, id));
        }
    }

    @Override
    public Location save(Location entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Location location = session.get(Location.class, id);
            if (location != null) {
                session.remove(location);
            }

            session.getTransaction().commit();
        }
    }

    public List<Location> findAllByUserId(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("Select l From Location l Where l.user.id = :id", Location.class).setParameter("id", id).list();
        }
    }

}
