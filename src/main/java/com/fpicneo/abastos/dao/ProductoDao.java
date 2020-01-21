/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpicneo.abastos.dao;

import com.fpiceno.abastos.entity.Producto;
import java.util.List;

/**
 *
 * @author fpiceno
 */
public interface ProductoDao {
    
    public void agregarProducto(Producto producto);
    public void updateProducto(Producto producto);
    public void eliminarProducto(Producto producto);
    public List<Producto> obtenerTodos();
    
    
}
