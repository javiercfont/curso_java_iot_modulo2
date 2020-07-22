package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import service.ServicePaises;

public class TestPaises {
	
	ServicePaises ps;
	
	@Before
	public void setUp() throws Exception {
		
		ps = new ServicePaises();
	}

	@Test
	public void testContinenteConMasPaises() {
		
		assertEquals(ps.continenteConMasPaises(), "Europe");

	}

	@Test
	public void testPaisMasPoblado() {
		
		assertEquals(ps.paisMasPoblado().getCountry(), "USA");
	}
}
