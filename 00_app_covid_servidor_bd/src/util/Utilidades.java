package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

public class Utilidades {
	
	// Formato fecha
	
	public final static String FORMATO_FECHA = "yyyy-MM-dd";
	
	private static SimpleDateFormat fechaPersonal = new SimpleDateFormat(FORMATO_FECHA);
	
	// Mapa de comunidades aut�nomas
	
	public static final Map<String, String> NOMBRE_CA = Map.ofEntries(
			
			Map.entry("AN", "Andaluc�a"),
			Map.entry("AR", "Arag�n"),
			Map.entry("AS", "Asturias"),
			Map.entry("CN", "Canarias"),
			Map.entry("CB", "Cantabria"),
			Map.entry("CM", "Castilla-La Mancha"),
			Map.entry("CL", "Castilla y Le�n"),
			Map.entry("CT", "Catalu�a"),
			Map.entry("EX", "Extremadura"),
			Map.entry("CE", "Ceuta"),
			Map.entry("GA", "Galicia"),
			Map.entry("IB", "Islas Baleares"),
			Map.entry("RI", "La Rioja"),
			Map.entry("MD", "Madrid"),
			Map.entry("MC", "Murcia"),
			Map.entry("NC", "Navarra"),
			Map.entry("PV", "Pa�s Vasco"),
			Map.entry("VC", "Valencia"),
			Map.entry("ML", "Melilla"));
	
	// Convertir LocalDate a Date
	
	public static Date convertirADate(LocalDate ld) {
		
		return Date.from(ld.atStartOfDay(ZoneId.systemDefault())
				          .toInstant());
	}
	
	// Convertir Date a LocalDate
	
	public static LocalDate convertirALocalDate(Date d) {
		
		return Instant.ofEpochMilli(d.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
	}
	
	// Convertir cadena de formato personal a Date
	
	public static Date convertirCadenaADate(String cadena) {
		
		try {
			
			return fechaPersonal.parse(cadena);
			
		} catch (ParseException e) {

			e.printStackTrace();
			
			return null;
		}
	}
}
