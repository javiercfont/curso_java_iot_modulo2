package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Contacto;

public class AgendaService {
	
	// Ruta del archivo JSON de configuracion
	
	private final String RUTA_JSON = "bd_config\\bd_config.json";
	
	// Inserta un contacto
	
	public boolean insertarContacto(Contacto c) {
		
		// El try con recursos garantiza el cierre de la conexión
		
		try (Connection con = new DatosJson().leerConfiguracionBD(RUTA_JSON)) {

			// Envío de instrucciones SQL
			
			/*String sql="INSERT INTO contactos(nombre,email,edad) VALUES('"+c.getNombre()+"','"+c.getEmail()+"',"+c.getEdad()+")";
			
			Statement st = con.createStatement();
			
			st.execute(sql);*/
			
			// Opción usando preparedStatement
			
			String sql = "INSERT INTO contactos(nombre, email, edad) VALUES(?,?,?)";
			
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setString(1, c.getNombre());
			st.setString(2, c.getEmail());
			st.setInt(3, c.getEdad());
			
			return st.execute();
		
		} catch (SQLException e) {

			e.printStackTrace();
			
			return true;
		}
	}
	
	public boolean eliminarContacto(String email) {
	
		// El try con recursos garantiza el cierre de la conexión
		
		try (Connection con = new DatosJson().leerConfiguracionBD(RUTA_JSON)) {
			
			String sql = "DELETE FROM contactos WHERE email=?";
			
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setString(1, email);
			
			return st.executeUpdate() > 0;
			
		} catch (SQLException e) {

			e.printStackTrace();
			
			return false;
		}
	}
	
	public Contacto buscarContacto(String email) {
		
		// El try con recursos garantiza el cierre de la conexión
		
		try (Connection con = new DatosJson().leerConfiguracionBD(RUTA_JSON)) {
			
			String sql = "SELECT * FROM contactos WHERE email=?";
			
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setString(1, email);
			
			ResultSet rs = st.executeQuery();
			
			if (rs.next()) {
				
			    return new Contacto(rs.getInt("idcontacto"),rs.getString("nombre"), rs.getString("email"), rs.getInt("edad"));
			    
			} else {
				
				return null;
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
			
			return null;
		}
	}
	
	public List<Contacto> leerContactos() {

		// El try con recursos garantiza el cierre de la conexión
		
		try (Connection con = new DatosJson().leerConfiguracionBD(RUTA_JSON)) {
			
			List<Contacto> contactos = new ArrayList<>();
			
			String sql = "SELECT * FROM contactos";
			
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
				
				contactos.add(new Contacto(rs.getInt("idcontacto"),rs.getString("nombre"), rs.getString("email"), rs.getInt("edad")));
			}
			
			return contactos;
			
		} catch (SQLException e) {

			e.printStackTrace();
			
			return null;
		}
	}
}
