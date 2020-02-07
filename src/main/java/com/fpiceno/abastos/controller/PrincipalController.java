/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.controller;

import com.fazecast.jSerialComm.SerialPort;
import com.fpiceno.abastos.dao.mysql.ProductoDaoMysql;
import com.fpiceno.abastos.dto.UnidadMedida;
import com.fpiceno.abastos.entity.Producto;
import com.fpicneo.abastos.dao.ProductoDao;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.io.IOException;
import java.io.InputStream;
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
    
    private Producto producto;
    
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
            try {
                if(getProducto() != null){
                    getProducto().setCostoTotal(Double.parseDouble(pesoField.getText()));
                    getProducto().setCostoUnitario(Double.parseDouble(costoUnitField.getText()));
                    getProducto().setDescripcion(descripcionField.getText());
                    getProducto().setNombre(conceptoField.getText());
                    getProducto().setUnidad((UnidadMedida)comboUnidadMedida.getValue());
                    dao.updateProducto(producto);
                }else{
                    Producto newProducto= new Producto();
                    newProducto.setCostoTotal(Double.parseDouble(pesoField.getText()));
                    newProducto.setCostoUnitario(Double.parseDouble(costoUnitField.getText()));
                    newProducto.setDescripcion(descripcionField.getText());
                    newProducto.setFechaAlta(new Date());
                    newProducto.setNombre(conceptoField.getText());
                    newProducto.setUnidad((UnidadMedida)comboUnidadMedida.getValue());
                    dao.agregarProducto(newProducto);
                }
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
            }catch (NullPointerException ex){
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }catch(NumberFormatException ex){
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
    
    public void cargarDatos(Producto producto){
        conceptoField.setText(producto.getNombre());
        costoUnitField.setText(producto.getCostoUnitario().toString());
        pesoField.setText(producto.getCostoTotal().toString());
        descripcionField.setText(producto.getDescripcion());
        comboUnidadMedida.setValue(producto.getUnidad());
        
        this.producto = producto;
    }

    
    @FXML private void obtenerPeso()
    {
        LOG.info("INTENTANDO COMUNICAR AL PUERTO SERIAL SELECCIONADO");
        
            SerialPort comPort = SerialPort.getCommPorts()[0];
            comPort.openPort();
            comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
            InputStream in = comPort.getInputStream();
            try
            {
               for (int j = 0; j < 1000; ++j)
                  System.out.print((char)in.read());
               in.close();
            } catch (Exception e) { e.printStackTrace(); }
            comPort.closePort();
    }
    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }
    
}
