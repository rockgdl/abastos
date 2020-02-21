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
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import jssc.SerialPortException;
import org.hibernate.exception.ConstraintViolationException;
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
     
        private static StringTokenizer tokens=null;
    
    @FXML private BorderPane borderPane;
    

    @FXML Label lblProducto;
    @FXML ComboBox <Producto> boxProducto;
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
    private Double precioTotalAlta;
    
    
    private ProductosController controladorHijo;
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
                LOG.info("Error de ConnectException:" + ex.getMessage());
                     Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("error de ConnectException");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                  System.out.println("trono al eliminar la excepcion connecException");
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JDBCConnectionException ex) {
                
                LOG.info("Error de JDBCConnectionException:" + ex.getMessage());
                     Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("error de JDBCConnectionException");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                  System.out.println("trono al eliminar la excepcion JDBCConnectionException ");
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CommunicationsException ex) {
                
                LOG.info("Error de CommunactionsExceptions:" + ex.getMessage());
                  System.out.println("trono al eliminar la excepcion CommunicationsException" );
                  
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("Error de CommunicationsException");
                alerta.setContentText(ex.getMessage());
                alerta.setContentText(ex.getMessage());
                alerta.show();
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                
                LOG.info("Error de InvocationTargetException:" + ex.getMessage());
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("error de InvocationTargetException");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                  System.out.println("trono al eliminar la excepcion InvocationTargetException");
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExceptionInInitializerError ex) {
                
                LOG.info("Error de ContraintViolationException: " + ex.getMessage());
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("error de ExceptionInInitializerError");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                System.out.println("trono al eliminar la excepcion ExceptionInInitializerError");
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex){
                LOG.info("Error de NullPointerException:" + ex.getMessage());
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("error de NullPointerException");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                  System.out.println("trono al eliminar la excepcion NullPointerException");
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ConstraintViolationException ex) {
                
                LOG.info("Error de ContraintViolationException:" + ex.getMessage());
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("error de ConstraintViolationException");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                System.out.println("trono al eliminar la excepcion ConstraintViolationException");
                Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                
            } catch(NumberFormatException ex){
                LOG.info("Error de NumberFormatException: " + ex.getMessage());
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("Error de NumberFormatException");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML private void agregar(ActionEvent event) throws IOException{
        if(txtCantidad.getText().equals("") || txtPrecio.getText().equals("") || boxProducto.getValue() == null || boxUnidad.getValue() == null){
                LOG.info("Hay un campo en la vista de stock que no se lleno");
                Alert alerta = new Alert(AlertType.WARNING);
                alerta.setHeaderText("Todos los campos deben de estar llenos");
                alerta.show();
        }else{
            

            try { 
                //Si se trata de una alta
                if(getIsAlta()){
                    Altas alta= new Altas();
                    alta.setCantidad(Double.parseDouble(txtCantidad.getText()));
                    alta.setRestante(Double.parseDouble(txtCantidad.getText()));
                    alta.setFecha(new Date());
                    alta.setProducto(boxProducto.getValue());
                    alta.setPrecioVenta(Double.parseDouble(txtPrecio.getText()));
                    alta.setUnidad(boxUnidad.getValue());
                    alta.setPrecioTotal(alta.getPrecioVenta() * alta.getCantidad());

                    daoA.agregarAltas(alta);

                    Producto producto = alta.getProducto();


                    producto.setStock(producto.getStock() + alta.getCantidad());
                    producto.setCostoTotal(producto.getCostoTotal() + alta.getPrecioTotal());
                    daoP.updateProducto(producto);

                }else{//si se trata de una baja

                    Double cantidad = Double.parseDouble(txtCantidad.getText());
                    Bajas baja = new Bajas();
                    baja.setCantidad(Double.parseDouble(txtCantidad.getText()));
                    baja.setFecha(new Date());
                    baja.setProducto(boxProducto.getValue());
                    baja.setPrecioVenta(Double.parseDouble(txtPrecio.getText()));
                    baja.setUnidad(boxUnidad.getValue());
    //                
    //                
                    if (boxProducto.getValue().getStock() >= cantidad){
                        Double cantidadTemp = cantidad;
                        int i = 0;
                        List <Altas> listaRegistros = daoA.findAltaWhithRestante();

                        while (cantidadTemp > 0.0){
                            Bajas newBaja = new Bajas();

                            if(listaRegistros.get(i).getRestante() >= cantidadTemp){

                                listaRegistros.get(i).setRestante(listaRegistros.get(i).getRestante()- cantidadTemp);
                                newBaja.setCantidad(cantidadTemp);

                                cantidadTemp = 0.0;

                            }else{
                                newBaja.setCantidad(listaRegistros.get(i).getCantidad());
                                cantidadTemp = cantidadTemp - listaRegistros.get(i).getRestante();

                                listaRegistros.get(i).setRestante(0.0);

                            }

                            daoA.updateAltas(listaRegistros.get(i));
                            newBaja.setFecha(new Date());
                            newBaja.setPrecioVenta(listaRegistros.get(i).getPrecioVenta());
                            newBaja.setProducto(boxProducto.getValue());
                            newBaja.setUnidad(boxUnidad.getValue());
                            System.out.println("Nueva baja - " + newBaja.getCantidad() + " - " + newBaja.getPrecioVenta());

                            daoB.agregarBajas(newBaja);
                            i++;

                        }
                        //daoB.agregarBajas(baja);

                        Producto producto = baja.getProducto();
                        producto.setStock(producto.getStock() - baja.getCantidad());
                        daoP.updateProducto(producto);
                    }else{
                        //Mandar alerta
                        Alert alerta = new Alert(AlertType.ERROR);
                        alerta.setHeaderText("No tiene se tiene en existencia tal cantidad");
                        alerta.show();
                    }


                }
            } catch (ConnectException ex) {
                    LOG.info("Error de ConnectException:" + ex.getMessage());
                         Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText("error de ConnectException");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                      System.out.println("trono al eliminar la excepcion connecException");
                    Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JDBCConnectionException ex) {

                    LOG.info("Error de JDBCConnectionException:" + ex.getMessage());
                         Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText("error de JDBCConnectionException");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                      System.out.println("trono al eliminar la excepcion JDBCConnectionException ");
                    Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (CommunicationsException ex) {

                    LOG.info("Error de CommunactionsExceptions:" + ex.getMessage());
                      System.out.println("trono al eliminar la excepcion CommunicationsException" );

                    Alert alerta = new Alert(Alert.AlertType.ERROR);

                    alerta.setHeaderText("Error de CommunicationsException");
                    alerta.setContentText(ex.getMessage());
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                    Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {

                    LOG.info("Error de InvocationTargetException:" + ex.getMessage());
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText("error de InvocationTargetException");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                      System.out.println("trono al eliminar la excepcion InvocationTargetException");
                    Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExceptionInInitializerError ex) {

                    LOG.info("Error de ContraintViolationException: " + ex.getMessage());
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText("error de ExceptionInInitializerError");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                    System.out.println("trono al eliminar la excepcion ExceptionInInitializerError");
                    Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NullPointerException ex){
                    LOG.info("Error de NullPointerException:" + ex.getMessage());

                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText("error de NullPointerException");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                      System.out.println("trono al eliminar la excepcion NullPointerException");
                    Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ConstraintViolationException ex) {

                    LOG.info("Error de ContraintViolationException:" + ex.getMessage());
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText("error de ConstraintViolationException");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                    System.out.println("trono al eliminar la excepcion ConstraintViolationException");
                    Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);

                    LOG.info("Error de SQLException: "+ ex.getMessage());
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText("error de SQLException");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                } catch(NumberFormatException ex){
                    Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
                    LOG.info("Error de NumberFormatExceotion: " + ex.getMessage());

                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText("Error de NumberFormatException");
                    alerta.setContentText(ex.getMessage());
            }

            getControladorHijo().obtenerAltas();
            getControladorHijo().obtenerBajas();
            getControladorHijo().obtenerProductos();
    //        FlowPane pane;
    //        LOG.info("REGRESANDO A LA VISTA DE TODOS LOS PRODUCTOS UNA VEZ GUARDADO EL PRODUCTO ");
    //        pane= FXMLLoader.load(getClass().getResource("/fxml/Productos.fxml"));
    //        borderPane.getChildren().setAll(pane);

            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            // do what you have to do
            stage.close();
        }
        
    }
    
    @FXML private void editar(ActionEvent event) throws IOException{
            if(txtCantidad.getText().equals("") || txtPrecio.getText().equals("") || boxProducto.getValue() == null || boxUnidad.getValue() == null){
                LOG.info("Hay un campo en la vista de stock que no se lleno");
                Alert alerta = new Alert(AlertType.WARNING);
                alerta.setHeaderText("Todos los campos deben de estar llenos");
                alerta.show();
            }else{
                try { 
                //Para el caso del stock simplemente sacamos la diferencia del nuevo dato con el anterior y lo modificamos en producto
                Double valorCapturado = Double.parseDouble(txtCantidad.getText());
                Double diferencia = valorCapturado - this.CantidadTemporal;
                if(getIsAlta()){
                    Altas alta= new Altas();

                    alta.setCantidad(valorCapturado);
                    alta.setRestante(valorCapturado);
                    alta.setFecha(new Date());
                    alta.setProducto(boxProducto.getValue());
                    alta.setPrecioVenta(Double.parseDouble(txtPrecio.getText()));
                    alta.setUnidad(boxUnidad.getValue());
                    alta.setPrecioTotal(alta.getCantidad() * alta.getPrecioVenta());

                    alta.setId(getIdentificador());

                    daoA.updateAltas(alta);

                    //Se le resta al producto el precio que tienia la alta y al final le cargamos el nuevo
                    Double costoProducto = this.productoSeleccionado.getCostoTotal() - this.precioTotalAlta;
                    this.productoSeleccionado.setCostoTotal(costoProducto + alta.getPrecioTotal());


                    this.productoSeleccionado.setStock(this.productoSeleccionado.getStock() + diferencia);


                }else{
                    Bajas baja = new Bajas();
                    baja.setCantidad(valorCapturado);
                    baja.setFecha(new Date());
                    baja.setProducto(boxProducto.getValue());
                    baja.setPrecioVenta(Double.parseDouble(txtPrecio.getText()));
                    baja.setUnidad(boxUnidad.getValue());
                    baja.setId(getIdentificador());

                    daoB.updateBajas(baja);

                    this.productoSeleccionado.setStock(this.productoSeleccionado.getStock()-diferencia);
                }

                daoP.updateProducto(this.productoSeleccionado);
            } catch (ConnectException ex) {
                    LOG.info("Error de ConnectException:" + ex.getMessage());
                         Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText("error de ConnectException");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                      System.out.println("trono al eliminar la excepcion connecException");
                    Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JDBCConnectionException ex) {

                    LOG.info("Error de JDBCConnectionException:" + ex.getMessage());
                         Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText("error de JDBCConnectionException");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                      System.out.println("trono al eliminar la excepcion JDBCConnectionException ");
                    Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (CommunicationsException ex) {

                    LOG.info("Error de CommunactionsExceptions:" + ex.getMessage());
                      System.out.println("trono al eliminar la excepcion CommunicationsException" );

                    Alert alerta = new Alert(Alert.AlertType.ERROR);

                    alerta.setHeaderText("Error de CommunicationsException");
                    alerta.setContentText(ex.getMessage());
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                    Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {

                    LOG.info("Error de InvocationTargetException:" + ex.getMessage());
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText("error de InvocationTargetException");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                      System.out.println("trono al eliminar la excepcion InvocationTargetException");
                    Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExceptionInInitializerError ex) {

                    LOG.info("Error de ContraintViolationException: " + ex.getMessage());
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText("error de ExceptionInInitializerError");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                    System.out.println("trono al eliminar la excepcion ExceptionInInitializerError");
                    Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NullPointerException ex){
                    LOG.info("Error de NullPointerException:" + ex.getMessage());

                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText("error de NullPointerException");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                      System.out.println("trono al eliminar la excepcion NullPointerException");
                    Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ConstraintViolationException ex) {

                    LOG.info("Error de ContraintViolationException:" + ex.getMessage());
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText("error de ConstraintViolationException");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                    System.out.println("trono al eliminar la excepcion ConstraintViolationException");
                    Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);

                    LOG.info("Error de SQLException: "+ ex.getMessage());
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText("error de SQLException");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                } catch(NumberFormatException ex){

                    LOG.info("Error de NumberFormatException: " + ex.getMessage());

                    Alert alerta = new Alert(AlertType.ERROR);
                    alerta.setHeaderText("Error de FormatNumberException");
                    alerta.setContentText(ex.getMessage());
                    alerta.show();
                    Logger.getLogger(StockController.class.getName()).log(Level.SEVERE, null, ex);
            }

            getControladorHijo().obtenerAltas();
            getControladorHijo().obtenerBajas();
            getControladorHijo().obtenerProductos();

            Stage stage = (Stage) btnAgregar.getScene().getWindow();
            stage.close();
        }
        
        
//        FlowPane pane;
//        LOG.info("REGRESANDO A LA VISTA DE TODOS LOS PRODUCTOS UNA VEZ GUARDADO EL PRODUCTO ");
//        pane= FXMLLoader.load(getClass().getResource("/fxml/Productos.fxml"));
//        borderPane.getChildren().setAll(pane);
    }
    
    
    @FXML private void obtenerPeso(ActionEvent event) throws SerialPortException{
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
        String responseLine = new String(buffer);
        
        LOG.info("tengo esto en linea"+responseLine);
     
        
                       
//                                       System.out.println( Thread.currentThread() );
                        tokens=new StringTokenizer(responseLine);
//		                	  int nDatos=tokens.countTokens();

                          while(tokens.hasMoreTokens()){
            String gramos = tokens.nextToken();
            LOG.info("TENGO UN TOKEN DE GRAMOS:"+gramos);
//		                		  String cadena = bf.readLine().replace("kg", "");

            txtCantidad.setText(gramos);
                        String peso=tokens.nextToken().replace("kg", "");


                         if(peso!=null && !peso.equalsIgnoreCase(""))
                         {
                             
                                txtCantidad.setText(responseLine);
                         }
                         else
                         {
                             LOG.info("El peso es 0 no subire el archivo");
                              txtCantidad.setText(gramos);
                         }

                          }
        
      
    }
    catch (SerialPortException ex) {
        LOG.info("Error de SerialPortException: " + ex.getMessage());
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.setHeaderText("Error de SerialPortException");
        alerta.setContentText(ex.getMessage());
        alerta.show();
        System.out.println(ex);
    }
        finally
        {
             serialPort.closePort();//Close serial port 
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
            boxProducto.setDisable(true);
        }else{
            btnEditar.setDisable(true);
        }
        
        if(isAlta){
            boxCliente.setVisible(false);
        }else{
            txtPrecio.setText("0");
            txtPrecio.setDisable(true);
            boxCliente.setVisible(true);
        }
    }
    
    public void cargarDatos(Producto producto, Double cantidad, UnidadMedida unidad, Double precio, Double precioTotal){
        lblProducto.setText(producto.getNombre());
        boxProducto.setValue(producto);
        this.productoSeleccionado = producto;
        txtCantidad.setText(cantidad.toString());
        boxUnidad.setValue(unidad);
        txtPrecio.setText(precio.toString());
        this.precioTotalAlta = precioTotal;
        
        this.CantidadTemporal = cantidad;
    }

    
    public Integer getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }

    /**
     * @return the controladorHijo
     */
    public ProductosController getControladorHijo() {
        return controladorHijo;
    }

    /**
     * @param controladorHijo the controladorHijo to set
     */
    public void setControladorHijo(ProductosController controladorHijo) {
        this.controladorHijo = controladorHijo;
    }
}
