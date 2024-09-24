import com.example.weatherviewerapp.dao.SessionDAO;
import com.example.weatherviewerapp.dao.UserDAO;
import com.example.weatherviewerapp.entity.User;
import com.example.weatherviewerapp.entity.UserSession;
import com.example.weatherviewerapp.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestDataBaseRunner {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private final UserDAO userDAO = new UserDAO();
    private final SessionDAO sessionDAO = new SessionDAO();

    @Test
    public void testConnection() {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            System.out.println("Транзакция успешно открыта");
            session.getTransaction().commit();
        }
    }

    @Test
    public void testUserDAO() {
        System.out.println(userDAO.findById(1L));
        List<User> listU = userDAO.findAll();
        for (User user : listU) {
            System.out.println(user);
        }
    }

    @Test
    public void testSessionDAO() {
        List<UserSession> listU = sessionDAO.findAll();
        for (UserSession userSession : listU) {
            System.out.println(userSession);
        }
    }

//    @Test
//    public void testLocationsDAO(){
//        Location location = Location.builder()
//                .name("Minsk")
//                .lon(15.688)
//                .lat(203.686)
//                .user(userDAO.findById(3L).get())
//                .build();
//        System.out.println(locationsDAO.save(location));
//        System.out.println(locationsDAO.findById(1L));
//        locationsDAO.delete(1L);
//    }
}

