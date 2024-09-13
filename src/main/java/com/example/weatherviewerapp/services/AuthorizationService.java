package com.example.weatherviewerapp.services;

import com.example.weatherviewerapp.dao.SessionDAO;
import com.example.weatherviewerapp.dao.UserDAO;
import com.example.weatherviewerapp.dto.UserRequestDTO;
import com.example.weatherviewerapp.dto.UserResponseDTO;
import com.example.weatherviewerapp.entity.User;
import lombok.extern.slf4j.Slf4j;

import static com.example.weatherviewerapp.filters.CookiesFilter.ANSI_GREEN;
import static com.example.weatherviewerapp.filters.CookiesFilter.ANSI_RESET;

@Slf4j
public class AuthorizationService {
    private final SessionDAO sessionDAO = new SessionDAO();
    private final UserDAO userDAO = new UserDAO();

    public UserResponseDTO getUserDTO(UserRequestDTO userRequestDTO) {
        User user = userDAO.findByLoginAndPass(userRequestDTO.getLogin(), userRequestDTO.getPassword()).orElse(null);
        if (user != null) {
            log.info(ANSI_GREEN + "Юзер найден" + ANSI_RESET);
            return new UserResponseDTO(user.getId());
        } else {
            log.info(ANSI_GREEN + "Юзера с такими данными нет" + ANSI_RESET);
            return null;
        }
    }
}
