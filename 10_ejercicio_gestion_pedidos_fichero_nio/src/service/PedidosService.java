package service;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.Pedido;
import util.Utilidades;

public class PedidosService {
	
	// Ruta del archivo de datos
	
	private final String RUTA = "d:\\FicherosTestJava\\pedidos.txt";
	
	// Separador utilizado en los datos
	
	private final String SEPARADOR_DATOS = ",";
	
	// Constructor
	
	private Path pathPedidos;
	
	public PedidosService() {
		
		pathPedidos = Paths.get(RUTA);
		
		if (!Files.exists(pathPedidos)) {
			
			try {
				
				Files.createFile(pathPedidos);
				
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
	
	// Se comprueba que existe el pedido
	
	private boolean comprobarPedido(Pedido pedido) {
		
		try (Stream<String> st = Files.lines(pathPedidos)) {
			
			return st
					.anyMatch(p -> Utilidades.construirPedido(p, SEPARADOR_DATOS).equals(pedido));
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			return false;
		}

	}
	
	// Se vuelcan los pedidos al fichero de datos
	// Sobreescribe el fichero con los nuevos datos
	
	private boolean volcarPedidosAlFichero(List<Pedido> pedidos) {
		
		try {
			
			// Borra el archivo si existe
			
			Files.delete(pathPedidos);
			
			// Vuelca los datos
			
			Files.write(pathPedidos, 
					pedidos
					.stream()
					.map(p -> Utilidades.construirCadena(p, SEPARADOR_DATOS))
					.collect(Collectors.toList()),
						StandardCharsets.UTF_8,
						StandardOpenOption.CREATE);
			
			return true;
			
		} catch (IOException e) {

			e.printStackTrace();
			
			return false;
		}
	}
	
	// Se registra el pedido si no existe
	
	public boolean registrarPedido(Pedido p) {
		
		if (!comprobarPedido(p)) {
			
			try {
				
				// Se escribe el pedido en el formato elegido
				
				Files.writeString(pathPedidos, 
						Utilidades.construirCadena(p, SEPARADOR_DATOS),
						StandardCharsets.UTF_8,
						StandardOpenOption.APPEND);
				
				// Se añade el salto de línea
				
				Files.writeString(pathPedidos, "\n", StandardOpenOption.APPEND);
				
				return true;
				
			} catch (IOException e) {

				e.printStackTrace();
				
				return false;
			}
		}
		
		return false;
	}
	
	// Se procesa el pedido
	
	public Pedido procesarPedido() {
		
		List<Pedido> pedidos = leerPedidos();
		
		Pedido procesado = null;
		
		// Si hay pedidos se procesa el de más prioridad

		if (!pedidos.isEmpty()) {
			
			procesado = pedidos.remove(0);
			
			if (procesado != null) {
			
				volcarPedidosAlFichero(pedidos);
				
				return procesado;
			}
		}
		
		return procesado;
	}

	// Devuelve la facturación pendiente de todos los pedidos
	
	public double facturacionPendiente() {
		
		try (Stream<String> st = Files.lines(pathPedidos)) {
			
			return st
					.mapToDouble(s -> Utilidades.construirPedido(s, SEPARADOR_DATOS).getPrecio())
					.sum();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			return 0;
			
		}

	}
	
	// Obtener pedido a partir del número de pedido
	
	public Pedido obtenerPedido(int numeroPedido) {
		
		try (Stream<String> st = Files.lines(pathPedidos)) {
			
			return st
					.map(s -> Utilidades.construirPedido(s, SEPARADOR_DATOS))
					.filter(p -> p.getId() == numeroPedido)
					.findFirst()
					.orElse(null);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			return null;
		}
	}
	
	// Prioriza un pedido
	
	public Pedido priorizarPedido(int id) {
		
		// Se intenta priorizar un pedido si hay pedidos
		
		List<Pedido> pedidos = leerPedidos();
		
		Pedido prioritario = null;
		
		// Si hay más de un pedido
		
		if (pedidos.size() > 1) {
			
			Pedido secundario = null;
			int posicion = 0;
				
			// Se guardan los pedidos a intercambiar

			if (obtenerPedido(id) != null) {
				
				posicion = pedidos.indexOf(obtenerPedido(id));
			
				// Si el buscado no es el primer pedido de la lista
				
				if (posicion > 0) {
					
					prioritario = pedidos.get(posicion);
					secundario = pedidos.get(posicion - 1);
	
					// Se da prioridad al pedido prioritario buscado
	
					pedidos.set((posicion - 1), prioritario);
					pedidos.set(posicion,  secundario);
				
					// Volcamos los datos al fichero
				
					volcarPedidosAlFichero(pedidos);
				}
			}
		}
		
		return prioritario;
	}
	
	// Devuelve todos los pedidos almacenados
	
	public List<Pedido> leerPedidos() {
	
		try (Stream<String> st = Files.lines(pathPedidos)) {
			
			return st
					.map(s -> Utilidades.construirPedido(s, SEPARADOR_DATOS))
					.collect(Collectors.toList());
			
		} catch (IOException e) {

			e.printStackTrace();
			
			return null;
		}
	}
}
