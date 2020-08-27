package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.Movimiento;
import util.Utilidades;

public class BancaService {
	
	// Vuelca el contenido de la BD de movimientos a memoria
	
	private Stream<Movimiento> leerMovimientosBD() {
		
		// El try con recursos garantiza el cierre de la conexión
		
		try (Connection con = Datos.getConnection()) {
			
			String sql = "SELECT * FROM movimientos";
			
			PreparedStatement st = con.prepareStatement(sql);
			
			// Se obtine el resultset de la consulta
			
			ResultSet rs = st.executeQuery();
			
			// Se genera un Stream personalizado con los datos
			
			Stream.Builder<Movimiento> builder = Stream.builder();
			
	        while (rs.next()) {
	        	
	            Movimiento dato = new Movimiento(rs.getInt("idMovimiento"),
	            		                         rs.getInt("idCuenta"),
	            		                         rs.getDate("fecha"),
	            		                         rs.getDouble("cantidad"),
	            		                         rs.getString("operacion"));
	            
	            builder.add(dato);
	        }
	        
			// Devuelve un Stream de datos
			
            return builder.build();
			
		} catch (SQLException e) {

			e.printStackTrace();
			
			return Stream.empty();
		}
	}
	
	public boolean comprobarCuenta(int num_cuenta) {
		
		// El try con recursos garantiza el cierre de la conexión
		
		try (Connection con = Datos.getConnection()) {
			
			String sql = "SELECT * FROM cuentas WHERE numeroCuenta=?";
			
			PreparedStatement st = con.prepareStatement(sql);
			
			// Se pasa el parámetro al PreparedStatement
			
			st.setInt(1, num_cuenta);
			
			// Se obtine el resultset de la consulta
			
			ResultSet rs = st.executeQuery();
			
			// Si existe el registro
			
            if (rs.next()) {
            	
            	return true;
            }
			
		} catch (SQLException e) {

			e.printStackTrace();
			
			return false;
		}
		
		return false;
	}
	
	public int sacar(double cantidad, int num_cuenta) {
		
		int registros_afectados = 0;
		
		// El try con recursos garantiza el cierre de la conexión
		
		try (Connection con = Datos.getConnection()) {
				
			// Se crea una consulta para actualizar la tabla de cuentas

			String sql = "UPDATE cuentas SET saldo=saldo-? WHERE numeroCuenta=?";

			PreparedStatement st = con.prepareStatement(sql);

			// Se pasan los parámetros al PreparedStatement

			st.setDouble(1, cantidad);
			st.setInt(2, num_cuenta);

			// Se ejecuta la consulta

			registros_afectados = st.executeUpdate();

			// Se crea una consulta para insertar el registro en la tabla de movimientos

			sql = "INSERT INTO movimientos (idCuenta, cantidad, fecha, operacion) VALUES (?,?,?,?)";

			st = con.prepareStatement(sql);

			// Se pasan los parámetros al PreparedStatement. La fecha será la actual del sistema

			st.setInt(1, num_cuenta);
			st.setDouble(2, cantidad);
			st.setDate(3, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			st.setString(4, "extracción");

			// Se ejecuta la consulta

			registros_afectados = st.executeUpdate();
            
            return registros_afectados;
			
		} catch (SQLException e) {

			e.printStackTrace();
			
			return registros_afectados;
		}
	}
	
	public int ingresar(double cantidad, int num_cuenta) {
		
		int registros_afectados = 0;
		
		// El try con recursos garantiza el cierre de la conexión
		
		try (Connection con = Datos.getConnection()) {
				
			// Se crea una consulta para actualizar la tabla de cuentas

			String sql = "UPDATE cuentas SET saldo=saldo+? WHERE numeroCuenta=?";

			PreparedStatement st = con.prepareStatement(sql);

			// Se pasan los parámetros al PreparedStatement

			st.setDouble(1, cantidad);
			st.setInt(2, num_cuenta);

			// Se ejecuta la consulta

			registros_afectados = st.executeUpdate();

			// Se crea una consulta para insertar el registro en la tabla de movimientos

			sql = "INSERT INTO movimientos (idCuenta, cantidad, fecha, operacion) VALUES (?,?,?,?)";

			st = con.prepareStatement(sql);

			// Se pasan los parámetros al PreparedStatement. La fecha será la actual del sistema

			st.setInt(1, num_cuenta);
			st.setDouble(2, cantidad);
			st.setDate(3, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			st.setString(4, "ingreso");

			// Se ejecuta la consulta

			registros_afectados = st.executeUpdate();
            
            return registros_afectados;
			
		} catch (SQLException e) {

			e.printStackTrace();
			
			return registros_afectados;
		}
	}
	
	public double saldo(int num_cuenta) {
		
		// El try con recursos garantiza el cierre de la conexión
		
		try (Connection con = Datos.getConnection()) {
			
			String sql = "SELECT * FROM cuentas WHERE numeroCuenta=?";
			
			PreparedStatement st = con.prepareStatement(sql);
			
			// Se pasa el parámetro al PreparedStatement
			
			st.setInt(1, num_cuenta);
			
			// Se obtine el resultset de la consulta
			
			ResultSet rs = st.executeQuery();
			
			// Si existe el registro
			
            if (rs.next()) {
            	
            	return rs.getDouble("saldo");
            }
			
		} catch (SQLException e) {

			e.printStackTrace();
			
			return 0;
		}
		
		return 0;
	}

	public List<Movimiento> ultimosMovimientos(int num_cuenta) {
		
		List<Movimiento> movimientos = new ArrayList<>();
		
		SortedMap<Date, List<Movimiento>> datos = new TreeMap<>();
		
		// Se agrupan los datos filtrados por cuenta con clave fecha (ordenada) y valor de lista de movimientos
		
		datos = leerMovimientosBD()
				     .filter(m -> m.getIdCuenta() == num_cuenta)
				     .collect(Collectors.groupingBy(m -> m.getFecha(), TreeMap::new, Collectors.toList()));
		
		if(datos.size() > 0) {
			
			// Se obtiene la última fecha del mapa ordenado

			Date fecha_ultima = datos.lastKey();

			// Se calcula la fecha de inicio y fin para un margen de 30 días

			LocalDate inicio = Utilidades.convertirALocalDate(fecha_ultima).minus(Period.ofDays(31));
			LocalDate fin = Utilidades.convertirALocalDate(fecha_ultima).plus(Period.ofDays(1));

			Date fecha_inicio = Utilidades.convertirADate(inicio);
			Date fecha_fin = Utilidades.convertirADate(fin);

			// Se obtiene la lista de fechas para los últimos 30 días

			List<Date> fechas = datos.keySet().stream()
					                             .filter(f -> f.after(fecha_inicio))
					                             .filter(f -> f.before(fecha_fin))
					                             .collect(Collectors.toList());
			
			// Se agregan las listas de movimientos al total
			
			for (Date f : fechas) {
				
				movimientos.addAll(datos.get(f));
			}
			
			// Se devuelve el resultado
			
			return movimientos;
		}
		
		// En este punto no habrá ningún movimiento recuperado
		
		return movimientos;
	}
}
