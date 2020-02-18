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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
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
    
        private Logger LOG=Logger.getLogger(this.getClass().getSimpleName());
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
                }
            } catch (ConnectException ex) {
                 Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("ERROR DE ConnectException");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JDBCConnectionException ex) {
                     Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("ERROR DE JDBCConnectionException");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CommunicationsException ex) {
                     Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("ERROR DE CommunicationsException");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                     Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("ERROR DE InvocationTargetException");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExceptionInInitializerError ex) {
                     Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("ERROR DE ExceptionInInitializerError");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }catch (NullPointerException ex){
                     Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("ERROR DE NullPointerException");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }catch(NumberFormatException ex){
                     Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("ERROR DE NumberFormatException");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        FlowPane pane;
        LOG.info("REGRESANDO A LA VISTA DE TODOS LOS PRODUCTOS UNA VEZ GUARDADO EL PRODUCTO ");
        pane= FXMLLoader.load(getClass().getResource("/fxml/Productos.fxml"));
        borderPane.getChildren().setAll(pane);
        LOG.info(conceptoField.getText());
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

    
    @FXML private void obtenerPeso()
    {
        LOG.info("INTENTANDO COMUNICAR AL PUERTO SERIAL SELECCIONADO");
        
           System.out.println("\nUsing Library Version v" + SerialPort.getVersion());
		SerialPort[] ports = SerialPort.getCommPorts();
		System.out.println("\nAvailable Ports:\n");
		for (int i = 0; i < ports.length; ++i)
                {
                    System.out.println("   [" + i + "] " + ports[i].getSystemPortName() + ": " + ports[i].getDescriptivePortName() + " - " + ports[i].getPortDescription());
                }
		
		System.out.print("\nChoose your desired serial port or enter -1 to specify a port directly: ");
		int serialPortChoice = 0;
		try {
			Scanner inputScanner = new Scanner(System.in);
			serialPortChoice = inputScanner.nextInt();
			inputScanner.close();
		} catch (Exception e) {}

                    

                
                SerialPort comPort = ports[serialPortChoice];
//    SerialPort comPort = SerialPort.getCommPorts()[2];
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 1000, 0);
        
         os = new DataOutputStream(comPort.getOutputStream());
        is = new DataInputStream(comPort.getInputStream());
        
        
          if (os != null && is != null) 
        {
            
        try {
            
            System.out.println(" pude obtener los inputStrem  y los OutputStream "+is.readLine());
            try {
                //  os.write("\nP\r".getBytes());
                
                System.out.println("que tengo en el is "+is.available());
                System.out.println(is.readLine());
//               while ((responseLine = is.readLine()) != null)
//                {
//                     System.out.println(responseLine);
////                       tokens=new StringTokenizer(responseLine);
////                      while(tokens.hasMoreTokens()){
////                          
////                      }
////                      
////                        os.writeUTF("\nP\r");
//                }
                
                
                
                
                
                
            } catch (IOException ex) {
                Logger.getLogger(InputStreamSerial.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //   comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
//        InputStream in = comPort.getInputStream();
//        try
//        {
//           for (int j = 0; j < 1000; ++j)
//              System.out.print((char)in.read());
//           in.close();
//        } catch (Exception e) { e.printStackTrace(); }
//             comPort.closePort();
 catch (IOException ex) {
            Logger.getLogger(InputStreamSerial.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

    }
    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }
    
}
