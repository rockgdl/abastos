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
                comPort.setBaudRate(9600);
//                int newBaudRate, int newDataBits, int newStopBits, int newParity
                //comPort.setComPortParameters(9600, 8, 1, 0);
            

//    SerialPort comPort = SerialPort.getCommPorts()[2];
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 100000, 0);
        
         os = new DataOutputStream(comPort.getOutputStream());
        is = new DataInputStream(comPort.getInputStream());
        
        
          if (os != null && is != null) 
        {
            System.out.println("no es null el dataoutput ni el input");
//        try {
            
            //System.out.println(" pude obtener los inputStrem  y los OutputStream "+is.readLine());
            try {
                 os.write("P\r\n".getBytes());
                 // os.write("\nP\r".getBytes());
              //  https://en.wikibooks.org/wiki/Serial_Programming/Serial_Java
               // System.out.println("que tengo en el is "+is.available());
                  
                  if( (responseLine=is.readLine())!=null)
                  {
                      System.out.println("contesta con la linea "+responseLine);
                  }
                  else
                  {
                        System.out.println(is.readLine());
                  }
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
// catch (IOException ex) {
//            Logger.getLogger(InputStreamSerial.class.getName()).log(Level.SEVERE, null, ex);
//        }
        }

            
    
          String data="\nP\r";
          
          
          
//          byte[] b = data.getBytes();
//            int l = serialPort.writeBytes(b, b.length);
//            if (l == -1) {
//              throw new IOException("Write error.");
//            }
//            b = getLineEndingType().getLineEnding().getBytes();
//            l = serialPort.writeBytes(b, b.length);
//            if (l == -1) {
//              throw new IOException("Write error.");
//            }
    
          
          
            //Torrey
            //byte[] buffer = Encoding.ASCII.GetBytes("P");
            //PuertoSerieBascula.Write(buffer, 0, buffer.Length);

//            if (Dispositivos_Bascula.Propiedades.Nombre == "Datalogic Magellan 8400")
//            {
//                byte[] inBuffer = new byte[] { 87 };
//                PuertoSerieBascula.Write(inBuffer, 0, inBuffer.Length);
//            }
//            else //Torrey
//            {
//                byte[] inBuffer = new byte[] { 80 };
//                PuertoSerieBascula.Write(inBuffer, 0, inBuffer.Length);
//            }

            //PuertoSerieBascula.Write("P");
    
    
    
    
}
