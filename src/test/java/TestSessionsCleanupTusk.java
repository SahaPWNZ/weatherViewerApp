import com.example.weatherviewerapp.dao.SessionDAO;
import com.example.weatherviewerapp.entity.User;
import com.example.weatherviewerapp.entity.UserSession;
import com.example.weatherviewerapp.services.SessionsCleanupTusk;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestSessionsCleanupTusk {

    @Mock
    private SessionDAO sessionDAO;
    @InjectMocks
    private SessionsCleanupTusk sessionsCleanupTusk;
    private final User testUser1 = User.builder()
            .id(1L)
            .build();
    private final User testUser2 = User.builder()
            .id(2L)
            .build();


    @DisplayName("test for removing invalid sessions")
    @Test
    void testRunMethod() {
        UserSession expiredSession = new UserSession("testUUID1", testUser1, new Timestamp(System.currentTimeMillis() - 100000));
        UserSession validSession = new UserSession("testUUID2", testUser2, new Timestamp(System.currentTimeMillis() + 100000));

        when(sessionDAO.findAll()).thenReturn(List.of(expiredSession, validSession));

        sessionsCleanupTusk.run();

        verify(sessionDAO).delete(expiredSession.getId());
        verify(sessionDAO, never()).delete(validSession.getId());
    }
}
