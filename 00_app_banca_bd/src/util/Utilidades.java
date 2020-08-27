package util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Utilidades {
	
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
}
