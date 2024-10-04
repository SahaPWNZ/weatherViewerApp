package com.example.weatherviewerapp.utils;

import com.example.weatherviewerapp.entity.Location;
import com.example.weatherviewerapp.entity.User;
import com.example.weatherviewerapp.entity.UserSession;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

    @Getter
    private static final SessionFactory sessionFactory;

    static {
        sessionFactory = getConfiguration()
                .buildSessionFactory();
    }

    private static Configuration getConfiguration() {
        return new Configuration()
                .setProperty("hibernate.connection.username", ConfigUtil.getDbUser())
                .setProperty("hibernate.connection.password", ConfigUtil.getDbPass())
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Location.class)
                .addAnnotatedClass(UserSession.class)
                .configure();
    }


}
