import com.example.weatherviewerapp.dao.UserModelDAO;
import com.example.weatherviewerapp.dto.UserRequestDTO;
import com.example.weatherviewerapp.dto.UserResponseDTO;
import com.example.weatherviewerapp.entity.User;
import com.example.weatherviewerapp.exception.CustomException;
import com.example.weatherviewerapp.services.AuthorizationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TestAuthorizationService {
    @InjectMocks
    private AuthorizationService authorizationService;
    @Mock
    private UserModelDAO userDAO;
    private final static UserRequestDTO testRequestDTO = new UserRequestDTO("testLogin", "Pass");


    @Test
    void testAuthorizationServiceWithWrongPass() {
        Optional<User> user = Optional.of(new User(1L, "testLogin",
                BCrypt.hashpw("wrongPass", BCrypt.gensalt()), new ArrayList<>()));

        Mockito.when(userDAO.findByLogin("testLogin")).thenReturn(user);

        Assertions.assertThrows(CustomException.class,
                () -> authorizationService.getUserDtoIfExist(testRequestDTO));
    }

    @Test
    void testAuthorizationServiceWithCorrectPass() {
        UserResponseDTO testResponseDTO = new UserResponseDTO(1L);
        Optional<User> user = Optional.of(new User(1L, "testLogin",
                BCrypt.hashpw("Pass", BCrypt.gensalt()), new ArrayList<>()));

        Mockito.when(userDAO.findByLogin("testLogin")).thenReturn(user);

        assertEquals(testResponseDTO, authorizationService.getUserDtoIfExist(testRequestDTO));
    }

    @Test
    void testAuthorizationServiceWithEmptyUser() {
        Optional<User> user = Optional.empty();

        Mockito.when(userDAO.findByLogin("testLogin")).thenReturn(user);

        Assertions.assertThrows(CustomException.class,
                () -> authorizationService.getUserDtoIfExist(testRequestDTO));
    }
}
