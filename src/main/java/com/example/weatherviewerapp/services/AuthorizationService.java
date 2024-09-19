package com.example.weatherviewerapp.services;

import com.example.weatherviewerapp.dao.SessionDAO;
import com.example.weatherviewerapp.dao.UserDAO;
import com.example.weatherviewerapp.dto.UserRequestDTO;
import com.example.weatherviewerapp.dto.UserResponseDTO;
import com.example.weatherviewerapp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;

import static com.example.weatherviewerapp.filters.CookiesFilter.ANSI_GREEN;
import static com.example.weatherviewerapp.filters.CookiesFilter.ANSI_RESET;

@Slf4j
public class AuthorizationService {
    private final SessionDAO sessionDAO = new SessionDAO();
    private final UserDAO userDAO = new UserDAO();


    public UserResponseDTO getUserDTO(UserRequestDTO userRequestDTO) {
        User user = userDAO.findByLogin(userRequestDTO.getLogin()).orElse(null);
        if (user != null) {
            log.info(ANSI_GREEN + "Юзер с таким логином найден: " +userRequestDTO.getLogin() + ANSI_RESET);
            if(BCrypt.checkpw(userRequestDTO.getPassword(), user.getPassword())){
                return new UserResponseDTO(user.getId());
            }
            else{
                log.info(ANSI_GREEN + "Пароли не совпали" + ANSI_RESET);
                return null;
            }
        } else {
            log.info(ANSI_GREEN + "Юзера с такими данными нет" + ANSI_RESET);
            return null;
        }

    }
}
