/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.config;

import java.io.File;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class HibernateUtil{
    private static final SessionFactory sessionFactory;
    static
    {
        try
        {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
           // configuration.configure("com/fpiceno/abogados/config/hibernate.cfg.xml");
           // StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
             //ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
           // sessionFactory = configuration.buildSessionFactory();
                        sessionFactory = configuration.buildSessionFactory();

      }
        catch (Throwable ex)
        {
          // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);

         }

    }

    public static SessionFactory getSessionFactory(){

       return sessionFactory;

    }
    public static Session getSession() throws HibernateException{
        
       Session session;
       session = getSessionFactory().openSession(); 
       return session;
    }

}