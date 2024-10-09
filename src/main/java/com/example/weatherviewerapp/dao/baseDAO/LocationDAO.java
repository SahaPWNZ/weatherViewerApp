package com.example.weatherviewerapp.dao.baseDAO;

import com.example.weatherviewerapp.entity.Location;

import java.util.List;

public interface LocationDAO {
    Location save(Location entity);

    void delete(Long id);

    List<Location> findAllByUserId(Long id);
}
