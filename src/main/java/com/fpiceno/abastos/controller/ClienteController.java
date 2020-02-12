/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.controller;

import com.fpicneo.abastos.dao.ClienteDao;
import com.fpiceno.abastos.dao.mysql.ClienteDaoMysql;
import com.fpiceno.abastos.entity.Cliente;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;


import java.net.URL;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.hibernate.exception.JDBCConnectionException;

/**
 * FXML Controller class
 *
 * @author gnr_a
 */
public class ClienteController implements Initializable {

    @FXML Label lblNombre, lblCorreo, lblRFC, lblTelefono, lblDomicilio;
    @FXML Button btnGuardar, btnAgregar, btnEliminar;
    @FXML TextField txtNombre, txtTelefono, txtDomicilio, txtRFC, txtCorreo, txtBusqueda;
    @FXML TableView<Cliente> tablaClientes;
    @FXML TableColumn<Cliente, String> columnNombre, columnTelefono, columnDomicilio, columnRFC, columnCorreo;
    ObservableList <Cliente> oblist= FXCollections.observableArrayList();
    
    int idCliente = 0;
    
    //private AgregarCasoController agregarCasoController;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        obtenerClientes();
        btnGuardar.setDisable(true);
        btnEliminar.setDisable(true);
        
    }
    
    @FXML private void addCliente(ActionEvent event){
        if(verificar()){
            try {
                Cliente cliente = new Cliente();
                
                cliente.setId(idCliente);
                cliente.setCorreo(txtCorreo.getText());
                cliente.setDomicilio(txtDomicilio.getText());
                cliente.setNombre(txtNombre.getText());
                cliente.setRfc(txtRFC.getText());
                cliente.setTelefono(txtTelefono.getText());
                
                ClienteDao dao = new ClienteDaoMysql();
                dao.insert(cliente);
                obtenerClientes();
                limpiar();
                
//                if(getAgregarCasoController() != null){
//                    getAgregarCasoController().boxCliente.getItems().add(cliente);
//                    //getAgregarCasoController().boxClienteBusqueda.getItems().add(cliente);
//                }
            } catch (SQLIntegrityConstraintViolationException ex) {
                Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ConnectException ex) {
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("No se pudo conectar a mysql");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (JDBCConnectionException ex) {
                ////Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("Se encontro un error al quere insertar la información");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (CommunicationsException ex) {
                ////Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("No se pudo comunicar con la base de datos mysql");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (InvocationTargetException ex) {
                //Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExceptionInInitializerError ex) {
                //Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else{
            System.out.println("ERROR");
        }
    }
    
    @FXML private void searchCliente(KeyEvent event){
        if(txtBusqueda.getText().equals("")){
            obtenerClientes();
        }else{
            btnEliminar.setDisable(true);
            try {
                ClienteDao dao = new ClienteDaoMysql();
                
                tablaClientes.getItems().clear();
                oblist.addAll(dao.readLike(txtBusqueda.getText()));
                
                if(oblist.size() == 0){
                    oblist.clear();
                    oblist.addAll(dao.readRFC(txtBusqueda.getText()));
                }
                tablaClientes.setItems(oblist);
            } catch (ConnectException ex) {
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("No se pudo conectar a mysql");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (JDBCConnectionException ex) {
                //Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("Se encontro un error al quere insertar la información");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (CommunicationsException ex) {
                //Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("No se pudo comunicar con la base de datos mysql");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (InvocationTargetException ex) {
                //Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExceptionInInitializerError ex) {
                //Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML private void deleteCliente(ActionEvent event){
        Cliente cliente = tablaClientes.getSelectionModel().getSelectedItem();
        
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMACION");
        alert.setHeaderText("Se va a eliminar al cliente " + cliente.getNombre());
        alert.setContentText("¿Seguro que desea eliminarlo?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            try {
                ClienteDao dao = new ClienteDaoMysql();
                dao.delete(cliente);
                obtenerClientes();
            } catch (ConnectException ex) {
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("No se pudo conectar a mysql");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (JDBCConnectionException ex) {
                //Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("Se encontro un error al quere insertar la información");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (CommunicationsException ex) {
                //Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("No se pudo comunicar con la base de datos mysql");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (InvocationTargetException ex) {
                //Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExceptionInInitializerError ex) {
                //Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            
        }
    }
    
//    @FXML private void updateCliente(ActionEvent evet){
//        Cliente cliente = tablaClientes.getSelectionModel().getSelectedItem();
//        ClienteDao dao = new ClienteDaoMysql();
//        
//        txtCorreo.setText(cliente.getCorreo());
//        txtDomicilio.setText(cliente.getDomicilio());
//        txtNombre.setText(cliente.getNombre());
//        txtRFC.setText(cliente.getRfc());
//        txtTelefono.setText(cliente.getTelefono());
//        idCliente = cliente.getId();
//        
//        obtenerClientes();
//        btnGuardar.setDisable(false);
//    }
    
    @FXML private void saveCliente(){
        if (verificar()){
            try {
                Cliente cliente = new Cliente();
                
                cliente.setId(idCliente);
                cliente.setCorreo(txtCorreo.getText());
                cliente.setDomicilio(txtDomicilio.getText());
                cliente.setNombre(txtNombre.getText());
                cliente.setRfc(txtRFC.getText());
                cliente.setTelefono(txtTelefono.getText());
                
                 Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("CONFIRMACION");
                alert.setHeaderText("Se va a modificar al cliente " + cliente.getNombre());
                alert.setContentText("¿Seguro que desea modificarlo?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    ClienteDao dao = new ClienteDaoMysql();
                    dao.update(cliente);
                    limpiar();
                    btnGuardar.setDisable(true);
                    this.idCliente = 0;
                    obtenerClientes();
                }
                
            } catch (ConnectException ex) {
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("No se pudo conectar a mysql");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (JDBCConnectionException ex) {
                //Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("Se encontro un error al quere insertar la información");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (CommunicationsException ex) {
                //Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("No se pudo comunicar con la base de datos mysql");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (InvocationTargetException ex) {
                //Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExceptionInInitializerError ex) {
                //Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //Cuando se manda el controlador de caso a este mismo le regresamos el valor del cliente que queremos abir para que se 
    //imprima en la pantalla de caso segun el cliente seleccionado
    @FXML private void retornarCliente(MouseEvent event){
        Cliente cliente = tablaClientes.getSelectionModel().getSelectedItem();
        
//        if(cliente != null && getAgregarCasoController() == null){
//            btnEliminar.setDisable(false);
//        }
        
        if (event.getClickCount() == 2 && cliente != null){
//           if(getAgregarCasoController() != null){
//                getAgregarCasoController().obtenerCliente(cliente);
//               ((Node)(event.getSource())).getScene().getWindow().hide(); 
//           }else{
               
               ClienteDao dao = new ClienteDaoMysql();
                txtCorreo.setText(cliente.getCorreo());
                txtDomicilio.setText(cliente.getDomicilio());
                txtNombre.setText(cliente.getNombre());
                txtRFC.setText(cliente.getRfc());
                txtTelefono.setText(cliente.getTelefono());
                idCliente = cliente.getId();

                //obtenerClientes();
                btnGuardar.setDisable(false);
                btnAgregar.setDisable(true);
//           }
           
        }
    }
    
    /* VErificar que ningun campo sea nulo y sea correcto*/
    private boolean verificar(){
        boolean permitido = true;
        
        if(txtNombre.getText().equals("")){
            lblNombre.setVisible(true);
            permitido = false;
        }else{
            lblNombre.setVisible(false);    
        }
        
        if(txtCorreo.getText().equals("") || !validacionCorreo()){
            lblCorreo.setVisible(true);
            permitido = false;
        }else{
            lblCorreo.setVisible(false);    
        }
        
        if(txtDomicilio.getText().equals("")){
            lblDomicilio.setVisible(true);
            permitido = false;
        }else{
            lblDomicilio.setVisible(false);    
        }
        
        if(txtRFC.getText().equals("")){
            lblRFC.setVisible(true);
            permitido = false;
        }else{
            lblRFC.setVisible(false);    
        }
        
        try{
            Integer.parseInt(txtTelefono.getText());
            lblTelefono.setVisible(false);
        }catch(NumberFormatException ex){
            permitido = false;
            lblTelefono.setVisible(true);
        }
        
        
        return permitido;
    }
    
    
    private void obtenerClientes(){
        btnEliminar.setDisable(true);
        try {
            tablaClientes.getItems().clear();
            ClienteDao dao = new ClienteDaoMysql();
            
            Iterator<Cliente> i = dao.read().iterator();
            
            while(i.hasNext()){
                oblist.add(i.next());
            }
            
            columnNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
            columnRFC.setCellValueFactory(new PropertyValueFactory("rfc"));
            columnDomicilio.setCellValueFactory(new PropertyValueFactory("domicilio"));
            columnTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
            columnCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
            
            tablaClientes.setItems(oblist);
        } catch (ConnectException ex) {
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("No se pudo conectar a mysql");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (JDBCConnectionException ex) {
                //Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("Se encontro un error al quere insertar la información");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (CommunicationsException ex) {
                //Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
                
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                
                alerta.setHeaderText("No se pudo comunicar con la base de datos mysql");
                alerta.setContentText(ex.getMessage());
                alerta.show();
                
            } catch (InvocationTargetException ex) {
                //Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExceptionInInitializerError ex) {
                //Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public boolean validacionCorreo(){
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9._]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(txtCorreo.getText());
        
        if(m.find() && m.group().equals(txtCorreo.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("¡Error!");
            alert.setContentText("El formato de correo es invalido");
            alert.showAndWait();
            return false;
        }   
    }
    
    @FXML private void LimitarCaracteres(KeyEvent event){
        if(txtNombre.getText().length() > 30){
            txtNombre.setText(txtNombre.getText().substring(0, 30));
            txtNombre.positionCaret(30);
        }
        
        if(txtRFC.getText().length() > 13){
            txtRFC.setText(txtRFC.getText().substring(0, 13));
            txtRFC.positionCaret(13);
        }
        
        if(txtCorreo.getText().length() > 30){
            txtCorreo.setText(txtCorreo.getText().substring(0, 30));
            txtCorreo.positionCaret(30);
        }
        
        if(txtDomicilio.getText().length() > 40){
            txtNombre.setText(txtNombre.getText().substring(0, 40));
            txtNombre.positionCaret(40);
        }
        
        if(txtTelefono.getText().length() > 10){
            txtTelefono.setText(txtTelefono.getText().substring(0, 10));
            txtTelefono.positionCaret(10);
        }
        
    }
    
    @FXML private void limpiarCampos(ActionEvent event){
        limpiar();
        this.idCliente = 0;
        btnAgregar.setDisable(false);
        btnGuardar.setDisable(true);
    }
    
    private void limpiar(){
        txtCorreo.clear();
        txtDomicilio.clear();
        txtNombre.clear();
        txtRFC.clear();
        txtTelefono.clear();
    }

    
//    public AgregarCasoController getAgregarCasoController() {
//        return agregarCasoController;
//    }
//
//    
//    public void setAgregarCasoController(AgregarCasoController agregarCasoController) {
//        this.agregarCasoController = agregarCasoController;
//    }

}
