/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.dao.mysql;

import com.fpiceno.abastos.config.HibernateUtil;
import com.fpiceno.abastos.entity.Producto;
import com.fpicneo.abastos.dao.ProductoDao;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.JDBCConnectionException;

import org.hibernate.Transaction;


/**
 *
 * @author fpiceno
 */
public class ProductoDaoMysql implements ProductoDao{

    @Override
    public void agregarProducto(Producto producto) {
        Session session = getSession();
        session.beginTransaction();

        session.save(producto);
        session.getTransaction().commit();

        getSession().close();
    }

    @Override
    public void updateProducto(Producto producto) {
 Session session = getSession();
        session.beginTransaction();
        session.update(producto);
        session.getTransaction().commit();

        getSession().close();    }

    @Override
    public void eliminarProducto(Producto producto) {
       Session session = getSession();

       Transaction tx = session.beginTransaction();
        session.delete(producto);

        session.flush();
        session.getTransaction().commit();
       
        tx.commit();

        getSession().close();
    }

    @Override
    public List<Producto> obtenerTodos() {
        
         Session session = getSession();
         Transaction tx = session.beginTransaction();
         Criteria cr=session.createCriteria(Producto.class);
       // Criteria cr = getSession().createCriteria(Producto.class);
         List<Producto> lista=cr.list();
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

    public List<Producto> findProducto(Producto producto) throws ConnectException, JDBCConnectionException, CommunicationsException, InvocationTargetException, ExceptionInInitializerError {
        Criteria cr = getSession().createCriteria(Producto.class);
        cr.add(Restrictions.eq("nombre", producto.getNombre()));
        return cr.list();
    }
    
}
