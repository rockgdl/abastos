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
import com.fpiceno.abastos.dto.AutoCompleteBox;
import com.fpiceno.abastos.dto.UnidadMedida;
import com.fpiceno.abastos.entity.Altas;
import com.fpiceno.abastos.entity.Bajas;
import com.fpiceno.abastos.entity.Cliente;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import javafx.stage.Stage;
import org.hibernate.SessionException;
import org.hibernate.exception.ConstraintViolationException;
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
       @FXML TableColumn <Producto, Double> columnCostoUnit, columnCostoTotal;
       @FXML TableColumn <Producto, Integer> columnId, columnStock;
       @FXML private FlowPane rootPane;

       private ProductoDao dao=new ProductoDaoMysql();
       private ObservableList <Producto> oblistProducto= FXCollections.observableArrayList();

       
       @FXML TableView<Altas> tablaAltas;
       @FXML TableColumn <Altas, String> columnFechaAltas, columnProductoAltas, columnUnidadAltas;
       @FXML TableColumn <Altas, Double> columnPrecioAltas;
       @FXML TableColumn <Altas, Integer> columnIdAltas, columnCantidadAltas;
       private ObservableList <Altas> oblistAltas= FXCollections.observableArrayList();
       private AltasDao daoA=new AltasDaoMysql();
       
       @FXML DatePicker fechaInicioAlta, fechaFinAlta;
       @FXML ComboBox <Producto> boxProductoAlta;
       
       @FXML TableView<Bajas> tablaBajas;
       @FXML TableColumn <Bajas, String> columnFechaBajas, columnProductoBajas, columnUnidadBajas;
       @FXML TableColumn <Bajas, Double> columnPrecioBajas;
       @FXML TableColumn <Bajas, Integer> columnIdBajas, columnCantidadBajas;
       private ObservableList <Bajas> oblistBajas= FXCollections.observableArrayList();
       private BajasDao daoB=new BajasDaoMysql();
       private ClienteDao daoC = new ClienteDaoMysql();
       
       @FXML DatePicker fechaInicioBaja, fechaFinBaja;
       @FXML ComboBox <Producto> boxProductoBaja;
