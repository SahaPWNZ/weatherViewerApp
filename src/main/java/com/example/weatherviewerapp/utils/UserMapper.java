package com.example.weatherviewerapp.utils;

import com.example.weatherviewerapp.dto.UserRequestDTO;
import com.example.weatherviewerapp.dto.UserSessionDTO;
import com.example.weatherviewerapp.entity.User;
import com.example.weatherviewerapp.entity.UserSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUserEntityFromDTO(UserRequestDTO userDTO); //map User to UserResponse

    //    UserSession toUserSessionEntityFromDTO(UserSessionDTO userSessionDTO);
//    @Mapping(source = "userId", target = "user_id")
//    // Опционально, если имена совпадают
//    UserSession toUserSessionEntityFromDTO(UserSessionDTO userSessionDTO); // Маппинг UserSessionDTO в UserSession

}
