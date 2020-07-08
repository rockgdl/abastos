/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpicneo.abastos.dao;

import com.fpiceno.abastos.entity.Producto;
import com.fpiceno.abastos.entity.Reporte;
import java.util.Date;
import java.util.List;

/**
 *
 * @author oswal
 */
public interface ReporteDao {
    public List<Reporte> findReporte();
    public List<Reporte> findReporteProducto(Producto producto, String tipo, Date fechaInicio, Date fechaFin);
    public List<Reporte> findReporteForTipoAndFecha(String tipo, Date fechaInicio, Date fechaFin);
    public List<Reporte> findReporteForTipo(String tipo);
    public List<Reporte> findReporteForFecha(Date fechaInicio, Date fechaFin);
    
}
