package com.example.weatherviewerapp.dao;

import com.example.weatherviewerapp.entity.User;

import java.util.List;
import java.util.Optional;

public class UserDAO extends BaseDAO<User>{
    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
