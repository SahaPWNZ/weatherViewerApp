package com.example.weatherviewerapp.utils;

import com.example.weatherviewerapp.dao.UserDAO;
import com.example.weatherviewerapp.dto.UserSessionDTO;
import com.example.weatherviewerapp.dto.api.LocationResponseDTO;
import com.example.weatherviewerapp.entity.Location;
import com.example.weatherviewerapp.entity.User;
import com.example.weatherviewerapp.entity.UserSession;

public class MappingUtils {
    private static final UserDAO userDAO = new UserDAO();
public static UserSession toUserSessionFromDTO(UserSessionDTO userSessionDTO){
    return UserSession.builder()
            .id(userSessionDTO.getGUID())
            .user(userDAO.findById(userSessionDTO.getUserId()).get())
            .timestamp(userSessionDTO.getTimestamp())
            .build();
}
}
