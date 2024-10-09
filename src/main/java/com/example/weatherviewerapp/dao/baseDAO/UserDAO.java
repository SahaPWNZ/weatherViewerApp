package com.example.weatherviewerapp.dao.baseDAO;

import com.example.weatherviewerapp.entity.User;

import java.util.Optional;

public interface UserDAO {

    Optional<User> findById(Long id);

    User save(User entity);

    Optional<User> findByLogin(String login);
}
