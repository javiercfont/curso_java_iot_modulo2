package service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.Pedido;
import model.PedidoTienda;
import util.Utilidades;

public class GestorPedidos  {
	
	private Path path;
	private Path path_total;
	
	private final String DIR_TIENDA="D:\\FicherosTestJava\\pedidos_totales.txt";
	
	public static final String DIR1 = "data\\pedido_tienda_1.txt";
	public static final String DIR2 = "data\\pedido_tienda_2.txt";
	public static final String DIR3 = "data\\pedido_tienda_3.txt";
	
	// Separador utilizado en los datos
	
	private final String SEPARADOR_DATOS = ",";
	
	// Constructores
	
	// Para trabajar contra BD
	
	public GestorPedidos() {
		
		path = null;
		path_total = null;
	}
	
	// Para trabajar contra archivos de datos
	
	public GestorPedidos(String dir) {
		
		path = Paths.get(dir);
		
		if (!Files.exists(path)) {
			
			try {
				
				Files.createFile(path);
				
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		
		path_total = Paths.get(DIR_TIENDA);
		
		if (!Files.exists(path_total)) {
			
			try {
				
				Files.createFile(path_total);
				
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
	
	// Devuelve todos los pedidos almacenados (en un archivo)
	
	private Stream<Pedido> leerPedidos() {
		
		// Se usa el ISO_8859_1 para evitar problemas de acentos y demás
		
		try (Stream<String> st = Files.lines(path, StandardCharsets.ISO_8859_1)) {
			
			return st
					.map(s -> Utilidades.construirPedido(s, SEPARADOR_DATOS))
					.collect(Collectors.toList())
					.stream();
			
		} catch (IOException e) {

			e.printStackTrace();
			
			return null;
		}
	}
	
	// Almacena el pedido recibido (en un archivo)
	
	public void grabarPedido(Pedido pedido) {
		
		try {
			
			// Se escribe el pedido en el formato elegido más el separador de línea
			
			Files.writeString(path, 
					Utilidades.construirCadena(pedido, SEPARADOR_DATOS) + System.lineSeparator(),
					StandardCharsets.UTF_8,
					StandardOpenOption.APPEND);
			
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	// Almacena el pedido de tienda recibido (en BD)
	
	public void grabarPedido(List<Pedido> pedidos, String tienda) {
		
		// El try con recursos garantiza el cierre de la conexión
		
		try (Connection con = Datos.getConnection()) {
			
			String sql = "INSERT INTO pedidos(tienda, producto, unidades, precioUnitario, seccion, fecha) VALUES(?,?,?,?,?,?)";
			
			PreparedStatement st = con.prepareStatement(sql);
			
			// El primer campo representa siempre la tienda
			
			st.setString(1, tienda);
			
			// Por cada pedido, lo insertamos en la BD
			
			for (Pedido p : pedidos) {
				
				// La fecha hay que pasarla en sql.Date

				st.setString(2, p.getProducto());
				st.setInt(3, p.getUnidades());
				st.setDouble(4, p.getPrecioUnitario());
				st.setString(5, p.getSeccion());
				st.setDate(6, new java.sql.Date(p.getFecha().getTime()));
				
				st.executeUpdate();
			}
		
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	// Recupera los PedidosTienda de una tienda (desde BD)
	
	public List<PedidoTienda> recuperarPedidosTienda(String tienda) {
		
		List<PedidoTienda> pedidos = new ArrayList<>();
		
		// El try con recursos garantiza el cierre de la conexión
		
		try (Connection con = Datos.getConnection()) {
			
			String sql = "SELECT * FROM pedidos WHERE tienda=?";
			
			PreparedStatement st = con.prepareStatement(sql);
			
			// El primer campo representa siempre la tienda
			
			st.setString(1, tienda);
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				
			    pedidos.add(new PedidoTienda(rs.getInt("idPedido"),
			    		                     rs.getString("producto"),
			    		                     rs.getInt("unidades"), 
			    		                     rs.getDouble("precioUnitario"),
			    		                     rs.getString("seccion"),
			    		                     rs.getDate("fecha"),
			    		                     rs.getNString("tienda")));
			}
			
			return pedidos;
			
		} catch (SQLException e) {

			e.printStackTrace();
			
			return null;
		}
	}
	
	//media de ventas pedidos de esa seccion
	public double promedioSeccion(String seccion) {
		
		return leerPedidos()
              .filter(p -> p.getSeccion().equalsIgnoreCase(seccion))
              .mapToDouble(p -> p.getPrecioUnitario() * p.getUnidades())
              .average()
              .getAsDouble();
	}

	//ventas totales de el producto indicado
	public double totalProducto(String producto) {
		
		return leerPedidos()
		     .filter(p -> p.getProducto().equalsIgnoreCase(producto))
		     .mapToDouble(p -> p.getPrecioUnitario() * p.getUnidades())
		     .sum();
	}

	//devuelve el pedido con venta superior
	public Pedido pedidoSuperior() {
		
		return leerPedidos()
			       .max((p1, p2) -> p1.getPrecioUnitario() * p1.getUnidades() < p2.getPrecioUnitario() * p2.getUnidades() ? -1 : 1)
			       .orElse(null);
	}

	//devuelve lista de pedidos de una sección
	public List<Pedido> pedidosSeccion(String seccion) {
		
		return leerPedidos()
		          .filter(p -> p.getSeccion().equalsIgnoreCase(seccion))
		          .collect(Collectors.toList());
	}
	
	//devuelve el pedido con fecha más reciente
	public Pedido pedidoMasReciente() {
		
		return leerPedidos()
			      .max((p1, p2) -> p1.getFecha().compareTo(p2.getFecha()))
			      .orElse(null);
	}
	
	//devuelve lista de pedidos, posteriores a la fecha indicada
	public List<Pedido> pedidosPosterioresFecha(Date fecha) {
		
		return leerPedidos()
		         .filter(p -> p.getFecha().after(fecha))
		         .collect(Collectors.toList());
	}
	
	
	//lista de nombres de sección, no repetidas
	public List<String> secciones() {
		
		return leerPedidos()
		          .map(p -> p.getSeccion())
		          .distinct()
		          .collect(Collectors.toList());
	}
	
	// Pedidos en un rango de fechas
	public List<Pedido> pedidoRangoFecha(LocalDate fecha, Period periodo) {
		
		LocalDate fechaFin = fecha.plus(periodo);
		
		return leerPedidos()
		         .filter(p -> p.getFecha().after(Utilidades.convertirADate(fecha)) &&
		                     (p.getFecha().before(Utilidades.convertirADate(fechaFin))))
		         .collect(Collectors.toList());
	}
	
	// Extrae los pedidos de la fecha actual
	public List<Pedido> pedidosFechaActual() {
		
		return leerPedidos()
		          .filter(p -> Utilidades.convertirALocalDate(p.getFecha()).equals(LocalDate.now()))
		          .collect(Collectors.toList());
	}
}
