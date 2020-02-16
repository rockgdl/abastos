/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpicneo.abastos.dao;

import com.fpiceno.abastos.entity.Bajas;
import com.fpiceno.abastos.entity.Producto;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.util.Date;
import java.util.List;
import org.hibernate.exception.JDBCConnectionException;

/**
 *
 * @author gnr_a
 */
public interface BajasDao {
    public void agregarBajas(Bajas baja)throws ConnectException,JDBCConnectionException,CommunicationsException,InvocationTargetException,ExceptionInInitializerError;;
    public void updateBajas(Bajas baja)throws ConnectException,JDBCConnectionException,CommunicationsException,InvocationTargetException,ExceptionInInitializerError;;
    public void eliminarBajas(Bajas baja)throws ConnectException,JDBCConnectionException,CommunicationsException,InvocationTargetException,ExceptionInInitializerError;;
    public List<Bajas> obtenerTodos() throws ConnectException,JDBCConnectionException,CommunicationsException,InvocationTargetException,ExceptionInInitializerError;;
    public List<Bajas> findBajaWhithProducto(Producto producto) throws ConnectException,JDBCConnectionException,CommunicationsException,InvocationTargetException,ExceptionInInitializerError;;
    public List<Bajas> findBajaWhithFecha(Date fechaInicio, Date fechaFin) throws ConnectException,JDBCConnectionException,CommunicationsException,InvocationTargetException,ExceptionInInitializerError;;
    public List<Bajas> findBajaWhithProductoAndFecha (Producto producto, Date fechaInicio, Date fechaFin)throws ConnectException,JDBCConnectionException,CommunicationsException,InvocationTargetException,ExceptionInInitializerError;;
}
