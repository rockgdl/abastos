/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.controller;

import com.fpiceno.abastos.dao.mysql.AltasDaoMysql;
import com.fpiceno.abastos.dao.mysql.BajasDaoMysql;
import com.fpiceno.abastos.dao.mysql.ProductoDaoMysql;
import com.fpiceno.abastos.dao.mysql.ReporteDaoMysql;
import com.fpiceno.abastos.dto.UnidadMedida;
import com.fpiceno.abastos.entity.Producto;
import com.fpiceno.abastos.entity.Reporte;
import com.fpicneo.abastos.dao.AltasDao;
import com.fpicneo.abastos.dao.BajasDao;
import com.fpicneo.abastos.dao.ProductoDao;
import com.fpicneo.abastos.dao.ReporteDao;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;

/**
 * FXML Controller class
 *
 * @author gnr_a
 */
public class VistaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Logger LOG=Logger.getLogger(this.getClass().getSimpleName());
    
    @FXML TableView<Reporte> tabla;
    @FXML TableColumn <Reporte, String> columnProducto, columnFecha, columnTipo;
    @FXML TableColumn <Reporte, Double> columnPrecio, columnTotal;
    @FXML TableColumn <Reporte, Integer> columnId, columnCantidad;
    @FXML TableColumn <Reporte, UnidadMedida> columnUnidad;

    private ObservableList <Reporte> oblist= FXCollections.observableArrayList();
    
    @FXML ComboBox<Producto> boxProducto;
    @FXML ComboBox<String> boxTipo;
    
    @FXML DatePicker txtFechaInicio, txtFechaFin;
    @FXML Label lblCantidad, lblSaldoFinal, lblUltimoMes;
    
    ReporteDao daoR = new ReporteDaoMysql();
    ProductoDao daoP = new ProductoDaoMysql();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] tipos = {"","Alta", "Baja"};
        
        boxTipo.getItems().addAll(tipos);
        
        boxTipo.getSelectionModel().select(0);
        
        obtenerDatos();
    }    

    private void obtenerDatos(){
        
        columnCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        columnUnidad.setCellValueFactory(new PropertyValueFactory("unidad"));
        columnFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        columnId.setCellValueFactory(new PropertyValueFactory("folio"));
        columnTipo.setCellValueFactory(new PropertyValueFactory("tipo"));
        columnPrecio.setCellValueFactory(new PropertyValueFactory("precioVenta"));
        columnProducto.setCellValueFactory(new PropertyValueFactory("producto"));
        columnTotal.setCellValueFactory(new PropertyValueFactory("saldoFinal"));
        
        try{
            oblist.addAll(daoR.findReporte());
            boxProducto.getItems().setAll(daoP.obtenerTodos());
            
            Double saldo = saldoUltimoMes(new Date());
            
            calcular(oblist, saldo);
            tabla.setItems(oblist);
            
            lblUltimoMes.setText("El saldo del último mes es de: " + saldo);
        } catch (ConnectException ex) {
            LOG.info("Error de ConnectException:" + ex.getMessage());
                 Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("error de ConnectException");
            alerta.setContentText(ex.getMessage());
            alerta.show();
              System.out.println("trono al eliminar la excepcion connecException");
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDBCConnectionException ex) {

            LOG.info("Error de JDBCConnectionException:" + ex.getMessage());
                 Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("error de JDBCConnectionException");
            alerta.setContentText(ex.getMessage());
            alerta.show();
              System.out.println("trono al eliminar la excepcion JDBCConnectionException ");
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CommunicationsException ex) {

            LOG.info("Error de CommunactionsExceptions:" + ex.getMessage());
              System.out.println("trono al eliminar la excepcion CommunicationsException" );

            Alert alerta = new Alert(Alert.AlertType.ERROR);

            alerta.setHeaderText("Error de CommunicationsException");
            alerta.setContentText(ex.getMessage());
            alerta.setContentText(ex.getMessage());
            alerta.show();
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {

            LOG.info("Error de InvocationTargetException:" + ex.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("error de InvocationTargetException");
            alerta.setContentText(ex.getMessage());
            alerta.show();
              System.out.println("trono al eliminar la excepcion InvocationTargetException");
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExceptionInInitializerError ex) {

            LOG.info("Error de ContraintViolationException: " + ex.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("error de ExceptionInInitializerError");
            alerta.setContentText(ex.getMessage());
            alerta.show();
            System.out.println("trono al eliminar la excepcion ExceptionInInitializerError");
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex){
            LOG.info("Error de NullPointerException:" + ex.getMessage());

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("error de NullPointerException");
            alerta.setContentText(ex.getMessage());
            alerta.show();
              System.out.println("trono al eliminar la excepcion NullPointerException");
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConstraintViolationException ex) {

            LOG.info("Error de ContraintViolationException:" + ex.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("error de ConstraintViolationException");
            alerta.setContentText(ex.getMessage());
            alerta.show();
            System.out.println("trono al eliminar la excepcion ConstraintViolationException");
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);

            LOG.info("Error de SQLException: "+ ex.getMessage());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("error de SQLException");
            alerta.setContentText(ex.getMessage());
            alerta.show();
        }
    }
    

    
     @FXML private void buscar(ActionEvent event){
         
        if (verificar()){    
                tabla.getItems().clear();
                Date fechaInicio = null, fechaFin = null;
                Double ultimoMes = 0.0;
                 
                
                if(txtFechaFin.getValue() == null){
                    fechaFin = new Date();
                }else{
                    fechaFin = Date.from(txtFechaFin.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                    fechaFin.setHours(23);
                    fechaFin.setMinutes(59);
                    fechaFin.setSeconds(59);
                }
                
                //Iniciamos la comparacion de los casos 
               
                
                if(boxProducto.getValue() == null && txtFechaInicio.getValue() == null && boxTipo.getValue() == ""){
                //Si todos los campos estan vacios hace una busqueda en general    
                    oblist.addAll(daoR.findReporte());
                    ultimoMes = saldoUltimoMes(new Date());
                    
                }else if(boxProducto.getValue() != null && txtFechaInicio.getValue() != null && boxTipo.getValue() != ""){
                //Si todos los campos estan llenos buscara pot fecha producto y tipo
                    
                    fechaInicio = Date.from(txtFechaInicio.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                    oblist.addAll(daoR.findReporteForProductoAndTipoAndFecha(boxProducto.getValue(), boxTipo.getValue(), fechaInicio, fechaFin));
                    
                    ultimoMes = saldoUltimoMes(fechaInicio);
                    
                }else if(boxProducto.getValue() != null && txtFechaInicio.getValue() != null){
                //Buscar por producto y fecha   
                    fechaInicio = Date.from(txtFechaInicio.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                    oblist.addAll(daoR.findReporteForProductoAndFecha(boxProducto.getValue(), fechaInicio, fechaFin));
                    ultimoMes = saldoUltimoMes(fechaInicio);
                    
                }else if(boxProducto.getValue() != null && boxTipo.getValue() != ""){
                //Buscar por producto y tipo
                    
                    oblist.addAll(daoR.findReporteForProductoAndTipo(boxProducto.getValue(), boxTipo.getValue()));
                    ultimoMes = saldoUltimoMes(new Date());
                    
                }else if(boxTipo.getValue() != "" && txtFechaInicio.getValue() != null){
                //Buscar por tipo y fecha
                    fechaInicio = Date.from(txtFechaInicio.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                    oblist.addAll(daoR.findReporteForTipoAndFecha(boxTipo.getValue(), fechaInicio, fechaFin));
                    ultimoMes = saldoUltimoMes(fechaInicio);
                    
                }else if (boxTipo.getValue() != ""){
                //Buscar por tipo
                    oblist.addAll(daoR.findReporteForTipo(boxTipo.getValue()));
                    ultimoMes = saldoUltimoMes(new Date());
                    
                }else if(txtFechaInicio.getValue() != null){
                //Buscar por fecha
                    fechaInicio = Date.from(txtFechaInicio.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                    oblist.addAll(daoR.findReporteForFecha(fechaInicio, fechaFin));
                    ultimoMes = saldoUltimoMes(fechaInicio);
                    
                }else if(boxProducto.getValue() != null){
                //Buscar por producto    
                    oblist.addAll(daoR.findReporteForProducto(boxProducto.getValue()));
                    ultimoMes = saldoUltimoMes(new Date());
                }
                
                lblUltimoMes.setText("El saldo del último mes es de: " + ultimoMes);
                calcular(oblist, ultimoMes);
                tabla.setItems(oblist);
                
        }
                
            
    }

     private Boolean verificar(){
        Alert alerta = new Alert(Alert.AlertType.ERROR);

        if(txtFechaFin.getValue() != null && txtFechaInicio.getValue() == null){
            alerta.setHeaderText("Se encotro un error");
            alerta.setContentText("No puedes consultar con una fecha final sin una fehca de inicio");
            alerta.show();
            return false;
        }
        
        return true;
    }
     
     private void calcular(List<Reporte> listaReportes, Double ultimoMes){
         Double total=ultimoMes;
         Double cantidad=0.0;
         
         for(Reporte reporte: listaReportes){
             Double totalTemp = reporte.getPrecioVenta()*reporte.getCantidad();
             Double cantidadTemp = reporte.getCantidad();
             
             if(reporte.getTipo().replace(" ", "").equals("alta")){
                 total += totalTemp;
                 cantidad += cantidadTemp;
             }else{
                 total -= totalTemp;
                 cantidad -= cantidadTemp;
             }
             
             reporte.setSaldoFinal(total);    
         }
         
         lblCantidad.setText("Cantidad en almacen: " + cantidad);
         lblSaldoFinal.setText("Saldo Final: " + total);
     }
     
     public Double saldoUltimoMes(Date fecha){
         Calendar calI = Calendar.getInstance();
         calI.setTime(fecha);
         calI.add(Calendar.MONTH, -1);
         calI.set(Calendar.DAY_OF_MONTH, calI.getActualMinimum(Calendar.DAY_OF_MONTH));
            
         Calendar calF = Calendar.getInstance();
         calF.setTime(fecha);
         calF.add(Calendar.MONTH, -1);
         calF.set(Calendar.DAY_OF_MONTH, calI.getActualMaximum(Calendar.DAY_OF_MONTH));
         
         
         
         return daoR.lastReporte(calI.getTime(), calF.getTime());
     }
}
    





//    public void obtenerAltas(){
//        try {
//            tabla.getItems().clear();
//
//            oblist.addAll(daoR.obtenerTodos());
//
//            columnCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
//            columnUnidad.setCellValueFactory(new PropertyValueFactory("unidad"));
//            columnFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
//            columnId.setCellValueFactory(new PropertyValueFactory("id"));
//            columnPrecio.setCellValueFactory(new PropertyValueFactory("precioVenta"));
//            columnProducto.setCellValueFactory(new PropertyValueFactory("producto"));
//            columnTotal.setCellValueFactory(new PropertyValueFactory("precioTotal"));
//
//            tabla.setItems(oblist);
//        } catch (ConnectException ex) {
//            LOG.info("Error de ConnectException:" + ex.getMessage());
//                 Alert alerta = new Alert(Alert.AlertType.ERROR);
//            alerta.setHeaderText("error de ConnectException");
//            alerta.setContentText(ex.getMessage());
//            alerta.show();
//              System.out.println("trono al eliminar la excepcion connecException");
//            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JDBCConnectionException ex) {
//
//            LOG.info("Error de JDBCConnectionException:" + ex.getMessage());
//                 Alert alerta = new Alert(Alert.AlertType.ERROR);
//            alerta.setHeaderText("error de JDBCConnectionException");
//            alerta.setContentText(ex.getMessage());
//            alerta.show();
//              System.out.println("trono al eliminar la excepcion JDBCConnectionException ");
//            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (CommunicationsException ex) {
//
//            LOG.info("Error de CommunactionsExceptions:" + ex.getMessage());
//              System.out.println("trono al eliminar la excepcion CommunicationsException" );
//
//            Alert alerta = new Alert(Alert.AlertType.ERROR);
//
//            alerta.setHeaderText("Error de CommunicationsException");
//            alerta.setContentText(ex.getMessage());
//            alerta.setContentText(ex.getMessage());
//            alerta.show();
//            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InvocationTargetException ex) {
//
//            LOG.info("Error de InvocationTargetException:" + ex.getMessage());
//            Alert alerta = new Alert(Alert.AlertType.ERROR);
//            alerta.setHeaderText("error de InvocationTargetException");
//            alerta.setContentText(ex.getMessage());
//            alerta.show();
//              System.out.println("trono al eliminar la excepcion InvocationTargetException");
//            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ExceptionInInitializerError ex) {
//
//            LOG.info("Error de ContraintViolationException: " + ex.getMessage());
//            Alert alerta = new Alert(Alert.AlertType.ERROR);
//            alerta.setHeaderText("error de ExceptionInInitializerError");
//            alerta.setContentText(ex.getMessage());
//            alerta.show();
//            System.out.println("trono al eliminar la excepcion ExceptionInInitializerError");
//            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NullPointerException ex){
//            LOG.info("Error de NullPointerException:" + ex.getMessage());
//
//            Alert alerta = new Alert(Alert.AlertType.ERROR);
//            alerta.setHeaderText("error de NullPointerException");
//            alerta.setContentText(ex.getMessage());
//            alerta.show();
//              System.out.println("trono al eliminar la excepcion NullPointerException");
//            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ConstraintViolationException ex) {
//
//            LOG.info("Error de ContraintViolationException:" + ex.getMessage());
//            Alert alerta = new Alert(Alert.AlertType.ERROR);
//            alerta.setHeaderText("error de ConstraintViolationException");
//            alerta.setContentText(ex.getMessage());
//            alerta.show();
//            System.out.println("trono al eliminar la excepcion ConstraintViolationException");
//            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//
//            LOG.info("Error de SQLException: "+ ex.getMessage());
//            Alert alerta = new Alert(Alert.AlertType.ERROR);
//            alerta.setHeaderText("error de SQLException");
//            alerta.setContentText(ex.getMessage());
//            alerta.show();
//        }
//   }