//       @FXML TextField txtPrecioBaja, txtCantidadBaja;
//       @FXML ComboBox boxProductoBaja, boxUnidadBaja;
//       @FXML ComboBox<Cliente> boxCliente;
       
       
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            try {
                
                LOG.info("CARGANDO TODAS LAS ALTAS DEL MES ");
                obtenerProductos();
                obtenerAltas();
                obtenerBajas();
                
                boxProductoAlta.getItems().setAll(dao.obtenerTodos());
                boxProductoBaja.getItems().setAll(dao.obtenerTodos());
                //boxOpcion.getItems().set(1, columnId);
                
                
                //boxProductoBaja.getItems().setAll(dao.obtenerTodos());
                //boxUnidadBaja.getItems().addAll(UnidadMedida.values());
                //boxCliente.getItems().addAll(daoC.read());
                
                //inicializamos para el autocompletado de los combobox
                //new AutoCompleteBox(boxCliente);
                
            } catch (ConnectException ex) {
                LOG.error("excepcion de tipo ConnectException"+ ex);
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
            columnStock.setCellValueFactory(new PropertyValueFactory("stock"));
            columnFecha.setCellValueFactory(new PropertyValueFactory("fechaAlta"));
//            columnCostoUnit.setCellValueFactory(new PropertyValueFactory("costoUnitario"));
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
                       Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("ERROR DE InvocationTargetException ");
                alerta.setContentText(ex.getMessage());
                alerta.show();
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
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("error de InvocationTargetException");
                alerta.setContentText(ex.getMessage());
                alerta.show();
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
                     Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("error de ConnectException");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                  System.out.println("trono al eliminar la excepcion connecException");
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JDBCConnectionException ex) {
                     Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("error de JDBCConnectionException");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                  System.out.println("trono al eliminar la excepcion JDBCConnectionException ");
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CommunicationsException ex) {
                  System.out.println("trono al eliminar la excepcion CommunicationsException" );
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("error de InvocationTargetException");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                  System.out.println("trono al eliminar la excepcion InvocationTargetException");
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExceptionInInitializerError ex) {
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("error de ExceptionInInitializerError");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                  System.out.println("trono al eliminar la excepcion ExceptionInInitializerError");
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex){
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("error de NullPointerException");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                  System.out.println("trono al eliminar la excepcion NullPointerException");
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ConstraintViolationException ex) {
                         Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("error de ConstraintViolationException");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                System.out.println("trono al eliminar la excepcion ConstraintViolationException");
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
    
    @FXML private void seleccionarAlta(MouseEvent event) throws IOException{
        Altas alta = (Altas) tablaAltas.getSelectionModel().getSelectedItem();
        
        if (event.getClickCount() == 2 && alta != null){
            
            BorderPane pane;
            LOG.info("cargando vista editar stock alta");


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Stock.fxml"));

            pane = loader.load();

            StockController controller = loader.getController();
            controller.habilitarCampos(true, true);
            controller.cargarDatos(alta.getProducto(), alta.getCantidad(), alta.getUnidad(), alta.getPrecioVenta());
            controller.setIdentificador(alta.getId());
            rootPane.getChildren().setAll(pane);
            
//            boxProductoAlta.setValue(alta.getProducto());
//            boxUnidadAlta.setValue(alta.getUnidad());
//            txtCantidadAlta.setText(alta.getCantidad().toString());
//            txtPrecioAlta.setText(alta.getPrecioVenta().toString());
        }
    }
    
    @FXML private void agregarAlta(ActionEvent event) throws IOException{
        BorderPane pane;
        LOG.info("cargando vista stock baja");
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Stock.fxml"));
        
        pane = loader.load();
        
        StockController controller = loader.getController();
        controller.habilitarCampos(false, true);
        
        
        
        rootPane.getChildren().setAll(pane);
//        Altas alta = new Altas();    
//        try{
//            alta.setCantidad(Double.parseDouble(txtCantidadAlta.getText()));
//            alta.setFecha(new Date());
//            alta.setPrecioVenta(Double.parseDouble(txtPrecioAlta.getText()));
//            alta.setUnidad((UnidadMedida) boxUnidadAlta.getValue());
//            
//            alta.setProducto((Producto) boxProductoAlta.getValue());
//            
//            System.out.println(alta.getProducto().getFechaAlta());
//            
//            daoA.agregarAltas(alta);
//            System.out.println(alta.getProducto().getFechaAlta());
//            Producto producto = alta.getProducto();
//            
//            if(producto.getStock()== null){
//                producto.setStock( 0 + alta.getCantidad().intValue());
//            }else{
//                producto.setStock(producto.getStock()+alta.getCantidad().intValue());
//            }
//            
//            dao.updateProducto(producto);
//            
//            obtenerAltas();
//            obtenerProductos();
//        }catch(NumberFormatException ex){
//            System.out.println(ex.getMessage()+" No es un numero");
//        }catch (ConnectException ex) {
//            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JDBCConnectionException ex) {
//            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (CommunicationsException ex) {
//            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InvocationTargetException ex) {
//            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ExceptionInInitializerError ex) {
//            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
   
    @FXML private void openClientFx(ActionEvent event){
        LOG.info("CARGANDO la vista de clientes ");
        
              try {
                LOG.info("ABRIENDO LA VENTANA DE CONFIGURACION");
                BorderPane pane;
                LOG.info("cargando vista Administrador");
                pane= FXMLLoader.load(getClass().getResource("/fxml/Cliente.fxml"));
               // rootPane.getChildren().setAll(pane);
                Scene scene = new Scene(pane);
                 Stage stage = null;
                     stage= new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @FXML private void EliminarAlta(ActionEvent event){
        Altas alta = (Altas) tablaAltas.getSelectionModel().getSelectedItem();
        
        if(alta != null){
            try {
                daoA.eliminarAltas(alta);
                
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
            } catch (SessionException ex){
                System.out.println("La sesion esta cerrada");
            }
            
            obtenerAltas();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Debe de seleccionar un campo");
            alert.show();
        }
    }
    
    @FXML private void EditarAlta(ActionEvent event) throws IOException{
             
//        Altas alta = (Altas) tablaAltas.getSelectionModel().getSelectedItem();
//        if(alta !=null ){ 
//            try{
//
//                if(!txtCantidadAlta.getText().equals("")){
//                    alta.setCantidad(Double.parseDouble(txtCantidadAlta.getText()));
//                }
//                
//                if(!txtPrecioAlta.getText().equals("")){
//                    alta.setPrecioVenta(Double.parseDouble(txtPrecioAlta.getText()));
//                }
//                
//                alta.setUnidad((UnidadMedida) boxUnidadAlta.getValue());
//
//                alta.setProducto((Producto) boxProductoAlta.getValue());
//
//                daoA.updateAltas(alta);
//                obtenerAltas();
//            }catch(NumberFormatException ex){
//                System.out.println(ex.getMessage()+" No es un numero");
//            }catch (ConnectException ex) {
//                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (JDBCConnectionException ex) {
//                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (CommunicationsException ex) {
//                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (InvocationTargetException ex) {
//                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ExceptionInInitializerError ex) {
//                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }else{
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText("Debe de seleccionar un campo");
//            alert.show();
//        }
    }
    
    @FXML private void buscarAlta(ActionEvent event){
            try {
                tablaAltas.getItems().clear();
                Date fechaInicio = null, fechaFin = null;
                
                if(fechaFinAlta.getValue() == null){
                    fechaFin = new Date();
                }else{
                    fechaFin = Date.from(fechaFinAlta.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                    fechaFin.setHours(23);
                    fechaFin.setMinutes(59);
                    fechaFin.setSeconds(59);
                }
                
                if(boxProductoAlta.getValue() != null && fechaInicioAlta.getValue() != null){
                    fechaInicio = Date.from(fechaInicioAlta.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                    oblistAltas.setAll(daoA.findAltaWhithProductoAndFecha(boxProductoAlta.getValue(), fechaInicio, fechaFin));
                    
                    
                }else if (boxProductoAlta.getValue() != null){
                    oblistAltas.setAll(daoA.findAltaWhithProducto(boxProductoAlta.getValue()));
                    
                }else if (fechaInicioAlta.getValue() != null){
                    fechaInicio = Date.from(fechaInicioAlta.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                    oblistAltas.setAll(daoA.findAltaWhithFecha(fechaInicio, fechaFin));
                }
                
                tablaAltas.setItems(oblistAltas);
                
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
    
    
    @FXML private void seleccionarBaja(MouseEvent event) throws IOException{
        Bajas baja = tablaBajas.getSelectionModel().getSelectedItem();
        
        if (event.getClickCount() == 2 && baja != null){
            
            BorderPane pane;
            LOG.info("cargando vista editar stock baja");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Stock.fxml"));

            pane = loader.load();

            StockController controller = loader.getController();
            controller.habilitarCampos(true, false);
            controller.cargarDatos(baja.getProducto(), baja.getCantidad(), baja.getUnidad(), baja.getPrecioVenta());
            controller.setIdentificador(baja.getId());
            rootPane.getChildren().setAll(pane);
           
        }
    }
    
    @FXML private void agregarBaja(ActionEvent event) throws IOException{
        BorderPane pane;
        LOG.info("cargando vista stock baja");
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Stock.fxml"));
        
        pane = loader.load();
        
        StockController controller = loader.getController();
        controller.habilitarCampos(false, false);
        
        rootPane.getChildren().setAll(pane);
        
        
//        Bajas baja = new Bajas();
//            
//        try{
//            baja.setCantidad(Double.parseDouble(txtCantidadBaja.getText()));
//            baja.setFecha(new Date());
//            baja.setPrecioVenta(Double.parseDouble(txtPrecioBaja.getText()));
//            baja.setUnidad((UnidadMedida) boxUnidadBaja.getValue());
//            
//            baja.setProducto((Producto) boxProductoBaja.getValue());
//            
//            daoB.agregarBajas(baja);
//            System.out.println(boxCliente.getSelectionModel().getSelectedItem().getCorreo());
//            obtenerBajas();
//        }catch(NumberFormatException ex){
//            System.out.println(ex.getMessage()+" No es un numero");
//        }
//        }catch (ConnectException ex) {
//            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JDBCConnectionException ex) {
//            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (CommunicationsException ex) {
//            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InvocationTargetException ex) {
//            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ExceptionInInitializerError ex) {
//            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
//        Bajas baja = tablaBajas.getSelectionModel().getSelectedItem();
//        if(baja !=null ){ 
//            try{
//
//                if(!txtCantidadBaja.getText().equals("")){
//                    baja.setCantidad(Double.parseDouble(txtCantidadBaja.getText()));
//                }
//                
//                if(!txtPrecioBaja.getText().equals("")){
//                    baja.setPrecioVenta(Double.parseDouble(txtPrecioBaja.getText()));
//                }
//                
//                baja.setUnidad((UnidadMedida) boxUnidadBaja.getValue());
//
//                baja.setProducto((Producto) boxProductoBaja.getValue());
//
//                daoB.updateBajas(baja);
//                obtenerBajas();
//            }catch(NumberFormatException ex){
//                System.out.println(ex.getMessage()+" No es un numero");
//            }catch (ConnectException ex) {
//                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (JDBCConnectionException ex) {
//                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (CommunicationsException ex) {
//                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (InvocationTargetException ex) {
//                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ExceptionInInitializerError ex) {
//                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }else{
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText("Debe de seleccionar un campo");
//            alert.show();
//        }
    }
   
   @FXML private void buscarBaja(ActionEvent event){
            try {
                tablaBajas.getItems().clear();
                Date fechaInicio = null, fechaFin = null;
                
                if(fechaFinBaja.getValue() == null){
                    fechaFin = new Date();
                }else{
                    fechaFin = Date.from(fechaFinBaja.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                    fechaFin.setHours(23);
                    fechaFin.setMinutes(59);
                    fechaFin.setSeconds(59);
                }
                
                if(boxProductoBaja.getValue() != null && fechaInicioBaja.getValue() != null){
                    fechaInicio = Date.from(fechaInicioBaja.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                    oblistBajas.setAll(daoB.findBajaWhithProductoAndFecha(boxProductoBaja.getValue(), fechaInicio, fechaFin));
                    
                    
                }else if (boxProductoBaja.getValue() != null){
                    oblistBajas.setAll(daoB.findBajaWhithProducto(boxProductoBaja.getValue()));
                    
                }else if (fechaInicioBaja.getValue() != null){
                    fechaInicio = Date.from(fechaInicioBaja.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                    oblistBajas.setAll(daoB.findBajaWhithFecha(fechaInicio, fechaFin));
                }
                
                tablaBajas.setItems(oblistBajas);
                
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
    }
    
    @FXML private void agregarCliente(ActionEvent event) throws IOException{
        BorderPane pane;
        LOG.info("cargando vista Cliente");
        pane= FXMLLoader.load(getClass().getResource("/fxml/Cliente.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
