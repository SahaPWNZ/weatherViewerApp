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

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestContainerTests {
    private static final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private static UserDAO userDAO;
    private static SessionDAO sessionDAO;

    @BeforeAll
    static void getDao() {
        userDAO = new UserDAO(sessionFactory);
        sessionDAO = new SessionDAO(sessionFactory);
    }

    @Nested
    @DisplayName("Tests connection to dataBase and userDAO")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class TestsDataBase{
        @Order(1)
        @Test
        void testConnectionToContainerPostgres() {

            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                System.out.println("Транзакция успешно открыта");
                session.getTransaction().commit();
            }
        }

        @Order(2)
        @Test
        void testAddNewUser() {
            User testUser = User.builder()
                    .login("saha")
                    .password(BCrypt.hashpw("2210", BCrypt.gensalt()))
                    .locations(new ArrayList<>())
                    .build();
            System.out.println(userDAO.save(testUser));
        }

    }








}