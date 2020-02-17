/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.dao.mysql;

import com.fpiceno.abastos.config.HibernateUtil;
import com.fpiceno.abastos.entity.Altas;
import com.fpiceno.abastos.entity.Bajas;
import com.fpiceno.abastos.entity.Bajas;
import com.fpiceno.abastos.entity.Bajas;
import com.fpiceno.abastos.entity.Producto;
import com.fpicneo.abastos.dao.BajasDao;
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
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        Criteria cr = session.createCriteria(Bajas.class);
        
        List<Bajas> lista = cr.list();
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
    public List<Bajas> findBajaWhithProducto(Producto producto) throws ConnectException, JDBCConnectionException, CommunicationsException, InvocationTargetException, ExceptionInInitializerError {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        Criteria cr = session.createCriteria(Bajas.class);
        
        cr.add(Restrictions.eq("producto", producto));
        List<Bajas> lista = cr.list();
        tx.commit();
        
        return lista;
    }

    @Override
    public List<Bajas> findBajaWhithFecha(Date fechaInicio, Date fechaFin) throws ConnectException, JDBCConnectionException, CommunicationsException, InvocationTargetException, ExceptionInInitializerError {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        Criteria cr = session.createCriteria(Bajas.class);
        
        cr.add(Restrictions.between("fecha", fechaInicio, fechaFin));
        List<Bajas> lista = cr.list();
        tx.commit();
        
        return lista;
    }

    @Override
    public List<Bajas> findBajaWhithProductoAndFecha(Producto producto, Date fechaInicio, Date fechaFin) throws ConnectException, JDBCConnectionException, CommunicationsException, InvocationTargetException, ExceptionInInitializerError {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        Criteria cr = session.createCriteria(Bajas.class);
        
        cr.add(Restrictions.eq("producto", producto)).add(Restrictions.between("fecha", fechaInicio, fechaFin));
        List<Bajas> lista = cr.list();
        tx.commit();
        
        return lista;
    }
    
    
}
