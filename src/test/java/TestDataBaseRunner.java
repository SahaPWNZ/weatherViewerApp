import com.example.weatherviewerapp.dao.SessionDAO;
import com.example.weatherviewerapp.dao.UserDAO;
import com.example.weatherviewerapp.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

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


}

