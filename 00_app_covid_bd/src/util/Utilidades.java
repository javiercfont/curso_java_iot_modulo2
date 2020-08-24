package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import model.DatoCovid;

public class Utilidades {
	
	// Posiciones de los datos
	
	private final static int POSICION_CCAA_ISO = 0;
	private final static int POSICION_FECHA = 1;
	private final static int POSICION_CASOS = 2;
	private final static int POSICION_CASOS_PCR = 3;
	private final static int POSICION_CASOS_AC = 4;
	private final static int POSICION_CASOS_OTRA = 5;
	private final static int POSICION_CASOS_DESCONOCIDA = 6;
	
	// Formato fecha
	
	public final static String FORMATO_FECHA = "yyyy-MM-dd";
	
	private static SimpleDateFormat fechaPersonal = new SimpleDateFormat(FORMATO_FECHA);
	
	// Mapa de comunidades autÃ³nomas
	
	public static final Map<String, String> NOMBRE_CA = Map.ofEntries(
			
			Map.entry("AN", "Andalucía"),
			Map.entry("AR", "Aragón"),
			Map.entry("AS", "Asturias"),
			Map.entry("CN", "Canarias"),
			Map.entry("CB", "Cantabria"),
			Map.entry("CM", "Castilla-La Mancha"),
			Map.entry("CL", "Castilla y León"),
			Map.entry("CT", "Cataluña"),
			Map.entry("EX", "Extremadura"),
			Map.entry("CE", "Ceuta"),
			Map.entry("GA", "Galicia"),
			Map.entry("IB", "Islas Baleares"),
			Map.entry("RI", "La Rioja"),
			Map.entry("MD", "Madrid"),
			Map.entry("MC", "Murcia"),
			Map.entry("NC", "Navarra"),
			Map.entry("PV", "País Vasco"),
			Map.entry("VC", "Valencia"),
			Map.entry("ML", "Melilla"));
	
	// Separador utilizado en los datos
	
	public final static String SEPARADOR_DATOS = ",";
	
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
	
	// Construye un objeto DatoCovid a partir de una cadena de datos y un separador
	
	public static DatoCovid cadenaADatoCovid(String datos, String separador) {

		String[] cadena = datos.split(separador);

		try {
			
			// Para el idDato pasamos siempre un 0. Se gestiona en la BD
			
			return new DatoCovid(0,
					             cadena[POSICION_CCAA_ISO],
						         fechaPersonal.parse(cadena[POSICION_FECHA]),
						         Long.parseLong(cadena[POSICION_CASOS]),
						         Long.parseLong(cadena[POSICION_CASOS_PCR]),
						         Long.parseLong(cadena[POSICION_CASOS_AC]),
						         Long.parseLong(cadena[POSICION_CASOS_OTRA]),
						         Long.parseLong(cadena[POSICION_CASOS_DESCONOCIDA]));
			
		} catch (NumberFormatException | ParseException e) {

			e.printStackTrace();
			
			return null;
		}
	}
}
