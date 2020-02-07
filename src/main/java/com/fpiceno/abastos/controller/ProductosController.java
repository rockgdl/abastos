package com.fpiceno.abastos.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fpiceno.abastos.dao.mysql.AltasDaoMysql;
import com.fpiceno.abastos.dao.mysql.BajasDaoMysql;
import com.fpiceno.abastos.dao.mysql.ProductoDaoMysql;
import com.fpiceno.abastos.dto.UnidadMedida;
import com.fpiceno.abastos.entity.Altas;
import com.fpiceno.abastos.entity.Bajas;
import com.fpiceno.abastos.entity.Producto;
import com.fpicneo.abastos.dao.AltasDao;
import com.fpicneo.abastos.dao.BajasDao;
import com.fpicneo.abastos.dao.ProductoDao;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import org.hibernate.exception.JDBCConnectionException;

/**
 * FXML Controller class
 *
 * @author piceno
 */
public class ProductosController implements Initializable {
    
        private final org.apache.log4j.Logger LOG= org.apache.log4j.Logger.getLogger(ProductosController.class.getSimpleName());


       @FXML TableView<Producto> tablaProductos;
       @FXML TableColumn <Producto, String> columnNombre, columnDescripcion, columnFecha;
       @FXML TableColumn <Producto, Double> columnCantidad, columnCostoUnit, columnCostoTotal;
       @FXML TableColumn <Producto, Integer> columnId;
       @FXML private FlowPane rootPane;

       private ProductoDao dao=new ProductoDaoMysql();
       private ObservableList <Producto> oblistProducto= FXCollections.observableArrayList();

       
       @FXML TableView<Altas> tablaAltas;
       @FXML TableColumn <Altas, String> columnFechaAltas, columnProductoAltas, columnUnidadAltas;
       @FXML TableColumn <Altas, Double> columnPrecioAltas;
       @FXML TableColumn <Altas, Integer> columnIdAltas, columnCantidadAltas;
       private ObservableList <Altas> oblistAltas= FXCollections.observableArrayList();
       private AltasDao daoA=new AltasDaoMysql();
       
       @FXML TextField txtPrecioAlta, txtCantidadAlta;
       @FXML ComboBox boxProductoAlta, boxUnidadAlta;
       
