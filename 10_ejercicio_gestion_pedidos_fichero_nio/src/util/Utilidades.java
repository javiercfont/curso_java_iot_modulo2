package util;

import model.Pedido;

public class Utilidades {
	
	// Posiciones de los datos
	
	private final static int POSICION_ID = 0;
	private final static int POSICION_PRODUCTO = 1;
	private final static int POSICION_PRECIO = 2;
	
	public static Pedido construirPedido(String datos, String separador) {

		String[] cadena = datos.split(separador);

		return new Pedido(Integer.parseInt(cadena[POSICION_ID]),
				cadena[POSICION_PRODUCTO],
				Double.parseDouble(cadena[POSICION_PRECIO]));
	}
	
	public static String construirCadena(Pedido p, String separador) {
		
		return p.getId() + separador + p.getProducto() + separador + p.getPrecio();
		
	}

}
