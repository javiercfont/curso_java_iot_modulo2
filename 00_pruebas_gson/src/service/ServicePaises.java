package service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Pais;

public class ServicePaises {
	
	String json;
	
	public ServicePaises() {
		json="[{\"country\":\"USA\",\"population\":249903450,\"capital\":\"washington\",\"temperature\":21.5,\"foundation\":1750,\"continent\":\"America\"},";
		json+="{\"country\":\"Canada\",\"population\":145290345,\"capital\":\"Ottawa\",\"temperature\":14.3,\"foundation\":1870,\"continent\":\"America\"},";
		json+="{\"country\":\"Spain\",\"population\":47345901,\"capital\":\"Madrid\",\"temperature\":24.7,\"foundation\":1520,\"continent\":\"Europe\"},";
		json+="{\"country\":\"Mexico\",\"population\":95000360,\"capital\":\"Mexico\",\"temperature\":26.2,\"foundation\":1880,\"continent\":\"America\"},";
		json+="{\"country\":\"Norway\",\"population\":31034000,\"capital\":\"Oslo\",\"temperature\":10.9,\"foundation\":1901,\"continent\":\"Europe\"},";
		json+="{\"country\":\"Germany\",\"population\":95456920,\"capital\":\"Berlin\",\"temperature\":18.0,\"foundation\":1830,\"continent\":\"Europe\"},";
		json+="{\"country\":\"Japan\",\"population\":110478934,\"capital\":\"Tokio\",\"temperature\":21.6,\"foundation\":1857,\"continent\":\"Asia\"},";
		json+="{\"country\":\"Russia\",\"population\":215678924,\"capital\":\"Moscow\",\"temperature\":11.3,\"foundation\":1802,\"continent\":\"Asia\"},";
		json+="{\"country\":\"France\",\"population\":76256702,\"capital\":\"Paris\",\"temperature\":19.4,\"foundation\":1670,\"continent\":\"Europe\"},";
		json+="{\"country\":\"United Kingdom\",\"population\":65023789,\"capital\":\"London\",\"temperature\":17.4,\"foundation\":1650,\"continent\":\"Europe\"}]";
	}
	
	private List<Pais> leerJson() {

		Gson gson = new Gson();
		
		Type tipoListaPaises = new TypeToken<List<Pais>>(){}.getType();
		
		return gson.fromJson(this.json, tipoListaPaises);
	}
	
	public int paisesMasHabitantesValor(long habitantes) {
		
		return (int) leerJson()
			           .stream()
			           .filter(p -> p.getPopulation() > habitantes)
			           .count();
	}
	
	public double temperaturaMediaPaises() {
		
		return leerJson()
				.stream()
				.mapToDouble(p -> p.getTemperature())
				.average()
				.getAsDouble();
	}
	
	public boolean algunPaisCondicion(long habitantesMax, long yearMenor) {
		
		return leerJson()
				.stream()
				.anyMatch(p -> p.getPopulation() > habitantesMax && p.getFoundation() < yearMenor);		
	}
	
	public Pais paisMasPoblado() {
		
		return leerJson()
				.stream()
				.max((p1, p2) -> (int) (p1.getPopulation() - p2.getPopulation()))
				.orElse(null);
	}
	
	public List<Pais> paisesFundacionAnterior(long year) {
		
		return leerJson()
				.stream()
				.filter(p -> p.getFoundation() < year)
				.collect(Collectors.toList());
	}
	
	public String continenteConMasPaises() {
		
		Map<String,List<Pais>> agrupacion = paisesPorContinente();
		
		return agrupacion.keySet().stream()
				.max((k1, k2) -> agrupacion.get(k1).size() - agrupacion.get(k2).size())
				.get();
	}
	
	public String continenteConMasPoblacion() {
		
		Map<String,List<Pais>> agrupacion = paisesPorContinente();
		
		return agrupacion.keySet().stream()
				.max((k1, k2) -> (int) (agrupacion.get(k1).stream().mapToLong(p -> p.getPopulation()).sum() - 
						                agrupacion.get(k2).stream().mapToLong(p -> p.getPopulation()).sum()))
				.get();
	}
	
	public Map<String,List<Pais>> paisesPorContinente() {
		
		return leerJson()
				.stream()
				.collect(Collectors.groupingBy(p -> p.getContinent()));
	}
}
