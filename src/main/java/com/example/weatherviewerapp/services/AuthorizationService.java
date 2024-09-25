package com.example.weatherviewerapp.services;

import com.example.weatherviewerapp.dao.UserDAO;
import com.example.weatherviewerapp.dto.UserRequestDTO;
import com.example.weatherviewerapp.dto.UserResponseDTO;
import com.example.weatherviewerapp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;



@Slf4j
public class AuthorizationService {
    private final UserDAO userDAO = new UserDAO();


    public UserResponseDTO getUserDTO(UserRequestDTO userRequestDTO) {
        User user = userDAO.findByLogin(userRequestDTO.getLogin()).orElse(null);
        if (user != null) {
            log.info("Юзер с таким логином найден: " +userRequestDTO.getLogin());
            if(BCrypt.checkpw(userRequestDTO.getPassword(), user.getPassword())){
                return new UserResponseDTO(user.getId());
            }
            else{
                log.info("Пароли не совпали");
                return null;
            }
        } else {
            log.info("Юзера с такими данными нет");
            return null;
        }

    }
}
