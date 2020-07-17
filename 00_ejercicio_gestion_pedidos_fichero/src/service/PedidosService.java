package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import model.Pedido;

public class PedidosService {
	
	// Ruta del archivo de datos
	
	private final String RUTA = "d:\\FicherosTestJava\\pedidos.txt";
	
	// Posiciones de los datos
	
	private final int POSICION_ID = 0;
	private final int POSICION_PRODUCTO = 1;
	private final int POSICION_PRECIO = 2;
	
	// Separador utilizado en los datos
	
	private final String SEPARADOR_DATOS = ",";
	
	// Guarda la facturación pendiente
	
	private double facturacion;
	
	// Se comprueba que existe el pedido
	
	private boolean comprobarPedido(Pedido p) {
		
		for (Pedido pedido : getPedidos()) {
			
			if (pedido.equals(p)) {
				
				return true;
			}
		}
		
		return false;
	}
	
	// Se vuelcan los pedidos al fichero de datos
	// Sobreescribe el fichero con los nuevos datos
	
	private boolean volcarPedidosAlFichero(List<Pedido> pedidos) {
		
		try (PrintStream out = new PrintStream(new FileOutputStream(RUTA, false));) {
			
			pedidos.forEach(p -> out.println(p.getId() + SEPARADOR_DATOS + p.getProducto() + SEPARADOR_DATOS + p.getPrecio()));
			
			return true;
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		return false;
	}
	
	// Se registra el pedido si no existe
	
	public boolean registrarPedido(Pedido p) {
		
		if (!comprobarPedido(p)) {
			
			try (PrintStream out = new PrintStream(new FileOutputStream(RUTA, true));) {
				
				// Se escribe el pedido en el formato elegido
				
				out.println(p.getId() + SEPARADOR_DATOS + p.getProducto() + SEPARADOR_DATOS + p.getPrecio());
				
			} catch (IOException e) {

				e.printStackTrace();
			}
			
			return true;
		}
		
		return false;
	}
	
	// Se procesa el pedido
	
	public Pedido procesarPedido() {
		
		List<Pedido> pedidos = getPedidos();
		
		Pedido procesado = null;
		
		// Si hay pedidos se procesa el de más prioridad

		if (!pedidos.isEmpty()) {
			
			procesado = pedidos.remove(0);
			
			if (volcarPedidosAlFichero(pedidos)) {
			
				return procesado;
			}
		}
		
		return procesado;
	}
	
	// Se busca la posición del pedido por su id
	
	public int buscarPedido(int id) {
		
		List<Pedido> pedidos = getPedidos();
		
		if (!pedidos.isEmpty()) {
			
			for (Pedido pedido : pedidos) {
				
				if (pedido.getId() == id) {
					
					return pedidos.indexOf(pedido);
				}
			}
			
			return -1;
		}
		
		return -1;
	}

	// Devuelve la facturación pendiente de todos los pedidos
	
	public double facturacionPendiente() {
		
		facturacion = 0.0;
		
		getPedidos().forEach(p -> facturacion += p.getPrecio());
		
		return facturacion;
	}
	
	// Prioriza un pedido
	
	public Pedido priorizarPedido(int id) {
		
		// Se intenta priorizar un pedido si hay pedidos
		
		Pedido prioritario = null;
		
		List<Pedido> pedidos = getPedidos();
		
		if (pedidos.size() != 0) {
			
			int posicion;
			Pedido secundario = null;

			// Buscamos la posición que ocupa el pedido

			posicion = buscarPedido(id);

			// Si hay más de un pedido

			if (posicion > 0) {

				// Se guardan los pedidos a intercambiar

				prioritario = pedidos.get(posicion);
				secundario = pedidos.get(posicion - 1);

				// Se da prioridad al pedido prioritario buscado

				pedidos.set(posicion, secundario);
				pedidos.set((posicion - 1),  prioritario);
				
				// Volcamos los datos al fichero
				
				volcarPedidosAlFichero(pedidos);
			}
		}
		
		return prioritario;
	}
	
	// Devuelve todos los pedidos almacenados
	
	public List<Pedido> getPedidos() {
		
		File archivoPedidos = new File(RUTA);
		
		List<Pedido> pedidos = new LinkedList<>();
		
		// Si existe el archivo de pedidos leemos los pedidos
		// Si no se devuelve la lista vacía
		
		if (archivoPedidos.exists()) {
			
			String linea;
			String datos[];
			
			try (FileReader fr = new FileReader(RUTA);
					BufferedReader br = new BufferedReader(fr);) {

				while ((linea = br.readLine()) != null) {

					// Se extraen los datos leídos teniendo en cuenta el separador

					datos = linea.split(SEPARADOR_DATOS);

					Pedido pedido = new Pedido(Integer.parseInt(datos[POSICION_ID]),
							datos[POSICION_PRODUCTO],
							Double.parseDouble(datos[POSICION_PRECIO]));

					// Se agrega el pedido a la lista

					pedidos.add(pedido);
				}

			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return pedidos;
	}
}
