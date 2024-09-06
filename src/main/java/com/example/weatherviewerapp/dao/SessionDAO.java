package com.example.weatherviewerapp.dao;

import com.example.weatherviewerapp.entity.Session;

import java.util.List;
import java.util.Optional;

public class SessionDAO extends BaseDAO<Session> {
    @Override
    public List<Session> findAll() {
        return null;
    }

    @Override
    public Optional<Session> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Session save(Session entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
