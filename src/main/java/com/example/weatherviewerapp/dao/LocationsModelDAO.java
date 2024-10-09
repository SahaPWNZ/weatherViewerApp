package com.example.weatherviewerapp.dao;

import com.example.weatherviewerapp.dao.baseDAO.LocationDAO;
import com.example.weatherviewerapp.entity.Location;
import com.example.weatherviewerapp.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class LocationsModelDAO implements LocationDAO {
    private final SessionFactory sessionFactory;
    private final static String GET_ALL_WITH_USER_ID = "Select l From Location l Where l.user.id = :id";

    public LocationsModelDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }


    public Location save(Location entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

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
            return session.createQuery(GET_ALL_WITH_USER_ID, Location.class).
                    setParameter("id", id)
                    .list();
        }
    }

}
