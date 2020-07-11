/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.dao.mysql;

import com.fpiceno.abastos.config.HibernateUtil;
import com.fpiceno.abastos.entity.Producto;
import com.fpiceno.abastos.entity.Reporte;
import com.fpicneo.abastos.dao.ReporteDao;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author oswal
 */
public class ReporteDaoMysql implements ReporteDao{
    private final org.apache.log4j.Logger LOG= org.apache.log4j.Logger.getLogger(ProductoDaoMysql.class.getSimpleName());
    
    //Variable para hacer los reportes mensualemte, se toma el primer dia del mes
    private Date firstDay = new Date(new Date().getYear(), new Date().getMonth(), 1, 0, 0, 0);
    
    private Session getSession(){
                
        if (HibernateUtil.getSessionFactory()!=null &&  HibernateUtil.getSessionFactory().getCurrentSession().isOpen())
        {
            LOG.debug("hay una session activa");
                return HibernateUtil.getSessionFactory().getCurrentSession();
        }
        else
            return HibernateUtil.getSession();
    }
    

    @Override
    public List<Reporte> findReporte() {
        Session session = getSession();
        session.beginTransaction();
        Criteria cr=session.createCriteria(Reporte.class);
       // Criteria cr = getSession().createCriteria(Producto.class);
        
        
        cr.addOrder(Order.asc("fecha")).add(Restrictions.between("fecha", firstDay, new Date()));
        
        List<Reporte> lista=cr.list();
        session.close();
        return lista;  
    }

    @Override
    public List<Reporte> findReporteForTipoAndFecha(String tipo, Date fechaInicio, Date fechaFin) {
        Session session = getSession();
        session.beginTransaction();
        Criteria cr=session.createCriteria(Reporte.class);
       // Criteria cr = getSession().createCriteria(Producto.class);
        
        
        cr.addOrder(Order.asc("fecha")).add(Restrictions.between("fecha", fechaInicio, fechaFin)).add(Restrictions.eq("tipo", tipo));
        
        List<Reporte> lista=cr.list();
        session.close();
        return lista;  
    }

    @Override
    public List<Reporte> findReporteForTipo(String tipo) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        Criteria cr=session.createCriteria(Reporte.class);
        cr.add(Restrictions.eq("tipo", tipo)).add(Restrictions.between("fecha", firstDay, new Date()));
        List<Reporte> lista=cr.list();
        tx.commit();
        return lista;
    }

    @Override
    public List<Reporte> findReporteForFecha(Date fechaInicio, Date fechaFin) {
        Session session = getSession();
        session.beginTransaction();
        Criteria cr=session.createCriteria(Reporte.class);
       // Criteria cr = getSession().createCriteria(Producto.class);
        
        
        cr.addOrder(Order.asc("fecha")).add(Restrictions.between("fecha", fechaInicio, fechaFin));
        
        List<Reporte> lista=cr.list();
        session.close();
        return lista;  
    }

    @Override
    public List<Reporte> findReporteForProductoAndTipoAndFecha(Producto producto, String tipo, Date fechaInicio, Date fechaFin) {
        Session session = getSession();
        session.beginTransaction();
        Criteria cr=session.createCriteria(Reporte.class);
       // Criteria cr = getSession().createCriteria(Producto.class);
        
        
        cr.addOrder(Order.asc("fecha")).add(Restrictions.between("fecha", fechaInicio, fechaFin)).add(Restrictions.eq("producto", producto)).add(Restrictions.eq("tipo", tipo));
        
        List<Reporte> lista=cr.list();
        session.close();
        return lista;  
    }

    @Override
    public List<Reporte> findReporteForProductoAndTipo(Producto producto, String tipo) {
        Session session = getSession();
        session.beginTransaction();
        Criteria cr=session.createCriteria(Reporte.class);
       // Criteria cr = getSession().createCriteria(Producto.class);
        
        
        cr.addOrder(Order.asc("fecha")).add(Restrictions.eq("producto", producto)).add(Restrictions.eq("tipo", tipo)).add(Restrictions.between("fecha", firstDay, new Date()));
        
        List<Reporte> lista=cr.list();
        session.close();
        return lista;  
    }

    @Override
    public List<Reporte> findReporteForProductoAndFecha(Producto producto, Date fechaInicio, Date fechaFin) {
        Session session = getSession();
        session.beginTransaction();
        Criteria cr=session.createCriteria(Reporte.class);
       // Criteria cr = getSession().createCriteria(Producto.class);
        
        cr.addOrder(Order.asc("fecha")).add(Restrictions.between("fecha", fechaInicio, fechaFin)).add(Restrictions.eq("producto", producto));
        
        List<Reporte> lista=cr.list();
        session.close();
        return lista;  
    }

    @Override
    public List<Reporte> findReporteForProducto(Producto producto) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        Criteria cr=session.createCriteria(Reporte.class);
        cr.add(Restrictions.eq("producto", producto)).add(Restrictions.between("fecha", firstDay, new Date()));
        List<Reporte> lista=cr.list();
        tx.commit();
        return lista;
    }

    @Override
    public Double lastReporte(Date fechaInicio, Date fechaFinal) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        Criteria cr=session.createCriteria(Reporte.class);
        cr.add(Restrictions.between("fecha", fechaInicio, fechaFinal));
        List<Reporte> lista=cr.list();
        
        
        Double total=0.0;
        
         for(Reporte reporte: lista){
             Double totalTemp = reporte.getPrecioVenta()*reporte.getCantidad();
             
             if(reporte.getTipo().replace(" ", "").equals("alta")){
                 total += totalTemp;
             }else{
                 total -= totalTemp;
             }    
         }
         
        tx.commit();
        return total;
    }
    
}
