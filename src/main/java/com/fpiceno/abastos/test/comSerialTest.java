package com.fpiceno.abastos.test;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import jssc.SerialPort;
import jssc.SerialPortList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fpiceno
 * exec:java -Dexec.mainClass="com.fpiceno.abastos.test.comSerialTest"  
 */
public class comSerialTest {
     SerialPort arduinoPort = null;
     private static ObservableList<String> portList;
    
    Label labelValue;
    public static void main(String[] args) {
        
         detectPort();
   
     
  
    
}
  private static  void detectPort(){
         
        portList = FXCollections.observableArrayList();
 
        String[] serialPortNames = SerialPortList.getPortNames();
                String[] serialPortDescripcion = SerialPortList.getPortNames();

        for(String name: serialPortNames){
            System.out.println(name);
            portList.add(name);
        }
        
    }
}