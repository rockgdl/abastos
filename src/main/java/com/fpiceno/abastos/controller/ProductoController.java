package com.fpiceno.abastos.controller;

import com.fpiceno.abastos.*;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import org.hibernate.exception.JDBCConnectionException;

public class ProductoController implements Initializable {
    
    
    
    private Logger LOG=Logger.getLogger(this.getClass().getSimpleName());
    @FXML
    private Label label;
    @FXML
    private TextField conceptoField;
    @FXML
    private TextField costoUnitField;
    @FXML
    private TextArea descripcionField;
    @FXML private FlowPane rootPane;
    private ProductoDao dao=new ProductoDaoMysql();
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void guardaProducto() throws IOException
    {
        try {
            Producto producto= new Producto();
            producto.setCostoTotal(Double.parseDouble(costoUnitField.getText()));
            producto.setCostoUnitario(Double.parseDouble(costoUnitField.getText()));
            producto.setDescripcion(descripcionField.getText());
            producto.setFechaAlta(new Date());
            producto.setNombre(conceptoField.getText());
            producto.setUnidad(UnidadMedida.KG);
            dao.agregarProducto(producto);
            
            
            LOG.info(conceptoField.getText());
            
                    BorderPane pane;
        LOG.info("cargando vista Administrador");
        pane= FXMLLoader.load(getClass().getResource("/fxml/Principal.fxml"));
        rootPane.getChildren().setAll(pane);
        } catch (ConnectException ex) {
            Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDBCConnectionException ex) {
            Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CommunicationsException ex) {
            Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExceptionInInitializerError ex) {
            Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
