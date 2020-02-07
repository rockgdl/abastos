/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.dao.mysql;

import com.fpiceno.abastos.config.HibernateUtil;
import com.fpiceno.abastos.entity.Altas;
import com.fpiceno.abastos.entity.Producto;
import com.fpicneo.abastos.dao.AltasDao;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.exception.JDBCConnectionException;

/**
 *
 * @author gnr_a
 */
public class AltasDaoMysql implements AltasDao{

    @Override
    public void agregarAltas(Altas alta) throws ConnectException, JDBCConnectionException, CommunicationsException, InvocationTargetException, ExceptionInInitializerError {
        Session session = getSession();
        session.beginTransaction();

        session.save(alta);
        session.getTransaction().commit();

        getSession().close();
    }

    @Override
    public void updateAltas(Altas alta) throws ConnectException, JDBCConnectionException, CommunicationsException, InvocationTargetException, ExceptionInInitializerError {
         Session session = getSession();
        session.beginTransaction();
        session.update(alta);
        session.getTransaction().commit();

        getSession().close();   
    }

    @Override
    public void eliminarAltas(Altas alta) throws ConnectException, JDBCConnectionException, CommunicationsException, InvocationTargetException, ExceptionInInitializerError {
        Session session = getSession();
        session.beginTransaction();

        session.delete(alta);
        session.getTransaction().commit();

        getSession().close();
    }

    @Override
    public List<Altas> obtenerTodos() throws ConnectException, JDBCConnectionException, CommunicationsException, InvocationTargetException, ExceptionInInitializerError {
         Criteria cr = getSession().createCriteria(Altas.class);
        return cr.list(); 
    }
    
    public Session getSession() {

        return HibernateUtil.getSession();
    }
    
}
