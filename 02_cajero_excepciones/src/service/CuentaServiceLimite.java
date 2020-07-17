package service;

import exceptions.SaldoNegativoException;

public class CuentaServiceLimite extends CuentaService {
	
	private double limite; 

	public CuentaServiceLimite() {
		
		this.limite = 1_200.0;
	}
	
	public CuentaServiceLimite(double saldo, double limite) {
		
		super(saldo);
		this.limite = limite;
	}

	@Override
	public double ingresar(double cantidad) {
		
		if (cantidad <= limite) {
			
			super.ingresar(cantidad);
			
			return cantidad;
			
		} else {
			
			super.ingresar(limite);
			
			return limite;
		}
	}

	@Override
	public double extraer(double cantidad) throws SaldoNegativoException {
		
		if (cantidad <= limite) {
			
			super.extraer(cantidad);
			
			return cantidad;
			
		} else {
			
			super.extraer(limite);
			
			return limite;
		}
	}

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}
}
