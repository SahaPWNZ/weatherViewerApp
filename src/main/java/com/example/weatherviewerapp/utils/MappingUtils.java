package com.example.weatherviewerapp.utils;

import com.example.weatherviewerapp.dao.UserModelDAO;
import com.example.weatherviewerapp.dto.UserRequestDTO;
import com.example.weatherviewerapp.dto.UserSessionDTO;
import com.example.weatherviewerapp.entity.User;
import com.example.weatherviewerapp.entity.UserSession;

public class MappingUtils {
    private static final UserModelDAO userDAO = new UserModelDAO();

    public static UserSession toUserSessionFromDTO(UserSessionDTO userSessionDTO) {
        return UserSession.builder()
                .id(userSessionDTO.getId())
                .user(userDAO.findById(userSessionDTO.getUserId()).orElseThrow())
                .activeTime(userSessionDTO.getTimestamp())
                .build();
    }

    public static User toUserFromDTO(UserRequestDTO userDTO) {
        return User.builder()
                .login(userDTO.getLogin())
                .password(userDTO.getPassword())
                .build();
    }
}
