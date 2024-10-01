package com.example.weatherviewerapp.services;

import com.example.weatherviewerapp.dao.UserDAO;
import com.example.weatherviewerapp.dto.UserRequestDTO;
import com.example.weatherviewerapp.dto.UserResponseDTO;
import com.example.weatherviewerapp.entity.User;
import com.example.weatherviewerapp.exception.AuthenticationException;
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
        if (user.isPresent()) {
            log.info("A user with this login was found:" +userRequestDTO.getLogin());
            if(BCrypt.checkpw(userRequestDTO.getPassword(), user.get().getPassword())){
                return new UserResponseDTO(user.get().getId());
            }
            else{
                throw new AuthenticationException("Invalid login or password");
            }
        } else {
            throw new AuthenticationException("Invalid login or password");
        }

    }
}
