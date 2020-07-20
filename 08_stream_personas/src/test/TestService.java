package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.Persona;
import service.PersonasService;

public class TestService {

	PersonasService ps = new PersonasService();
	
	@Test
	public void testMasJoven() {
		
		Persona p = new Persona("Elena","al@gg.es",17);
		
		assertEquals(p, ps.obtenerMasJoven());
	}
	
	@Test
	public void testSuperiorMedia() {
		
		assertEquals(3, ps.superiorMedia());
	}
	
	@Test
	public void testOrdenadasEdad() {
		
		List<Persona> personas=new ArrayList<>();
		
		personas.add(new Persona("Alex","al@gg.com",20));				
		personas.add(new Persona("Juan","alfd@gg.com",29));
		personas.add(new Persona("Elena","al@gg.es",17));
		personas.add(new Persona("Marta","alaaoi@gg.com",34));
		personas.add(new Persona("Lucas","alert@gg.es",44));
		personas.add(new Persona("Alicia","sdfl@gg.com",35));
		
		personas.sort((p1, p2) -> p1.getEdad() - p2.getEdad());
		
		assertEquals(personas, ps.ordenadasPorEdad());
	}
	
	@Test
	public void testDominio() {
		
		List<Persona> personas=new ArrayList<>();
		
		personas.add(new Persona("Alex","al@gg.com",20));				
		personas.add(new Persona("Juan","alfd@gg.com",29));
		personas.add(new Persona("Marta","alaaoi@gg.com",34));
		personas.add(new Persona("Alicia","sdfl@gg.com",35));
		
		assertEquals(personas, ps.personasDominio(".com"));
	}
	
	@Test
	public void testNombres() {
		
		List<String> nombres = new ArrayList<>();
		
		nombres.add("Alex");
		nombres.add("Juan");
		nombres.add("Elena");
		nombres.add("Marta");
		nombres.add("Lucas");
		nombres.add("Alicia");
		
		assertEquals(nombres, ps.nombres());
	}
}
