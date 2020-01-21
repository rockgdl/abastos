/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.entity;

import com.fpiceno.abastos.dto.UnidadMedida;
import java.util.Date;

/**
 *
 * @author piceno
 */
public class Producto {
    
    
    private Integer id;
    private String nombre;
    private String descripcion;
    private Date fechaAlta;
    private UnidadMedida unidad;
    private Double costoUnitario;
    private Double costoTotal;
    
    
}
