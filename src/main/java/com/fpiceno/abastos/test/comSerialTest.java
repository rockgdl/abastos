//package com.fpiceno.abastos.test;
//
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.application.Platform;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.control.Label;
//import jssc.SerialPort;
//import static jssc.SerialPort.MASK_RXCHAR;
//import jssc.SerialPortEvent;
//import jssc.SerialPortEventListener;
//import jssc.SerialPortException;
//import jssc.SerialPortList;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
///**
// *
// * @author fpiceno
// * exec:java -Dexec.mainClass="com.fpiceno.abastos.test.comSerialTest"  
// */
//public class comSerialTest {
//     SerialPort arduinoPort = null;
//     private static ObservableList<String> portList;
//    
//    Label labelValue;
//    public static void main(String[] args) {
//        
//         detectPort();
//   
//     
//  
//    
//}
//  private static  void detectPort(){
//         
//         try {
//             portList = FXCollections.observableArrayList();
//             
//             String[] serialPortNames = SerialPortList.getPortNames();
//             String[] serialPortDescripcion = SerialPortList.getPortNames();
//             
//             for(String name: serialPortNames){
//                 System.out.println(name);
//                 portList.add(name);
//             }
//             
//             SerialPort serialPort = new SerialPort("COM1");
//             
//             serialPort.openPort();
//             
//             serialPort.setParams(SerialPort.BAUDRATE_9600,
//                     SerialPort.DATABITS_8,
//                     SerialPort.STOPBITS_1,
//                     SerialPort.PARITY_NONE);
//             
//             serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
//                     SerialPort.FLOWCONTROL_RTSCTS_OUT);
//             
//             serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
//             
//             serialPort.writeString("Hurrah!");
//         } catch (SerialPortException ex) {
//             Logger.getLogger(comSerialTest.class.getName()).log(Level.SEVERE, null, ex);
//         }
//        
//    }
//  
//  private static class PortReader implements SerialPortEventListener {
//
//    @Override
//    public void serialEvent(SerialPortEvent event) {
//        if(event.isRXCHAR() && event.getEventValue() > 0) {
//            try {
//                String receivedData = serialPort.readString(event.getEventValue());
//                System.out.println("Received response: " + receivedData);
//            }
//            catch (SerialPortException ex) {
//                System.out.println("Error in receiving string from COM-port: " + ex);
//            }
//        }
//    }
//    
//    
//    public boolean connectArduino(String port){
//        
//        System.out.println("connectArduino");
//        
//        boolean success = false;
//        SerialPort serialPort = new SerialPort(port);
//        try {
//            serialPort.openPort();
//            serialPort.setParams(
//                    SerialPort.BAUDRATE_9600,
//                    SerialPort.DATABITS_8,
//                    SerialPort.STOPBITS_1,
//                    SerialPort.PARITY_NONE);
//            serialPort.setEventsMask(MASK_RXCHAR);
//            serialPort.addEventListener((SerialPortEvent serialPortEvent) -> {
//                if(serialPortEvent.isRXCHAR()){
//                    try {
//                        String st = serialPort.readString(serialPortEvent
//                                .getEventValue());
//                        System.out.println(st);
//                        
//                        //Update label in ui thread
//                        Platform.runLater(() -> {
//                            labelValue.setText(st);
//                        });
//                        
//                    } catch (SerialPortException ex) {
//                        Logger.getLogger(JavaFX_jssc_Uno.class.getName())
//                                .log(Level.SEVERE, null, ex);
//                    }
//                    
//                }
//            });
//            
//            arduinoPort = serialPort;
//            success = true;
//        } catch (SerialPortException ex) {
//            Logger.getLogger(JavaFX_jssc_Uno.class.getName())
//                    .log(Level.SEVERE, null, ex);
//            System.out.println("SerialPortException: " + ex.toString());
//        }
//
//        return success;
//    }
//    
//      public void disconnectArduino(){
//        
//        System.out.println("disconnectArduino()");
//        if(arduinoPort != null){
//            try {
//                arduinoPort.removeEventListener();
//                
//                if(arduinoPort.isOpened()){
//                    arduinoPort.closePort();
//                }
//                
//            } catch (SerialPortException ex) {
//                Logger.getLogger(JavaFX_jssc_Uno.class.getName())
//                        .log(Level.SEVERE, null, ex);
//            }
//        }
//    }
//}