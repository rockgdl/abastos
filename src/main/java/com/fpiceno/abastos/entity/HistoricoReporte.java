/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.entity;

import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author gnr_a
 */
@Entity
public class HistoricoReporte {
    @Id
    @GeneratedValue
    private Integer id;
    
    @Column(name="saldoFinal")
    private Double saldoFinal;
    
    @Column(name="cantidadFinal")
    private Double cantidad;
    
    @Column(name = "fechaInicio")
    private Date fechaInicio;
    
    @Column(name = "fechaFin")
    private Date fechaFin;
    
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "id_Producto")
    private Producto producto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(Double saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public HistoricoReporte() {
        this.saldoFinal = 0.0;
        this.cantidad = 0.0;
    }
    
    
    @Override
    public String toString() {
        return "HistoricoReporte{" + "id=" + id + ", saldoFinal=" + saldoFinal + ", cantidad=" + cantidad + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", producto=" + producto + '}';
    }
}
