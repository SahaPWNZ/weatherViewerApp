package com.example.weatherviewerapp.dao.baseDAO;

import com.example.weatherviewerapp.entity.UserSession;

import java.util.List;
import java.util.Optional;

public interface SessionDAO {
    List<UserSession> getAll();

    Optional<UserSession> findById(String id);

    UserSession save(UserSession entity);

    void delete(String id);
}
