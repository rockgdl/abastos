/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.dao.mysql;

import com.fpiceno.abastos.config.HibernateUtil;
import com.fpiceno.abastos.entity.HistoricoReporte;
import com.fpiceno.abastos.entity.Producto;
import com.fpiceno.abastos.entity.Reporte;
import com.fpicneo.abastos.dao.HistoricoReporteDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author gnr_a
 */
public class HistoricoReporteDaoMysql implements HistoricoReporteDao{
    private final org.apache.log4j.Logger LOG= org.apache.log4j.Logger.getLogger(HistoricoReporteDaoMysql.class.getSimpleName());
    
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
    public HistoricoReporte ultimoReporte(Date fecha, Producto produto) {
//        String sentencia = "SELECT fechaInicio, max(fechaFin), cantidadFinal, saldoFinal FROM historicoReporte GROUP BY id_producto";
//        
//        Session session = getSession();
//        session.beginTransaction();
//        
//        Query query = session.createQuery(sentencia);
//        
//        List<HistoricoReporte> lista = query.list();
//        session.close();
//        
//        return lista;  
        
        Session session = getSession();
        session.beginTransaction();
        
        Criteria cr = session.createCriteria(HistoricoReporte.class);
        
        cr.add(Restrictions.lt("fechaFin", fecha)).add(Restrictions.eq("producto", produto)).addOrder(Order.desc("fechaFin"));
        
        
        return (HistoricoReporte) cr.list().get(0);
    }

    @Override
    public List<HistoricoReporte> findHistorico() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertHistorico(HistoricoReporte historico) {
        Session session = getSession();
        
        session.beginTransaction();
        
        session.save(historico);
        
        session.getTransaction().commit();
        
        session.close();
    }

    @Override
    public void uptadeHistorico(Producto producto, Date fechaFin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
