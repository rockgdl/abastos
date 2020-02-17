/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.fazecast.jSerialComm.SerialPort;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 *
 * @author fpiceno
 * 
 * exec:java -Dexec.mainClass="test.InputStreamSerial"
 */
public class InputStreamSerial {
    
    
          private static DataOutputStream os = null;
	private static DataInputStream is = null;
        private static String responseLine;
        private static StringTokenizer tokens=null;
    
    public static void main(String[] args) {
        
        String[] portNames = SerialPortList.getPortNames();
    for(int i = 0; i < portNames.length; i++){
        System.out.println(portNames[i]);
    }
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
        String s = new String(buffer);
        
        System.out.println("tengo esto en linea "+s);
        
        
        
        
        
        
        
        
        
        
        
        
        
        serialPort.closePort();//Close serial port
    }
    catch (SerialPortException ex) {
        System.out.println(ex);
    }
    }
    
}
