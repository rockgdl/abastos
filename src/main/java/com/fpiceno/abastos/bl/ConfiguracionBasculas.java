package com.fpiceno.abastos.bl;

import java.io.IOException;
import java.util.Properties;





public class ConfiguracionBasculas  {
	
	/**
	 * Configuracion de Propiedades	
	 * esta clase lee un archivo de properties para el software de abastos
	 * por default la primera instancia toma el archivo de propiedades para intentar conectar a una bascula 
	 *
	 * @autor Fabian Piceno Roque febrero 2020
	 */

    
    
		private static Properties configuracion;
	/**
	 * Verifica la configuracion y envia la actualizacion de  propiedades y  sera leida 	
	 */
		private static void leerConfiguracion(){
			try {
				if (configuracion==null){
					 configuracion = new Properties();
					 configuracion.load(ConfiguracionBasculas.class.getResourceAsStream("bascula.properties"));
//					 debug("Leyo la configuración del módulo de maquinaria");
				}
			} catch (IOException e) {
//				error("No se pudo leer la configuración del módulo de ETIQUETAS");
				e.printStackTrace();
			}
		}
	/**
	 * Devuelve la url   	
	 * @return URL
	 */
		public static Integer obtenBaudios(){
			leerConfiguracion();
			return Integer.parseInt(configuracion.getProperty("baudios"));
		}

		public static String obtenComPort(){
			leerConfiguracion();
			return configuracion.getProperty("comport");
		}
                public static String obtenParidad()
                {
                    leerConfiguracion();
                    return configuracion.getProperty("paridad");
                }
                public static String obtenStopBit()
                {
                    leerConfiguracion();
                    return configuracion.getProperty("stopbit");
                }
		
                  public static String obtenDataBits()
                {
                    leerConfiguracion();
                    return configuracion.getProperty("databits");
                }
                    public static String obtenSymbolPeso()
                {
                    leerConfiguracion();
                    return configuracion.getProperty("symbolpeso");
                }
           
	
		
	
		
	

}
