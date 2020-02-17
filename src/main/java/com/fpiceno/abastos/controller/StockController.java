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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import jssc.SerialPortException;
import org.hibernate.exception.JDBCConnectionException;

/**
 * FXML Controller class
 *
 * @author gnr_a
 */
public class StockController implements Initializable {
    
    private Logger LOG=Logger.getLogger(this.getClass().getSimpleName());
    
    private static DataOutputStream os = null;
	private static DataInputStream is = null;
        private static String responseLine;
        private static StringTokenizer tokens=null;
    
    @FXML private BorderPane borderPane;
    

    @FXML Label lblProducto;
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
    private Double CantidadTemporal;
    private Producto productoSeleccionado;
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
        
        Double valorCapturado = Double.parseDouble(txtCantidad.getText());
        Double diferencia = valorCapturado - this.CantidadTemporal;
        try { 
            if(getIsAlta()){
                Altas alta= new Altas();
                alta.setCantidad(valorCapturado);
                alta.setFecha(new Date());
                alta.setPrecioVenta(Double.parseDouble(txtPrecio.getText()));
                alta.setUnidad(boxUnidad.getValue());
                alta.setId(getIdentificador());
                
                daoA.updateAltas(alta);
                
                this.productoSeleccionado.setStock(this.productoSeleccionado.getStock()+diferencia.intValue());
                
            }else{
                Bajas baja = new Bajas();
                baja.setCantidad(valorCapturado);
                baja.setFecha(new Date());
                baja.setPrecioVenta(Double.parseDouble(txtPrecio.getText()));
                baja.setUnidad(boxUnidad.getValue());
                baja.setId(getIdentificador());
                
                daoB.updateBajas(baja);
                
                this.productoSeleccionado.setStock(this.productoSeleccionado.getStock()-diferencia.intValue());
            }
            
            daoP.updateProducto(this.productoSeleccionado);
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
    
    
    @FXML private void obtenerPeso(ActionEvent event){
        jssc.SerialPort serialPort = new jssc.SerialPort("COM3");
    try {
        serialPort.openPort();//Open serial port
        serialPort.setParams(jssc.SerialPort.BAUDRATE_9600, 
                             jssc.SerialPort.DATABITS_8,
                             jssc.SerialPort.STOPBITS_1,
                             jssc.SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
        serialPort.writeBytes("P".getBytes());//Write data to port
       
        
        serialPort.setParams(9600, 8, 1, 0);//Set params.
        byte[] buffer = serialPort.readBytes(10);//Read 10 bytes from serial port
        String s = new String(buffer);
        
        System.out.println("tengo esto en linea "+s);
        txtCantidad.setText(s);
        
        serialPort.closePort();//Close serial port
    }
    catch (SerialPortException ex) {
        
        System.out.println(ex);
    }
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
        lblProducto.setText(producto.getNombre());
        this.productoSeleccionado = producto;
        txtCantidad.setText(cantidad.toString());
        boxUnidad.setValue(unidad);
        txtPrecio.setText(precio.toString());
        
        this.CantidadTemporal = cantidad;
    }

    
    public Integer getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }
}
