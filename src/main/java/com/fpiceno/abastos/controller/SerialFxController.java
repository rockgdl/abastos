package com.fpiceno.abastos.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class
 *
 * @author fpiceno
 */
public class SerialFxController implements Initializable {

    /**
     * Initializes the controller class.
     */
            private final org.apache.log4j.Logger LOG= org.apache.log4j.Logger.getLogger(SerialFxController.class.getSimpleName());

    @FXML ComboBox portList;
        @FXML
    private AnchorPane anchorPane;
    
    private ObservableList <ArrayList> oblistBajas= FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                System.out.println("\nUsing Library Version v" + SerialPort.getVersion());
		SerialPort[] ports = SerialPort.getCommPorts();
		System.out.println("\nAvailable Ports:\n");
                List<SerialPort> list=Arrays.asList(ports);
              
                for(SerialPort c : list)
                {
                    
                    LOG.info("esto tengo en serial port"+c.getSystemPortName()+c.getPortDescription() +c.getSystemPortName());
                }
               portList.setItems(FXCollections.observableArrayList(list));
		for (int i = 0; i < ports.length; ++i)
                {
                  //  portList.setItems(FXCollections.observableArrayList());
                    LOG.info("   [" + i + "] " + ports[i].getSystemPortName() + ": " + ports[i].getDescriptivePortName() + " - " + ports[i].getPortDescription());
                }
                
                
    }    
    
    @FXML private void setConfiguracion()
    {
                try {
                    LOG.info("REGRESANDO a la pantalla de configuracion con los parametros para la base de datos ");
                    
                    FlowPane pane;
                    LOG.info("cargando vista de productos ");
                    pane= FXMLLoader.load(getClass().getResource("/fxml/Productos.fxml"));
                    anchorPane.getChildren().setAll(pane);
                } catch (IOException ex) {
                    Logger.getLogger(SerialFxController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
}
