package com.example.weatherviewerapp.services;

import com.example.weatherviewerapp.dao.UserDAO;
import com.example.weatherviewerapp.dto.UserRequestDTO;
import com.example.weatherviewerapp.dto.UserResponseDTO;
import com.example.weatherviewerapp.entity.User;
import com.example.weatherviewerapp.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@Slf4j
public class AuthorizationService {
    private final UserDAO userDAO;

    public AuthorizationService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserResponseDTO getUserDtoIfExist(UserRequestDTO userRequestDTO) {
        Optional<User> user = userDAO.findByLogin(userRequestDTO.getLogin());

        if (user.isPresent() && BCrypt.checkpw(userRequestDTO.getPassword(), user.get().getPassword())) {
            log.info("A user with this login was found:" + userRequestDTO.getLogin());
            return new UserResponseDTO(user.get().getId());
        } else {
            throw new CustomException("Invalid login or password");
        }
    }
}
