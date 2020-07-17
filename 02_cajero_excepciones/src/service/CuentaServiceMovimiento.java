package service;

import java.util.ArrayList;

import exceptions.SaldoNegativoException;
import model.Movimiento;

public class CuentaServiceMovimiento extends CuentaServiceLimite {
	
	private String ingreso = "Ingreso";
	private String extracto = "Extracto";
	
	private ArrayList<Movimiento> movimientos = new ArrayList<>();
	
	public CuentaServiceMovimiento(double saldo, double limite) {
		
		super(saldo, limite);
	}

	public ArrayList<Movimiento> obtenerMovimientos() {
		
		return this.movimientos;
	}

	@Override
	public double ingresar(double cantidad) {
		
		double resultado = super.ingresar(cantidad);
		
		Movimiento operacion = new Movimiento(resultado, ingreso);
		
		movimientos.add(operacion);
		
		return resultado;
	}

	@Override
	public double extraer(double cantidad) throws SaldoNegativoException {

		double resultado = super.extraer(cantidad);
		
		Movimiento operacion = new Movimiento(resultado, extracto);
		
		movimientos.add(operacion);
		
		return resultado;
	}
}
