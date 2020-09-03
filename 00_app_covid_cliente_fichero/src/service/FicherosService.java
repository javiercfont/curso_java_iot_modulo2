package service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

import util.Utilidades;

public class FicherosService {
	
	// Ruta del archivo de datos
	
	private final String RUTA = "d:\\FicherosTestJava\\datos_covid_recuperados.csv";
	
	// Separador utilizado en los datos
	
	private final String SEPARADOR_DATOS = ",";
	
	// Vuelca los datos recuperados al fichero, en modo append
	
	public boolean volcarDatosAlFichero(String ca_iso, long total_casos, double media, Date fecha_pico) {
		
		try (PrintStream out = new PrintStream(new FileOutputStream(RUTA, true))) {
			
			out.println(ca_iso + SEPARADOR_DATOS + 
					    total_casos + SEPARADOR_DATOS + 
					    String.format("%2.1f", media).replace(',', '.') + SEPARADOR_DATOS + 
					    Utilidades.convertirALocalDate(fecha_pico));
			
			return true;
			
		} catch (IOException e) {

			e.printStackTrace();
			
			return false;
		}
	}
}
