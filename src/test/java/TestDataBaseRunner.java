import com.example.weatherviewerapp.dao.UserDAO;
import com.example.weatherviewerapp.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.mindrot.jbcrypt.BCrypt;
import util.HibernateTestUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestDataBaseRunner {
    private static final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private static UserDAO userDAO;

    @BeforeAll
    static void getUserDao() {
        userDAO = new UserDAO(sessionFactory);
    }

    @Order(1)
    @Test
    void testConnectionToContainerPostgres() {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            System.out.println("Транзакция успешно открыта");
            session.getTransaction().commit();
//            assertTrue(false);
        }
    }

    @Order(2)
    @Test
    void testAddNewUser() {
        User testUser = User.builder()
                .login("saha07")
                .password(BCrypt.hashpw("2210", BCrypt.gensalt()))
                .locations(new ArrayList<>())
                .build();
        System.out.println(userDAO.save(testUser));
    }

    @Order(3)
    @Test
    void testPrintAllUsers() {
        List<User> listUsers = userDAO.findAll();
        for (User user : listUsers) {
            System.out.println(user);
        }
    }

//    @Test
//    public void testSessionDAO() {
//        List<UserSession> listU = sessionDAO.findAll();
//        for (UserSession userSession : listU) {
//            System.out.println(userSession);
//        }
//    }


}