/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.entity;

import com.fpiceno.abastos.dto.UnidadMedida;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.*;
import org.hibernate.annotations.Subselect;

/**
 *
 * @author oswal
 */
@Entity
    @Subselect("SELECT b.*, 'baja' as tipo FROM bajas b INNER JOIN Producto p ON p.id=b.id_producto UNION ALL SELECT a.id, a.cantidad, a.fecha, a.precioVenta, a.unidad, a.id_producto, 'alta' as tipo FROM altas a INNER JOIN Producto p ON p.id=a.id_producto")
public class Reporte {
    
    @Id
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
    
    @Transient
    private Double saldoFinal = 0.0;
    
    @Transient
    private Double saldo;

    public Double getSaldo() {
        if (getTipo().replace(" ", "").equals("alta"))
            return precioVenta*cantidad;
        else
            return 0-(precioVenta*cantidad);
    }


    public Double getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(Double saldoFinal) {
        this.saldoFinal = saldoFinal;
    }
    
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
    public String getFecha() {
        
        SimpleDateFormat formato = new SimpleDateFormat ("dd/MM/yyyy");
        return formato.format(fecha);
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

    @Override
    public String toString() {
        return "Reporte{" + "id=" + id + ", cantidad=" + cantidad + ", fecha=" + fecha + ", precioVenta=" + precioVenta + ", unidad=" + unidad + ", producto=" + producto + ", tipo=" + tipo + '}';
    }
    
    
}
