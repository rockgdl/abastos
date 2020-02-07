/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.dao.mysql;

import com.fpiceno.abastos.config.HibernateUtil;
import com.fpiceno.abastos.entity.Bajas;
import com.fpiceno.abastos.entity.Bajas;
import com.fpiceno.abastos.entity.Bajas;
import com.fpicneo.abastos.dao.BajasDao;
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
public class BajasDaoMysql implements BajasDao{

     @Override
    public void agregarBajas(Bajas baja) throws ConnectException, JDBCConnectionException, CommunicationsException, InvocationTargetException, ExceptionInInitializerError {
        Session session = getSession();
        session.beginTransaction();

        session.save(baja);
        session.getTransaction().commit();

        getSession().close();
    }

    @Override
    public void updateBajas(Bajas baja) throws ConnectException, JDBCConnectionException, CommunicationsException, InvocationTargetException, ExceptionInInitializerError {
         Session session = getSession();
        session.beginTransaction();
        session.update(baja);
        session.getTransaction().commit();

        getSession().close();   
    }

    @Override
    public void eliminarBajas(Bajas baja) throws ConnectException, JDBCConnectionException, CommunicationsException, InvocationTargetException, ExceptionInInitializerError {
        Session session = getSession();
        session.beginTransaction();

        session.delete(baja);
        session.getTransaction().commit();

        getSession().close();
    }

    @Override
    public List<Bajas> obtenerTodos() throws ConnectException, JDBCConnectionException, CommunicationsException, InvocationTargetException, ExceptionInInitializerError {
         Criteria cr = getSession().createCriteria(Bajas.class);
        return cr.list(); 
    }
    
    public Session getSession() {

        return HibernateUtil.getSession();
    }
    
    
}
