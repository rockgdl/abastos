/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.controller;

import com.fpiceno.abastos.dao.mysql.ProductoDaoMysql;
import com.fpiceno.abastos.dto.UnidadMedida;
import com.fpiceno.abastos.entity.Producto;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import org.hibernate.exception.JDBCConnectionException;

/**
 * FXML Controller class
 *
 * @author fpiceno
 */
public class PrincipalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
        private Logger LOG=Logger.getLogger(this.getClass().getSimpleName());

    
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField conceptoField;
    @FXML
    private TextField costoUnitField,pesoField;
    @FXML
    private TextArea descripcionField;
    @FXML   
    private ComboBox comboUnidadMedida;
       

    
    private ProductoDao dao=new ProductoDaoMysql();
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        borderPane.setBottom(null);
        borderPane.setLeft(null);
        borderPane.setRight(null);
        comboUnidadMedida.setItems(FXCollections.observableArrayList(UnidadMedida.values()));

        

    }   
    
     @FXML
    public void guardaProducto() throws IOException
    {
        Producto producto= new Producto();
        producto.setCostoTotal(Double.parseDouble(pesoField.getText()));
        producto.setCostoUnitario(Double.parseDouble(costoUnitField.getText()));
        producto.setDescripcion(descripcionField.getText());
        producto.setFechaAlta(new Date());
        producto.setNombre(conceptoField.getText());
        producto.setUnidad((UnidadMedida)comboUnidadMedida.getValue());
            try {
                dao.agregarProducto(producto);
            } catch (ConnectException ex) {
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JDBCConnectionException ex) {
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CommunicationsException ex) {
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExceptionInInitializerError ex) {
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        FlowPane pane;
        LOG.info("cargando vista Administrador");
        pane= FXMLLoader.load(getClass().getResource("/fxml/Productos.fxml"));
        borderPane.getChildren().setAll(pane);
        LOG.info(conceptoField.getText());
    }
    
    @FXML void eliminarProducto()
    {
        LOG.info("entrando a eliminar los productos de la tabla seleccionada");
    }
    
}
