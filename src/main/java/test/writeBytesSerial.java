/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fpiceno
 */
public class writeBytesSerial {
    
    

    public static void main(String[] args) {
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
		} catch (Exception e) {
                e.printStackTrace();
                }
                
                
                SerialPort comPort = ports[serialPortChoice];
//    SerialPort comPort = SerialPort.getCommPorts()[2];
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 1000, 0);
        
          String data="\nP\r";
                  byte[] b = data.getBytes();
            int l = comPort.writeBytes(b, b.length);
            if (l == -1) {
                    try {
                        throw new IOException("Write error.");
                    } catch (IOException ex) {
                        Logger.getLogger(writeBytesSerial.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
                    byte[] buffer = new byte[1024];
                    int bytesAvailable = comPort.bytesAvailable();
            int bytesRead = comPort.readBytes(buffer, Math.min(buffer.length, bytesAvailable));
            String response = new String(buffer, 0, bytesRead);
            
            System.out.println("response de la bascula "+response);
            // b = getLineEndingType().getLineEnding().getBytes();
//            l = comPort.writeBytes(b, b.length);
//            if (l == -1) {
//                    try {
//                        throw new IOException("Write error.");
//                    } catch (IOException ex) {
//                        Logger.getLogger(writeBytesSerial.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//            }
    }
    
}
