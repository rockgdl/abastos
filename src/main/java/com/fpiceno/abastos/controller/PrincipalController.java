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
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

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
    private TextField costoUnitField;
    @FXML
    private TextArea descripcionField;
    
    private ProductoDao dao=new ProductoDaoMysql();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        borderPane.setBottom(null);
        borderPane.setLeft(null);
        borderPane.setRight(null);

    }   
    
     @FXML
    public void guardaProducto()
    {
        Producto producto= new Producto();
        producto.setCostoTotal(Double.parseDouble(costoUnitField.getText()));
        producto.setCostoUnitario(Double.parseDouble(costoUnitField.getText()));
        producto.setDescripcion(descripcionField.getText());
        producto.setFechaAlta(new Date());
        producto.setNombre(conceptoField.getText());
        producto.setUnidad(UnidadMedida.KG);
        dao.agregarProducto(producto);
        
        
        LOG.info(conceptoField.getText());
    }
    
}
