/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.entity;

import com.fpiceno.abastos.dto.UnidadMedida;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.Subselect;

/**
 *
 * @author oswal
 */
@Entity
@Subselect("SELECT CONCAT('Ba', b.id) AS folio, b.*, 'baja' as tipo FROM bajas b INNER JOIN Producto p ON p.id=b.id_producto UNION ALL SELECT CONCAT('Al', a.id) AS folio, a.id, a.cantidad, a.fecha, a.precioVenta, a.unidad, a.id_producto, 'alta' as tipo FROM altas a INNER JOIN Producto p ON p.id=a.id_producto")
public class Reporte {
    
    @Id
    private String folio;
    
    //Este es el id que corresponde al registro tanto de la tabla altas como la tablas de basjas
    @Column
    private Integer id;
    
    @Column
    private Double cantidad;
    
    @Column
    private Date fecha;
    
    @Column
    private Double precioVenta;
    
    @Enumerated(EnumType.STRING)
    private UnidadMedida unidad;
    
    @ManyToOne( fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_producto", referencedColumnName = "id")
    private Producto producto;
    
    @Column
    private String tipo;

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
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the folio
     */
    public String getFolio() {
        return folio;
    }

    /**
     * @param folio the folio to set
     */
    public void setFolio(String folio) {
        this.folio = folio;
    }

    @Override
    public String toString() {
        return "Reporte{" + "folio=" + folio + ", id=" + id + ", cantidad=" + cantidad + ", fecha=" + fecha + ", precioVenta=" + precioVenta + ", unidad=" + unidad + ", producto=" + producto + ", tipo=" + tipo + '}';
    }
    
    
}
