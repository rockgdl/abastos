/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.entity;

import com.fpiceno.abastos.dto.UnidadMedida;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author piceno
 */
@Entity
@Table(name="producto")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="nombre", unique = true)
    private String nombre;
    @Column(name="descripcion")
    private String descripcion;
  
    @Column(name="fechaAlta")
    private Date fechaAlta;
    @Enumerated(EnumType.STRING)
    private UnidadMedida unidad;
    
    @Column(name="costoTotal")
    private Double costoTotal;
    
    @OneToMany( fetch = FetchType.LAZY, orphanRemoval = false, mappedBy = "producto") 
    private List<Altas> listaAltas = new ArrayList<Altas>();
    
    @OneToMany( fetch = FetchType.LAZY, orphanRemoval = false, mappedBy ="producto" ) 
    private List<Bajas> listaBajas = new ArrayList<Bajas>();

    @Column (name = "stock")
    private Double stock;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public UnidadMedida getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadMedida unidad) {
        this.unidad = unidad;
    }

    public Double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(Double costoTotal) {
        this.costoTotal = costoTotal;
    }

    @Override
    public String toString() {
        //return "Producto{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", cantidad=" + cantidad + ", fechaAlta=" + fechaAlta + ", unidad=" + unidad + ", costoUnitario=" + costoUnitario + ", costoTotal=" + costoTotal + '}';
        return this.nombre;
    }

    /**
     * @return the stock
     */
    public Double getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(Double stock) {
        this.stock = stock;
    }   

    /**
     * @return the listaAltas
     */
    public List<Altas> getListaAltas() {
        return listaAltas;
    }

    /**
     * @param listaAltas the listaAltas to set
     */
    public void setListaAltas(List<Altas> listaAltas) {
        this.listaAltas = listaAltas;
    }

    /**
     * @return the listaBajas
     */
    public List<Bajas> getListaBajas() {
        return listaBajas;
    }

    /**
     * @param listaBajas the listaBajas to set
     */
    public void setListaBajas(List<Bajas> listaBajas) {
        this.listaBajas = listaBajas;
    }
    
}