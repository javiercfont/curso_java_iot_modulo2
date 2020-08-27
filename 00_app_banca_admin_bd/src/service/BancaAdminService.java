package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Cliente;
import model.Cuenta;
import model.Titular;

public class BancaAdminService {
	
	// Comprueba si existe una cuenta
	
	private boolean comprobarCuenta(int num_cuenta) {
		
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
	
	// Comprueba si existe una cuenta
	
	private boolean comprobarCliente(int dni) {
		
		// El try con recursos garantiza el cierre de la conexión
		
		try (Connection con = Datos.getConnection()) {
			
			String sql = "SELECT * FROM clientes WHERE dni=?";
			
			PreparedStatement st = con.prepareStatement(sql);
			
			// Se pasa el parámetro al PreparedStatement
			
			st.setInt(1, dni);
			
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
	
	// Comprueba si existe un titular
	
	private boolean comprobarTitular(Titular titular) {
		
		// El try con recursos garantiza el cierre de la conexión
		
		try (Connection con = Datos.getConnection()) {
			
			String sql = "SELECT * FROM titulares WHERE idCuenta=? AND idCliente=?";
			
			PreparedStatement st = con.prepareStatement(sql);
			
			// Se pasa el parámetro al PreparedStatement
			
			st.setInt(1, titular.getIdCuenta());
			st.setInt(2, titular.getIdCliente());
			
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
	
	// Da de alta una cuenta
	
	public boolean altaCuenta(Cuenta cuenta) {
		
		// Si no existe la cuenta
		
		if ((cuenta != null) && (!comprobarCuenta(cuenta.getNumeroCuenta()))) {
			
			// El try con recursos garantiza el cierre de la conexión
			
			try (Connection con = Datos.getConnection()) {
				
				String sql = "INSERT INTO cuentas (numeroCuenta, saldo, tipocuenta) VALUES (?,?,?)";
				
				PreparedStatement st = con.prepareStatement(sql);
				
				// Se pasa el parámetro al PreparedStatement
				
				st.setInt(1, cuenta.getNumeroCuenta());
				st.setDouble(2, cuenta.getSaldo());
				st.setString(3, cuenta.getTipocuenta());
				
				// Se devuelve el resultado
				
				return st.executeUpdate() > 0;
				
			} catch (SQLException e) {

				e.printStackTrace();
				
				return false;
			}
		}
		
		return false;
	}

	// Da de alta un cliente
	
	public boolean altaCliente(Cliente cliente) {
		
		// Si no existe la cuenta
		
		if ((cliente != null) && (!comprobarCliente(cliente.getDni()))) {
			
			// El try con recursos garantiza el cierre de la conexión
			
			try (Connection con = Datos.getConnection()) {
				
				String sql = "INSERT INTO clientes (dni, nombre, direccion, telefono) VALUES (?,?,?,?)";
				
				PreparedStatement st = con.prepareStatement(sql);
				
				// Se pasa el parámetro al PreparedStatement
				
				st.setInt(1, cliente.getDni());
				st.setString(2, cliente.getNombre());
				st.setString(3, cliente.getDireccion());
				st.setInt(4, cliente.getTelefono());
				
				// Se devuelve el resultado
				
				return st.executeUpdate() > 0;
				
			} catch (SQLException e) {

				e.printStackTrace();
				
				return false;
			}
		}
		
		return false;
	}
	
	// Da de alta un titular
	
	public boolean altaTitular(Titular titular) {
		
		// Si no existe la cuenta
		
		if ((titular != null) && (!comprobarTitular(titular))) {
			
			// El try con recursos garantiza el cierre de la conexión
			
			try (Connection con = Datos.getConnection()) {
				
				String sql = "INSERT INTO titulares (idCuenta, idCliente) VALUES (?,?)";
				
				PreparedStatement st = con.prepareStatement(sql);
				
				// Se pasa el parámetro al PreparedStatement
				
				st.setInt(1, titular.getIdCuenta());
				st.setInt(2, titular.getIdCliente());
				
				// Se devuelve el resultado
				
				return st.executeUpdate() > 0;
				
			} catch (SQLException e) {

				e.printStackTrace();
				
				return false;
			}
		}
		
		return false;
	}
}