package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import model.Pedido;
import model.PedidoTienda;

public class Utilidades {
	
	// Posiciones de los datos
	
	private final static int POSICION_PRODUCTO = 0;
	private final static int POSICION_UNIDADES = 1;
	private final static int POSICION_PRECIO = 2;
	private final static int POSICION_SECCION = 3;
	private final static int POSICION_FECHA = 4;
	
	// Formato fecha
	
	public final static String FORMATO_FECHA = "dd/MM/yyyy";
	
	private static SimpleDateFormat fechaPersonal = new SimpleDateFormat(FORMATO_FECHA);
	
	// Construye un objeto pedido a partir de una cadena de datos y un separador
	
	public static Pedido construirPedido(String datos, String separador) {

		String[] cadena = datos.split(separador);


		try {
			
			return new Pedido(cadena[POSICION_PRODUCTO],
						      Integer.parseInt(cadena[POSICION_UNIDADES]),
						      Double.parseDouble(cadena[POSICION_PRECIO]),
						      cadena[POSICION_SECCION],
						      fechaPersonal.parse(cadena[POSICION_FECHA]));
			
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
			
			return null;
			
		} catch (ParseException e) {
			
			e.printStackTrace();
			
			return null;
		}
	}
	
	// Construye una cadena a partir de un objeto Pedido y un separador
	
	public static String construirCadena(Pedido p, String separador) {
		
		return p.getProducto() + separador + 
			   p.getUnidades() + separador + 
			   p.getPrecioUnitario() + separador +
			   p.getSeccion() + separador +
			   fechaPersonal.format(p.getFecha());
	}
	
	// Construye una cadena a partir de un objeto PedidoTienda y un separador
	
	public static String construirCadena(PedidoTienda p, String separador) {
		
		return p.getTienda() + separador +
			   p.getProducto() + separador + 
			   p.getUnidades() + separador + 
			   p.getPrecioUnitario() + separador +
			   p.getSeccion() + separador +
			   fechaPersonal.format(p.getFecha());
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
	
	// Convertir Cadena a Date
	
	public static Date convertirCadenaADate(String cadena) {
		
		try {
			
			return fechaPersonal.parse(cadena);
			
		} catch (ParseException e) {

			e.printStackTrace();
			
			return null;
		}
	}
	
	// Convierte objeto Pedido a objeto PedidoTienda
	
	public static PedidoTienda convertirPedidoAPedidoTienda(Pedido p, String tienda) {
		
		return new PedidoTienda(p.getProducto(), p.getUnidades(), p.getPrecioUnitario(), p.getSeccion(), p.getFecha(), tienda);
	}
}
