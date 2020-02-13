/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpicneo.abastos.dao;

import com.fpiceno.abastos.entity.Cliente;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import org.hibernate.exception.JDBCConnectionException;

/**
 *
 * @author gnr_a
 */
public interface ClienteDao {
    public void insert(Cliente cliente)throws SQLIntegrityConstraintViolationException, ConnectException,JDBCConnectionException,CommunicationsException,InvocationTargetException,ExceptionInInitializerError;
    public void delete(Cliente cliente) throws ConnectException,JDBCConnectionException,CommunicationsException,InvocationTargetException,ExceptionInInitializerError;
    public void update(Cliente cliente) throws ConnectException,JDBCConnectionException,CommunicationsException,InvocationTargetException,ExceptionInInitializerError;
    public List<Cliente> read() throws ConnectException,JDBCConnectionException,CommunicationsException,InvocationTargetException,ExceptionInInitializerError;
    public Cliente readCliente(int code) throws ConnectException,JDBCConnectionException,CommunicationsException,InvocationTargetException,ExceptionInInitializerError;
    public List<Cliente> readLike(String cadena) throws ConnectException,JDBCConnectionException,CommunicationsException,InvocationTargetException,ExceptionInInitializerError;
    public List<Cliente> readRFC(String cadena) throws ConnectException,JDBCConnectionException,CommunicationsException,InvocationTargetException,ExceptionInInitializerError;

    
}
