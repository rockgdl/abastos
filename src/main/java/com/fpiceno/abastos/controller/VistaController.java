/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.controller;

import com.fpiceno.abastos.dao.mysql.AltasDaoMysql;
import com.fpiceno.abastos.dao.mysql.BajasDaoMysql;
import com.fpiceno.abastos.dao.mysql.ProductoDaoMysql;
import com.fpiceno.abastos.dto.UnidadMedida;
import com.fpiceno.abastos.entity.Producto;
import com.fpicneo.abastos.dao.AltasDao;
import com.fpicneo.abastos.dao.BajasDao;
import com.fpicneo.abastos.dao.ProductoDao;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
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
    
    @FXML TableView<Object> tabla;
    @FXML TableColumn <Object, String> columnProducto, columnFecha;
    @FXML TableColumn <Object, Double> columnPrecio, columnTotal;
    @FXML TableColumn <Object, Integer> columnId, columnCantidad;
    @FXML TableColumn <Object, UnidadMedida> columnUnidad;

    private ObservableList <Object> oblist= FXCollections.observableArrayList();
    
    @FXML ComboBox<Producto> boxProducto;
    @FXML ComboBox<String> boxTipo;
    
    @FXML DatePicker txtFechaInicio, txtFechaFin;
    
    AltasDao daoA = new AltasDaoMysql();
    BajasDao daoB = new BajasDaoMysql();
    ProductoDao daoP = new ProductoDaoMysql();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        obtenerAltas();
        String[] tipos = {"Alta", "Baja"};
        
        boxTipo.getItems().addAll(tipos);
        
        boxTipo.getSelectionModel().select(0);
        
        obtenerAltas();
        try {
            boxProducto.getItems().setAll(daoP.obtenerTodos());
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
    
    public void obtenerAltas(){
        try {
            tabla.getItems().clear();

            oblist.addAll(daoA.obtenerTodos());

            columnCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
            columnUnidad.setCellValueFactory(new PropertyValueFactory("unidad"));
            columnFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
            columnId.setCellValueFactory(new PropertyValueFactory("id"));
            columnPrecio.setCellValueFactory(new PropertyValueFactory("precioVenta"));
            columnProducto.setCellValueFactory(new PropertyValueFactory("producto"));
            columnTotal.setCellValueFactory(new PropertyValueFactory("precioTotal"));

            tabla.setItems(oblist);
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
       
    public void obtenerBajas(){
        try {
            tabla.getItems().clear();

            oblist.addAll(daoB.obtenerTodos());
            columnCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
            columnUnidad.setCellValueFactory(new PropertyValueFactory("unidad"));
            columnFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
            columnId.setCellValueFactory(new PropertyValueFactory("id"));
            columnPrecio.setCellValueFactory(new PropertyValueFactory("precioVenta"));
            columnProducto.setCellValueFactory(new PropertyValueFactory("producto"));

            tabla.setItems(oblist);
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
            try {
                tabla.getItems().clear();
                Date fechaInicio = null, fechaFin = null;
                
                if(txtFechaFin.getValue() == null){
                    fechaFin = new Date();
                }else{
                    fechaFin = Date.from(txtFechaFin.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                    fechaFin.setHours(23);
                    fechaFin.setMinutes(59);
                    fechaFin.setSeconds(59);
                }
                
                if(boxProducto.getValue() != null && txtFechaInicio.getValue() != null){
                    fechaInicio = Date.from(txtFechaInicio.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                    
                    if(boxTipo.getValue() == "Alta"){
                        oblist.setAll(daoA.findAltaWhithProductoAndFecha(boxProducto.getValue(), fechaInicio, fechaFin));
                    }else if(boxTipo.getValue() == "Baja"){
                        oblist.setAll(daoB.findBajaWhithProductoAndFecha(boxProducto.getValue(), fechaInicio, fechaFin));
                    }
                    
                }else if (boxProducto.getValue() != null){
                    
                    if(boxTipo.getValue() == "Alta"){
                        oblist.setAll(daoA.findAltaWhithProducto(boxProducto.getValue()));
                    }else if(boxTipo.getValue() == "Baja"){
                        oblist.setAll(daoB.findBajaWhithProducto(boxProducto.getValue()));
                    }
                    
                }else if (txtFechaInicio.getValue() != null){
                    fechaInicio = Date.from(txtFechaInicio.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                    
                    
                    if(boxTipo.getValue() == "Alta"){
                        oblist.setAll(daoA.findAltaWhithFecha(fechaInicio, fechaFin));
                    }else if(boxTipo.getValue() == "Baja"){
                        oblist.setAll(daoB.findBajaWhithFecha(fechaInicio, fechaFin));
                    }
                }else{
                    if(boxTipo.getValue() == "Alta"){
                        oblist.setAll(daoA.obtenerTodos());
                    }else if(boxTipo.getValue() == "Baja"){
                        oblist.setAll(daoB.obtenerTodos());
                    }
                }
                
                tabla.setItems(oblist);
                
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
    
}
