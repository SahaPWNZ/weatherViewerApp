package com.example.weatherviewerapp.dao;

import java.util.List;
import java.util.Optional;

interface BaseDAO<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    T save(T entity);

    void delete(ID id);
}
