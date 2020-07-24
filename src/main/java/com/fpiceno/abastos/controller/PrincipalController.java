/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.controller;

import com.fazecast.jSerialComm.SerialPort;
import com.fpiceno.abastos.dao.mysql.HistoricoReporteDaoMysql;
import com.fpiceno.abastos.dao.mysql.ProductoDaoMysql;
import com.fpiceno.abastos.dto.UnidadMedida;
import com.fpiceno.abastos.entity.HistoricoReporte;
import com.fpiceno.abastos.entity.Producto;
import com.fpicneo.abastos.dao.HistoricoReporteDao;
import com.fpicneo.abastos.dao.ProductoDao;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.exception.JDBCConnectionException;
import test.InputStreamSerial;

/**
 * FXML Controller class
 *
 * @author fpiceno
 */
public class PrincipalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
        private final org.apache.log4j.Logger LOG= org.apache.log4j.Logger.getLogger(PrincipalController.class.getSimpleName());
        private static DataOutputStream os = null;
	private static DataInputStream is = null;
        private static String responseLine;
        private static StringTokenizer tokens=null;
    
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
    
    @FXML Button botonSave;
       

    
    private ProductoDao dao=new ProductoDaoMysql();
    private HistoricoReporteDao daoH = new HistoricoReporteDaoMysql();
    
    private Producto producto;
    
    private ProductosController contolladorHijo;
    
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
            if(conceptoField.getText().equals("") || comboUnidadMedida.getValue() == null){
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("Todos los campos deben de estar llenos");
                alerta.setContentText("La descripcion se puede omitir");
                alerta.show();
            }else{
                try {
                    if(getProducto() != null){

                        LOG.info("el boton es para inicializar un update al producto ");
                        //getProducto().setCostoTotal(Double.parseDouble(pesoField.getText()));
              //          getProducto().setCostoUnitario(Double.parseDouble(costoUnitField.getText()));
                        getProducto().setDescripcion(descripcionField.getText());
                        getProducto().setNombre(conceptoField.getText());
                        getProducto().setUnidad((UnidadMedida)comboUnidadMedida.getValue());
                        dao.updateProducto(producto);
                    }else{
                        LOG.info("SE INICIALIZA UN NUEVO PRODUCTO PONIENDO UN STOCK EN 0");
                        Producto newProducto= new Producto();
                        //newProducto.setCostoTotal(Double.parseDouble(pesoField.getText()));
                //        newProducto.setCostoUnitario(Double.parseDouble(costoUnitField.getText()));
                        newProducto.setDescripcion(descripcionField.getText());
                        newProducto.setFechaAlta(new Date());
                        newProducto.setNombre(conceptoField.getText());
                        newProducto.setUnidad((UnidadMedida)comboUnidadMedida.getValue());
                        newProducto.setStock(0.0);//es la primera vez
                        newProducto.setCostoTotal(0.0); //igual es su primer vez
                        dao.agregarProducto(newProducto);
                        
                        HistoricoReporte historico = new HistoricoReporte();
                        historico.setCantidad(0.0);
                        historico.setFechaFin(new Date());
                        historico.setFechaInicio(new Date());
                        historico.setSaldoFinal(0.0);
                        
                        //System.out.println(dao.findProducto(newProducto));
                        Producto pro = dao.findProducto(newProducto);
                        historico.setProducto(pro);
                        
                        daoH.insertHistorico(historico);
                    }
                } catch (ConnectException ex) {

                    LOG.info("Error de ConnectException: " + ex.getMessage());
                     Alert alerta = new Alert(Alert.AlertType.ERROR);

                    alerta.setHeaderText("ERROR DE ConnectException");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();

                    Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JDBCConnectionException ex) {

                    LOG.info("Error de JDBCConnectionException: " + ex.getMessage());
                         Alert alerta = new Alert(Alert.AlertType.ERROR);

                    alerta.setHeaderText("ERROR DE JDBCConnectionException");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                    Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (CommunicationsException ex) {

                    LOG.info("Error de CommunicationsException: " + ex.getMessage());
                         Alert alerta = new Alert(Alert.AlertType.ERROR);

                    alerta.setHeaderText("ERROR DE CommunicationsException");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                    Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {

                    LOG.info("InvocationTargetException: " + ex.getMessage());
                         Alert alerta = new Alert(Alert.AlertType.ERROR);

                    alerta.setHeaderText("ERROR DE InvocationTargetException");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                    Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExceptionInInitializerError ex) {

                    LOG.info("ExceptionInInitializerError" + ex.getMessage());
                         Alert alerta = new Alert(Alert.AlertType.ERROR);

                    alerta.setHeaderText("ERROR DE ExceptionInInitializerError");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                    Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                }catch (NullPointerException ex){

                    LOG.info("Error de NullPointerException: " + ex.getMessage());
                         Alert alerta = new Alert(Alert.AlertType.ERROR);

                    alerta.setHeaderText("ERROR DE NullPointerException");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                    Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                }catch(NumberFormatException ex){

                    LOG.info("Error de NumberFormatException: " + ex.getMessage());
                         Alert alerta = new Alert(Alert.AlertType.ERROR);

                    alerta.setHeaderText("ERROR DE NumberFormatException");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                    Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                }
          //  LOG.info(conceptoField.getText());

            // actualizar las tablas 
            getContolladorHijo().obtenerProductos();
            Stage stage = (Stage) botonSave.getScene().getWindow();
            // do what you have to do
            stage.close();
        }
    }
    
    @FXML void eliminarProducto()
    {
        LOG.info("entrando a eliminar los productos de la tabla seleccionada");
    }
    
    public void cargarDatos(Producto producto){
        
        LOG.info("cargando los datos para el producto creo uqe para el update ");
        conceptoField.setText(producto.getNombre());
        //costoUnitField.setText(producto.getCostoUnitario().toString());
        //pesoField.setText(producto.getCostoTotal().toString());
        descripcionField.setText(producto.getDescripcion());
        comboUnidadMedida.setValue(producto.getUnidad());
        
        this.producto = producto;
    }

    
   
    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @return the contolladorHijo
     */
    public ProductosController getContolladorHijo() {
        return contolladorHijo;
    }

    /**
     * @param contolladorHijo the contolladorHijo to set
     */
    public void setContolladorHijo(ProductosController contolladorHijo) {
        this.contolladorHijo = contolladorHijo;
    }
    
}
