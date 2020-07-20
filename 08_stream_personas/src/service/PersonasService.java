package service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Persona;

public class PersonasService {
	
	List<Persona> personas=new ArrayList<>();
	
	public PersonasService() {
		
		personas.add(new Persona("Alex","al@gg.com",20));				
		personas.add(new Persona("Juan","alfd@gg.com",29));
		personas.add(new Persona("Elena","al@gg.es",17));
		personas.add(new Persona("Marta","alaaoi@gg.com",34));
		personas.add(new Persona("Lucas","alert@gg.es",44));
		personas.add(new Persona("Alicia","sdfl@gg.com",35));
	}
	
	//persona más joven
	public Persona obtenerMasJoven() {
		
		return personas
				.stream()
				.max((p1, p2) -> p2.getEdad() - p1.getEdad())
				.get();
	}
	
	//el número de personas cuya edad es superior a la media
	public int superiorMedia() {
		
		double media = personas
							.stream()
							.mapToDouble(p -> p.getEdad())
							.average()
							.orElse(0);
		
		
		return (int) personas
						.stream()
						.filter(p -> p.getEdad() > media)
						.count();
	}
	
	//personas ordenadas por edad
	public List<Persona> ordenadasPorEdad(){
		
		return personas
				.stream()
				.sorted((p1, p2) -> p1.getEdad() - p2.getEdad())
				.collect(Collectors.toList());
		
	}

	//lista nombres de personas
	public List<String> nombres(){
		
		List<String> nombres = new ArrayList<>();
		
		personas
			.stream()
			.forEach(p -> nombres.add(p.getNombre()));
		
		return nombres;
		
	}
	
	
	//dominio sea igual al indicado
	public List<Persona> personasDominio(String dominio){
		
		return personas
				.stream()
				.filter(p -> p.getEmail().endsWith(dominio))
				.collect(Collectors.toList());
	}
	
}

