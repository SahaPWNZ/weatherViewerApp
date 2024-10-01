import com.example.weatherviewerapp.dao.SessionDAO;
import com.example.weatherviewerapp.dao.UserDAO;
import com.example.weatherviewerapp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.mindrot.jbcrypt.BCrypt;
import util.HibernateTestUtil;

import javax.naming.ldap.PagedResultsControl;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestContainerTests {
    private static final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private static UserDAO userDAO;

    @BeforeAll
    static void getDao() {
        userDAO = new UserDAO(sessionFactory);

    }

    @Nested
    @DisplayName("Tests connection to dataBase and userDAO")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class TestsDataBase {
        @Order(1)
        @Test
        void testConnectionToContainerPostgres() {

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                log.info("Transaction is opened");
                session.getTransaction().commit();
            }
        }

        @Order(2)
        @Test
        void testAddNewUsers() {
            User testUser = User.builder()
                    .login("saha")
                    .password(BCrypt.hashpw("2210", BCrypt.gensalt()))
                    .build();
            Assertions.assertInstanceOf(User.class, userDAO.save(testUser));

            User testUser2 = User.builder()
                    .login("testLogin")
                    .password(BCrypt.hashpw("testPass", BCrypt.gensalt()))
                    .locations(new ArrayList<>())
                    .build();
            Assertions.assertInstanceOf(User.class, userDAO.save(testUser2));
        }

        @Order(3)
        @Test
        void testGetUserOnLogin() {
            assertEquals(Optional.empty(), userDAO.findByLogin("InvalidLogin"));
            assertEquals(2L, userDAO.findByLogin("testLogin").get().getId());
        }

        @Order(4)
        @Test
        void testDelete() {
            userDAO.delete(1L);
            assertEquals(Optional.empty(), userDAO.findById(1L));
        }
    }


}