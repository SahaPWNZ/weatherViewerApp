import com.example.weatherviewerapp.dao.SessionDAO;
import com.example.weatherviewerapp.dao.UserDAO;
import com.example.weatherviewerapp.entity.User;
import com.example.weatherviewerapp.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestDataBaseRunner {
    public static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static final UserDAO userDAO = new UserDAO();
    public static final SessionDAO sessionDAO = new SessionDAO();

    @Test
    public void testConnection(){
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            System.out.println("Транзакция успешно открыта");
            session.getTransaction().commit();
        }
    }
    @Test
    public void testUserDAO(){
        System.out.println(userDAO.findById(1L));
        List<User> listU = userDAO.findAll();
        for (User user: listU){
            System.out.println(user);
        }
    }



}