       @FXML TableView<Bajas> tablaBajas;
       @FXML TableColumn <Bajas, String> columnFechaBajas, columnProductoBajas, columnUnidadBajas;
       @FXML TableColumn <Bajas, Double> columnPrecioBajas;
       @FXML TableColumn <Bajas, Integer> columnIdBajas, columnCantidadBajas;
       private ObservableList <Bajas> oblistBajas= FXCollections.observableArrayList();
       private BajasDao daoB=new BajasDaoMysql();
       @FXML TextField txtPrecioBaja, txtCantidadBaja;
       @FXML ComboBox boxProductoBaja, boxUnidadBaja;
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            try {
                obtenerProductos();
                obtenerAltas();
                obtenerBajas();
                
                boxProductoAlta.getItems().setAll(dao.obtenerTodos());
                boxUnidadAlta.getItems().setAll(UnidadMedida.values());
                
                boxProductoBaja.getItems().setAll(dao.obtenerTodos());
                boxUnidadBaja.getItems().addAll(UnidadMedida.values());
                
            } catch (ConnectException ex) {
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("No se pudo conectar a mysql");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (JDBCConnectionException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("Se encontro un error al quere insertar la información");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (CommunicationsException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("No se pudo comunicar con la base de datos mysql");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (InvocationTargetException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExceptionInInitializerError ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }    
    
     @FXML private void agregarProducto()throws IOException
     {
        BorderPane pane;
        LOG.info("cargando vista Administrador");
        pane= FXMLLoader.load(getClass().getResource("/fxml/Principal.fxml"));
        rootPane.getChildren().setAll(pane);
     }
    
       public void obtenerProductos(){
        try {
            tablaProductos.getItems().clear();
            
            
            oblistProducto.addAll(dao.obtenerTodos());
            columnId.setCellValueFactory(new PropertyValueFactory("id"));
            columnNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
            columnDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
            columnCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
            columnFecha.setCellValueFactory(new PropertyValueFactory("fechaAlta"));
            columnCostoUnit.setCellValueFactory(new PropertyValueFactory("costoUnitario"));
            columnCostoTotal.setCellValueFactory(new PropertyValueFactory("costoTotal"));
            
            
            tablaProductos.setItems(oblistProducto);
        } catch (ConnectException ex) {
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("No se pudo conectar a mysql");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (JDBCConnectionException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("Se encontro un error al quere insertar la información");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (CommunicationsException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("No se pudo comunicar con la base de datos mysql");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (InvocationTargetException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExceptionInInitializerError ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
       
       public void obtenerAltas(){
            try {
                tablaAltas.getItems().clear();
                
                oblistAltas.addAll(daoA.obtenerTodos());
                
                columnCantidadAltas.setCellValueFactory(new PropertyValueFactory("cantidad"));
                columnUnidadAltas.setCellValueFactory(new PropertyValueFactory("unidad"));
                columnFechaAltas.setCellValueFactory(new PropertyValueFactory("fecha"));
                columnIdAltas.setCellValueFactory(new PropertyValueFactory("id"));
                columnPrecioAltas.setCellValueFactory(new PropertyValueFactory("precioVenta"));
                columnProductoAltas.setCellValueFactory(new PropertyValueFactory("producto"));
                
                tablaAltas.setItems(oblistAltas);
            }catch (ConnectException ex) {
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("No se pudo conectar a mysql");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (JDBCConnectionException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("Se encontro un error al quere insertar la información");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (CommunicationsException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("No se pudo comunicar con la base de datos mysql");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (InvocationTargetException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExceptionInInitializerError ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
       
       public void obtenerBajas(){
            try {
                tablaBajas.getItems().clear();
                
                oblistBajas.addAll(daoB.obtenerTodos());
                columnCantidadBajas.setCellValueFactory(new PropertyValueFactory("cantidad"));
                columnUnidadBajas.setCellValueFactory(new PropertyValueFactory("unidad"));
                columnFechaBajas.setCellValueFactory(new PropertyValueFactory("fecha"));
                columnIdBajas.setCellValueFactory(new PropertyValueFactory("id"));
                columnPrecioBajas.setCellValueFactory(new PropertyValueFactory("precioVenta"));
                columnProductoBajas.setCellValueFactory(new PropertyValueFactory("producto"));
                tablaBajas.setItems(oblistBajas);
            }catch (ConnectException ex) {
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("No se pudo conectar a mysql");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (JDBCConnectionException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("Se encontro un error al quere insertar la información");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (CommunicationsException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("No se pudo comunicar con la base de datos mysql");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (InvocationTargetException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExceptionInInitializerError ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
       
       
       @FXML private void editarProducto(MouseEvent event) throws IOException{
           Producto producto = tablaProductos.getSelectionModel().getSelectedItem();
            if(event.getClickCount() == 2 && producto !=null){
                FXMLLoader load = new FXMLLoader(getClass().getResource("/fxml/Principal.fxml"));
                BorderPane pane;
                LOG.info("cargando vista Editable");
                pane= load.load();
                PrincipalController controlador = load.getController();
                controlador.cargarDatos(producto);
                rootPane.getChildren().setAll(pane);
            }
       }
       
       @FXML private void eliminarProducto(ActionEvent event){
            try {
                ProductoDao dao = new ProductoDaoMysql();
                
                dao.eliminarProducto(tablaProductos.getSelectionModel().getSelectedItem());
            } catch (ConnectException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JDBCConnectionException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CommunicationsException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExceptionInInitializerError ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex){
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
    
    @FXML private void agregarAlta(ActionEvent event){
        Altas alta = new Altas();
            
        try{
            alta.setCantidad(Double.parseDouble(txtCantidadAlta.getText()));
            alta.setFecha(new Date());
            alta.setPrecioVenta(Double.parseDouble(txtPrecioAlta.getText()));
            alta.setUnidad((UnidadMedida) boxUnidadAlta.getValue());
            
            alta.setProducto((Producto) boxProductoAlta.getValue());
            
            daoA.agregarAltas(alta);
            obtenerAltas();
        }catch(NumberFormatException ex){
            System.out.println(ex.getMessage()+" No es un numero");
        }catch (ConnectException ex) {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDBCConnectionException ex) {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CommunicationsException ex) {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExceptionInInitializerError ex) {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML private void EliminarAlta(ActionEvent event){
        Altas alta = tablaAltas.getSelectionModel().getSelectedItem();
        
        if(alta != null){
            try {
                daoA.eliminarAltas(alta);
                obtenerAltas();
            } catch (ConnectException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JDBCConnectionException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CommunicationsException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExceptionInInitializerError ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Debe de seleccionar un campo");
            alert.show();
        }
    }
    
    @FXML private void EditarAlta(ActionEvent event){
        Altas alta = tablaAltas.getSelectionModel().getSelectedItem();
        if(alta !=null ){ 
            try{

                if(!txtCantidadAlta.getText().equals("")){
                    alta.setCantidad(Double.parseDouble(txtCantidadAlta.getText()));
                }
                
                if(!txtPrecioAlta.getText().equals("")){
                    alta.setPrecioVenta(Double.parseDouble(txtPrecioAlta.getText()));
                }
                
                alta.setUnidad((UnidadMedida) boxUnidadAlta.getValue());

                alta.setProducto((Producto) boxProductoAlta.getValue());

                daoA.updateAltas(alta);
                obtenerAltas();
            }catch(NumberFormatException ex){
                System.out.println(ex.getMessage()+" No es un numero");
            }catch (ConnectException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JDBCConnectionException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CommunicationsException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExceptionInInitializerError ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Debe de seleccionar un campo");
            alert.show();
        }
    }
    
    
    @FXML private void eliminarProducto()
    {
        
        
       // dao.eliminarProducto(null);
    }
    
    @FXML private void openSerialConfig()
    {
            try {
                LOG.info("ABRIENDO LA VENTANA DE CONFIGURACION");
                AnchorPane pane;
                LOG.info("cargando vista Administrador");
                pane= FXMLLoader.load(getClass().getResource("/fxml/SerialFx.fxml"));
                rootPane.getChildren().setAll(pane);
            } catch (IOException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    
    @FXML private void agregarBaja(ActionEvent event){
        Bajas baja = new Bajas();
            
        try{
            baja.setCantidad(Double.parseDouble(txtCantidadBaja.getText()));
            baja.setFecha(new Date());
            baja.setPrecioVenta(Double.parseDouble(txtPrecioBaja.getText()));
            baja.setUnidad((UnidadMedida) boxUnidadBaja.getValue());
            
            baja.setProducto((Producto) boxProductoBaja.getValue());
            
            daoB.agregarBajas(baja);
            obtenerBajas();
        }catch(NumberFormatException ex){
            System.out.println(ex.getMessage()+" No es un numero");
        }catch (ConnectException ex) {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDBCConnectionException ex) {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CommunicationsException ex) {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExceptionInInitializerError ex) {
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML private void EliminarBaja(ActionEvent event){
        Bajas baja = tablaBajas.getSelectionModel().getSelectedItem();
        
        if(baja != null){
            try {
                daoB.eliminarBajas(baja);
                obtenerBajas();
            } catch (ConnectException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JDBCConnectionException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CommunicationsException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExceptionInInitializerError ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Debe de seleccionar un campo");
            alert.show();
        }
    }
    
    @FXML private void EditarBaja(ActionEvent event){
        Bajas baja = tablaBajas.getSelectionModel().getSelectedItem();
        if(baja !=null ){ 
            try{

                if(!txtCantidadBaja.getText().equals("")){
                    baja.setCantidad(Double.parseDouble(txtCantidadBaja.getText()));
                }
                
                if(!txtPrecioBaja.getText().equals("")){
                    baja.setPrecioVenta(Double.parseDouble(txtPrecioBaja.getText()));
                }
                
                baja.setUnidad((UnidadMedida) boxUnidadBaja.getValue());

                baja.setProducto((Producto) boxProductoBaja.getValue());

                daoB.updateBajas(baja);
                obtenerBajas();
            }catch(NumberFormatException ex){
                System.out.println(ex.getMessage()+" No es un numero");
            }catch (ConnectException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JDBCConnectionException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CommunicationsException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExceptionInInitializerError ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Debe de seleccionar un campo");
            alert.show();
        }
    }
}
