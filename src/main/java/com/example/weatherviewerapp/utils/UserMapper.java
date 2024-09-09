package com.example.weatherviewerapp.utils;

import com.example.weatherviewerapp.dto.UserRequestDTO;
import com.example.weatherviewerapp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User toUserEntityFromDTO(UserRequestDTO userDTO); //map User to UserResponse

}
