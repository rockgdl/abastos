/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpicneo.abastos.dao;

import com.fpiceno.abastos.entity.Producto;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.util.List;
import org.hibernate.exception.JDBCConnectionException;

/**
 *
 * @author fpiceno
 */
public interface ProductoDao {
    
    public void agregarProducto(Producto producto)throws ConnectException,JDBCConnectionException,CommunicationsException,InvocationTargetException,ExceptionInInitializerError;;
    public void updateProducto(Producto producto)throws ConnectException,JDBCConnectionException,CommunicationsException,InvocationTargetException,ExceptionInInitializerError;;
    public void eliminarProducto(Producto producto)throws ConnectException,JDBCConnectionException,CommunicationsException,InvocationTargetException,ExceptionInInitializerError;;
    public List<Producto> obtenerTodos() throws ConnectException,JDBCConnectionException,CommunicationsException,InvocationTargetException,ExceptionInInitializerError;;
    
    
}
