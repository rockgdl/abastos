package com.fpiceno.abastos.controller;

import com.fpiceno.abastos.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ProductoController implements Initializable {
    
    
    
    private Logger LOG=Logger.getLogger(this.getClass().getSimpleName());
    @FXML
    private Label label;
    @FXML
    private TextField conceptoField;
    @FXML
    private TextField costoUnitField;
    @FXML
    private TextArea descripcionField;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void guardaProducto()
    {
        LOG.info(conceptoField.getText());
    }
}
