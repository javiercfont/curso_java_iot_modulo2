package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import exceptions.SaldoNegativoException;
import model.Movimiento;
import service.CuentaServiceMovimiento;

public class TestCuentaServiceMovimiento {
	
	CuentaServiceMovimiento cuenta;

	@Before
	public void setUp() throws Exception {
		
		cuenta = new CuentaServiceMovimiento(2000, 300);
	}

	@Test
	public void testObtenerMovimientos() {
		
		ArrayList<Movimiento> operaciones = new ArrayList<>();
		
		cuenta.ingresar(100);
		cuenta.ingresar(100);
		cuenta.ingresar(100);
		
		try {
			cuenta.extraer(50);
		} catch (SaldoNegativoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cuenta.extraer(50);
		} catch (SaldoNegativoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		operaciones = cuenta.obtenerMovimientos();
	}
	
	@Test
	public void testIngreso() {
		
		cuenta.ingresar(100);
		
		assertEquals(2100, cuenta.getSaldo(), 0);
	}

	@Test
	public void testExtracto() {
		
		try {
			cuenta.extraer(100);
		} catch (SaldoNegativoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(1900, cuenta.getSaldo(), 0);
	}
}
