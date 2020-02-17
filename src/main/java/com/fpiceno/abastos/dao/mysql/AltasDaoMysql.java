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
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
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

        session.close();
    }

    @Override
    public List<Altas> obtenerTodos() throws ConnectException, JDBCConnectionException, CommunicationsException, InvocationTargetException, ExceptionInInitializerError {
       
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        Criteria cr = session.createCriteria(Altas.class);
        
        List<Altas> lista = cr.list();
        tx.commit();
        return lista; 
    }
    
    public Session getSession() {
        if (HibernateUtil.getSessionFactory()!=null &&  HibernateUtil.getSessionFactory().getCurrentSession().isOpen())

        {
            System.out.println("hay una session activa");
                return HibernateUtil.getSessionFactory().getCurrentSession();
        }
    
        else
            return HibernateUtil.getSession();
    }

    @Override
    public List<Altas> findAltaWhithProducto(Producto producto) throws ConnectException, JDBCConnectionException, CommunicationsException, InvocationTargetException, ExceptionInInitializerError {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        Criteria cr = session.createCriteria(Altas.class);
        
        cr.add(Restrictions.eq("producto", producto));
        List<Altas> lista = cr.list();
        tx.commit();
        
        return lista;
    }

    @Override
    public List<Altas> findAltaWhithProductoAndFecha(Producto producto, Date fechaInicio, Date fechaFin) throws ConnectException, JDBCConnectionException, CommunicationsException, InvocationTargetException, ExceptionInInitializerError {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        Criteria cr = session.createCriteria(Altas.class);
        
        cr.add(Restrictions.eq("producto", producto)).add(Restrictions.between("fecha", fechaInicio, fechaFin));
        List<Altas> lista = cr.list();
        tx.commit();
        
        return lista;
    }

    @Override
    public List<Altas> findAltaWhithFecha(Date fechaInicio, Date fechaFin) throws ConnectException, JDBCConnectionException, CommunicationsException, InvocationTargetException, ExceptionInInitializerError {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        Criteria cr = session.createCriteria(Altas.class);
        
        cr.add(Restrictions.between("fecha", fechaInicio, fechaFin));
        List<Altas> lista = cr.list();
        tx.commit();
        
        return lista;
    }
    
}
