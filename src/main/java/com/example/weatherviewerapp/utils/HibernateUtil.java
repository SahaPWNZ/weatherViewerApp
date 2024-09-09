package com.example.weatherviewerapp.utils;

import com.example.weatherviewerapp.entity.Location;
import com.example.weatherviewerapp.entity.Session;
import com.example.weatherviewerapp.entity.User;


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
    private static Configuration getConfiguration(){
        return new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Location.class)
                .addAnnotatedClass(Session.class)
                .configure();
    }

}
