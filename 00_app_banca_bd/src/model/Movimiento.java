package model;

import java.util.Date;

public class Movimiento {
	
	private int idMovimiento;
	private int idCuenta;
	private Date fecha;
	private double cantidad;
	private String operacion;
	
	public Movimiento(int idMovimiento, int idCuenta, Date fecha, double cantidad, String operacion) {
		
		this.idMovimiento = idMovimiento;
		this.idCuenta = idCuenta;
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.operacion = operacion;
	}

	public Movimiento(int idCuenta, Date fecha, double cantidad, String operacion) {

		this.idCuenta = idCuenta;
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.operacion = operacion;
	}

	public int getIdMovimiento() {
		return idMovimiento;
	}

	public void setIdMovimiento(int idMovimiento) {
		this.idMovimiento = idMovimiento;
	}

	public int getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(int idCuenta) {
		this.idCuenta = idCuenta;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	@Override
	public String toString() {
		return "Movimiento [idMovimiento=" + idMovimiento + ", idCuenta=" + idCuenta + ", fecha=" + fecha
				+ ", cantidad=" + cantidad + ", operacion=" + operacion + "]";
	}
}
