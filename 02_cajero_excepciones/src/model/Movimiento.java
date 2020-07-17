package model;

public class Movimiento {
	
	private double cantidad;
	private String tipoOperacion;
	
	public Movimiento() {
		
	}
	
	public Movimiento(double cantidad, String tipoOperacion) {
		super();
		this.cantidad = cantidad;
		this.tipoOperacion = tipoOperacion;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	@Override
	public String toString() {
		return "Movimiento [cantidad = " + cantidad + ", operación = " + tipoOperacion + "]";
	}
}
