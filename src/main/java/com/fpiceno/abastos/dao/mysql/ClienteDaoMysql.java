/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.dao.mysql;

import com.fpiceno.abastos.config.HibernateUtil;
import com.fpicneo.abastos.dao.ClienteDao;
import com.fpiceno.abastos.entity.Cliente;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author gnr_a
 */
public class ClienteDaoMysql implements ClienteDao{

    @Override
    public void insert(Cliente cliente) {
        Session session = getSession();
        session.beginTransaction();

        session.save(cliente);
        session.getTransaction().commit();

        getSession().close();
    }

    @Override
    public void delete(Cliente cliente) {
        Session session = getSession();
        session.beginTransaction();

        session.delete(cliente);
        session.getTransaction().commit();

        getSession().close();
    }

    @Override
    public void update(Cliente cliente) {
        Session session = getSession();
        session.beginTransaction();

        session.update(cliente);
        session.getTransaction().commit();

        getSession().close();
    }

    @Override
    public List<Cliente> read() {
        Criteria cr = getSession().createCriteria(Cliente.class);
        return cr.list();
    }

    @Override
    public Cliente readCliente(int code) {
        Criteria cr = getSession().createCriteria(Cliente.class);
        cr.add(Restrictions.eq("id", code));

        return (Cliente) cr.uniqueResult();
    }
    
    @Override
    public List<Cliente> readLike(String cadena) {
        Criteria cr = getSession().createCriteria(Cliente.class);
        cr.add(Restrictions.like("nombre", "%"+cadena+"%"));
        return cr.list();
    }
    
    public Session getSession() {

        return HibernateUtil.getSession();
    }

    @Override
    public List<Cliente> readRFC(String cadena) {
        Criteria cr = getSession().createCriteria(Cliente.class);
        cr.add(Restrictions.like("rfc", "%"+cadena+"%"));
        return cr.list();
    }
    
}
