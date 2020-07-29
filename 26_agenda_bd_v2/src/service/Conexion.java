package service;

public class Conexion {
	
	private String driver;
	private String cadenaConexion;
	private String user;
	private String password;
	
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getCadenaConexion() {
		return cadenaConexion;
	}
	public void setCadenaConexion(String cadenaConexion) {
		this.cadenaConexion = cadenaConexion;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
