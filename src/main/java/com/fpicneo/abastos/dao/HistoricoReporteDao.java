/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpicneo.abastos.dao;

import com.fpiceno.abastos.entity.HistoricoReporte;
import com.fpiceno.abastos.entity.Producto;
import java.util.Date;
import java.util.List;

/**
 *
 * @author gnr_a
 */
public interface HistoricoReporteDao {
    
    public HistoricoReporte ultimoReporte(Date fecha, Producto produto);
    public List<HistoricoReporte> findHistorico();
    public void insertHistorico(HistoricoReporte historico);
    public void uptadeHistorico(Producto producto, Date fechaFin);
}
