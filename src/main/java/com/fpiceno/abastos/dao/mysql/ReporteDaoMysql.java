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
        cr.addOrder(Order.asc("fecha"));
        List<Reporte> lista=cr.list();
        session.close();
        return lista;  
    }

    @Override
    public List<Reporte> findReporteProducto(Producto producto, String tipo, Date fechaInicio, Date fechaFin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reporte> findReporteForTipoAndFecha(String tipo, Date fechaInicio, Date fechaFin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reporte> findReporteForTipo(String tipo) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        Criteria cr=session.createCriteria(Reporte.class);
        cr.add(Restrictions.eq("tipo", tipo));
        List<Reporte> lista=cr.list();
        tx.commit();
        return lista;
    }

    @Override
    public List<Reporte> findReporteForFecha(Date fechaInicio, Date fechaFin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
