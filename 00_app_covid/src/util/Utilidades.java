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
	
	// Mapa de comunidades autónomas
	
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
	
	// Convierte el dato COVID a una cadena teniendo en cuenta el orden de los datos y el separador
	
	public static String DatoCovidACadena(DatoCovid dcv, String separador) {
		
		return dcv.getCcaa_iso() + separador +
			   fechaPersonal.format(dcv.getFecha()) + separador +
			   dcv.getNum_casos() + separador +
			   dcv.getNum_casos_prueba_pcr() + separador +
			   dcv.getNum_casos_prueba_test_ac() + separador +
			   dcv.getNum_casos_prueba_otras() + separador +
			   dcv.getNum_casos_prueba_desconocida();		
	}

	// Convierte el DatoCovid a una cadena para representar un caso de COVID en una CA
	
	public static String DatoCovidACadenaCasoCovid(DatoCovid dcv) {
		
		String tabs;
		
		if (NOMBRE_CA.get(dcv.getCcaa_iso()).length() > 12) {
			
			tabs = "\t";
			
		} else {
			
			tabs = "\t\t";
		}
		
		return "CASO COVID ----> " + "Comunidad: " + NOMBRE_CA.get(dcv.getCcaa_iso()) +
			   tabs + "Fecha: " + fechaPersonal.format(dcv.getFecha()) +
			   "\tNúmero de casos: " + dcv.getNum_casos();	
	}
	
	// Construye un objeto DatoCovid a partir de una cadena de datos y un separador
	
	public static DatoCovid cadenaADatoCovid(String datos, String separador) {

		String[] cadena = datos.split(separador);

		try {
			
			return new DatoCovid(cadena[POSICION_CCAA_ISO],
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
