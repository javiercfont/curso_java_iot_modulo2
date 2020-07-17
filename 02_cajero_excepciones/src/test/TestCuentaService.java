package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import exceptions.SaldoNegativoException;
import service.CuentaService;


public class TestCuentaService {
	
	CuentaService service;

	@Before
	public void setUp() throws Exception {
		
		service = new CuentaService(100);
	}

	@Test
	public void testIngresar() {
		
		service.ingresar(50);
		
		assertEquals(150, service.getSaldo(), 0);
	}

	@Test
	public void testExtraer() {
		
		try {
			service.extraer(50);
		} catch (SaldoNegativoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(50, service.getSaldo(), 0);
	}
}
