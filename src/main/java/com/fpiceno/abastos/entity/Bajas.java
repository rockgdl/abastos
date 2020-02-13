/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.entity;

import com.fpiceno.abastos.dto.UnidadMedida;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author gnr_a
 */

@Entity
@Table (name = "Bajas")
public class Bajas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "fechaBaja", nullable = false)
    private Date fecha;
    
    @ManyToOne( fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "producto", referencedColumnName = "id")
    private Producto producto;
    
    @Enumerated(EnumType.STRING)
    private UnidadMedida unidad;
    
    @Column(name = "cantidad", nullable = false)
    private Double cantidad;
    
    @Column(name = "precioVenta", nullable = false)
    private Double precioVenta;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * @return the unidad
     */
    public UnidadMedida getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(UnidadMedida unidad) {
        this.unidad = unidad;
    }

    /**
     * @return the cantidad
     */
    public Double getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the precioVenta
     */
    public Double getPrecioVenta() {
        return precioVenta;
    }

    /**
     * @param precioVenta the precioVenta to set
     */
    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }
}
