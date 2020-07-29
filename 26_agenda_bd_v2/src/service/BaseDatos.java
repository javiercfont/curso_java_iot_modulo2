package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseDatos {

	// Lee el archivo de configuracion
	
	public abstract Connection leerConfiguracionBD(String ruta);
	
	// Obtiene la conexi�n
	
	public Connection getConexion(Conexion con) throws SQLException {
		
		// Carga el driver
		
		try {
			
			Class.forName(con.getDriver());
			
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		
		// Devuelve la conexi�n
		
		return DriverManager.getConnection(con.getCadenaConexion(), con.getUser(), con.getPassword());
	}
}
