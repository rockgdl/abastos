package com.fpiceno.abastos.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.fpiceno.abastos.dao.mysql.AltasDaoMysql;
import com.fpiceno.abastos.dao.mysql.BajasDaoMysql;
import com.fpiceno.abastos.dao.mysql.ClienteDaoMysql;
import com.fpiceno.abastos.dao.mysql.ProductoDaoMysql;
import com.fpiceno.abastos.dto.UnidadMedida;
import com.fpiceno.abastos.entity.Altas;
import com.fpiceno.abastos.entity.Bajas;
import com.fpiceno.abastos.entity.Producto;
import com.fpicneo.abastos.dao.AltasDao;
import com.fpicneo.abastos.dao.BajasDao;
import com.fpicneo.abastos.dao.ClienteDao;
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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import org.hibernate.exception.JDBCConnectionException;

/**
 * FXML Controller class
 *
 * @author gnr_a
 */
public class StockController implements Initializable {
    
    private Logger LOG=Logger.getLogger(this.getClass().getSimpleName());
    
    @FXML private BorderPane borderPane;
    

    @FXML ComboBox<Producto> boxProducto;
    @FXML ComboBox<UnidadMedida> boxUnidad;
    @FXML TextField txtCantidad, txtPrecio;
    @FXML ComboBox boxCliente;
    
    @FXML Button btnAgregar, btnEditar;
    
    ClienteDao daoC = new ClienteDaoMysql();
    ProductoDao daoP = new ProductoDaoMysql();
    AltasDao daoA = new AltasDaoMysql();
    BajasDao daoB = new BajasDaoMysql();
    
    private Boolean isAlta;
    private Boolean isEditable;
    
    private Integer identificador;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        borderPane.setBottom(null);
        borderPane.setLeft(null);
        borderPane.setRight(null);
        
        try {
            boxUnidad.getItems().addAll(UnidadMedida.values());
            boxProducto.getItems().addAll(daoP.obtenerTodos());
            boxCliente.getItems().setAll(daoC.read());
            
        } catch (ConnectException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDBCConnectionException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CommunicationsException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExceptionInInitializerError ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch(NumberFormatException ex){
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML private void agregar(ActionEvent event) throws IOException{
        try { 
            if(getIsAlta()){
                Altas alta= new Altas();
                alta.setCantidad(Double.parseDouble(txtCantidad.getText()));
                alta.setFecha(new Date());
                alta.setProducto(boxProducto.getValue());
                alta.setPrecioVenta(Double.parseDouble(txtPrecio.getText()));
                alta.setUnidad(boxUnidad.getValue());
                
                daoA.agregarAltas(alta);
                
                Producto producto = alta.getProducto();
                
                
                producto.setStock(producto.getStock() + alta.getCantidad().intValue());
                daoP.updateProducto(producto);
                
            }else{
                Bajas baja = new Bajas();
                baja.setCantidad(Double.parseDouble(txtCantidad.getText()));
                baja.setFecha(new Date());
                baja.setProducto(boxProducto.getValue());
                baja.setPrecioVenta(Double.parseDouble(txtPrecio.getText()));
                baja.setUnidad(boxUnidad.getValue());
                
                daoB.agregarBajas(baja);
                
                Producto producto = baja.getProducto();
                producto.setStock(producto.getStock() - baja.getCantidad().intValue());
                daoP.updateProducto(producto);
            }
        } catch (ConnectException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDBCConnectionException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CommunicationsException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExceptionInInitializerError ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch(NumberFormatException ex){
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FlowPane pane;
        LOG.info("REGRESANDO A LA VISTA DE TODOS LOS PRODUCTOS UNA VEZ GUARDADO EL PRODUCTO ");
        pane= FXMLLoader.load(getClass().getResource("/fxml/Productos.fxml"));
        borderPane.getChildren().setAll(pane);
        
    }
    
    @FXML private void editar(ActionEvent event) throws IOException{
        try { 
            if(getIsAlta()){
                Altas alta= new Altas();
                alta.setCantidad(Double.parseDouble(txtCantidad.getText()));
                alta.setFecha(new Date());
                alta.setProducto(boxProducto.getValue());
                alta.setPrecioVenta(Double.parseDouble(txtPrecio.getText()));
                alta.setUnidad(boxUnidad.getValue());
                alta.setId(getIdentificador());
                daoA.updateAltas(alta);
            }else{
                Bajas baja = new Bajas();
                baja.setCantidad(Double.parseDouble(txtCantidad.getText()));
                baja.setFecha(new Date());
                baja.setProducto(boxProducto.getValue());
                baja.setPrecioVenta(Double.parseDouble(txtPrecio.getText()));
                baja.setUnidad(boxUnidad.getValue());
                baja.setId(getIdentificador());
                
                daoB.updateBajas(baja);
            }
        } catch (ConnectException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDBCConnectionException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CommunicationsException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExceptionInInitializerError ex) {
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch(NumberFormatException ex){
            Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FlowPane pane;
        LOG.info("REGRESANDO A LA VISTA DE TODOS LOS PRODUCTOS UNA VEZ GUARDADO EL PRODUCTO ");
        pane= FXMLLoader.load(getClass().getResource("/fxml/Productos.fxml"));
        borderPane.getChildren().setAll(pane);
    }
   
    public Boolean getIsAlta() {
        return isAlta;
    }
    
    public Boolean getIsEditable() {
        return isEditable;
    }

    public void habilitarCampos(Boolean isEditable, Boolean isAlta){
        this.isAlta = isAlta;
        this.isEditable = isEditable;
        
        if(isEditable){
            btnAgregar.setDisable(true);
        }else{
            btnEditar.setDisable(true);
        }
        
        if(isAlta){
            boxCliente.setVisible(false);
        }else{
            boxCliente.setVisible(true);
        }
    }
    
    public void cargarDatos(Producto producto, Double cantidad, UnidadMedida unidad, Double precio){
        boxProducto.setValue(producto);
        txtCantidad.setText(cantidad.toString());
        boxUnidad.setValue(unidad);
        txtPrecio.setText(precio.toString());
    }

    
    public Integer getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }
}
