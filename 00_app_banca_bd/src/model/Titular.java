package model;

public class Titular {
	
	private int idCuenta;
	private int idCliente;
	
	public Titular(int idCuenta, int idCliente) {
		
		this.idCuenta = idCuenta;
		this.idCliente = idCliente;
	}

	public int getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(int idCuenta) {
		this.idCuenta = idCuenta;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	@Override
	public String toString() {
		return "Titular [idCuenta=" + idCuenta + ", idCliente=" + idCliente + "]";
	}
}
