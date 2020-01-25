package com.fpiceno.abastos.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fpiceno.abastos.dao.mysql.ProductoDaoMysql;
import com.fpiceno.abastos.entity.Producto;
import com.fpicneo.abastos.dao.ProductoDao;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import org.hibernate.exception.JDBCConnectionException;

/**
 * FXML Controller class
 *
 * @author piceno
 */
public class ProductosController implements Initializable {
    
        private final org.apache.log4j.Logger log= org.apache.log4j.Logger.getLogger(ProductosController.class.getSimpleName());


       @FXML TableView<Producto> tablaProductos;
       @FXML TableColumn <Producto, String> columnNombre, columnDescripcion, columnFecha;
       @FXML TableColumn <Producto, Double> columnCantidad, columnCostoUnit, columnCostoTotal;
       @FXML TableColumn <Producto, Integer> columnId;
       @FXML private FlowPane rootPane;

       private ProductoDao dao=new ProductoDaoMysql();
       private ObservableList <Producto> oblistProducto= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
                obtenerProductos();

    }    
    
     @FXML private void agregarProducto()throws IOException
     {
        BorderPane pane;
        log.info("cargando vista Administrador");
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
    
}
