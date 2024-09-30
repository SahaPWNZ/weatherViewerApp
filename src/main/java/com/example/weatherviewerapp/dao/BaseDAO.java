package com.example.weatherviewerapp.dao;


import com.example.weatherviewerapp.utils.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public abstract class BaseDAO<T, ID> {
    protected final SessionFactory sessionFactory;

    public BaseDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }
    public BaseDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public abstract List<T> findAll();

    public abstract Optional<T> findById(ID id);

    public abstract T save(T entity);

    public abstract void delete(ID id);
}
