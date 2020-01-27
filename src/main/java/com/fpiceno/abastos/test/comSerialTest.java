//package com.fpiceno.abastos.test;
//
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.control.Label;
//import jssc.SerialPort;
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
//}