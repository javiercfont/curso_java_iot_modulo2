package service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import model.DatoPais;
import model.Pais;

public class PaisesService {
	
	// Fichero de datos
	
	static final String FICHERO_DATOS="data/paises.json";
	
	// Lee el archivo de datos y contruye el Stream de objetos DatoPais
	
	private Stream<DatoPais> leerDatosJson() {
		
		try {
			
			// Datos a recuperar
			
			String nombre;
			String capital;
			long area;
			long poblacion;
			String alpha2Code;
			String region;
			double[] posicion;
			
			// Se instancia el objeto Gson para recuperar los valores
			
			Gson gson = new GsonBuilder().create();
			
			// Se crea el JsonArray del fichero
			
			JsonArray json_data = gson.fromJson(new FileReader(FICHERO_DATOS, StandardCharsets.UTF_8), JsonArray.class);
			
			// En esta lista guardamos los datos recuperados según el modelo
			
			List<DatoPais> lista_datos = new ArrayList<>();
			
			for (JsonElement jse : json_data) {
					
				nombre = gson.fromJson(jse.getAsJsonObject().get("name"), String.class);
				capital = gson.fromJson(jse.getAsJsonObject().get("capital"), String.class);

				// Se han observado campos null en area. Esto produce NullPointerException
				
				if(!jse.getAsJsonObject().get("area").isJsonNull()) {

					area = gson.fromJson(jse.getAsJsonObject().get("area"), Long.class);

				} else {

					area = 0;
				}

				poblacion = gson.fromJson(jse.getAsJsonObject().get("population"), Long.class);
				alpha2Code = gson.fromJson(jse.getAsJsonObject().get("alpha2Code"), String.class);
				region = gson.fromJson(jse.getAsJsonObject().get("region"), String.class);
				posicion = gson.fromJson(jse.getAsJsonObject().get("latlng"), double[].class);

				
				// Se construye el objeto DatoPais con los datos leídos y se añade a a lista
				
				lista_datos.add(new DatoPais(nombre,
						                     capital,
						                     area,
						                     poblacion,
						                     alpha2Code,
						                     region,
						                     posicion));
			}
			
			return lista_datos.stream();
			
		} catch (IOException | JsonSyntaxException | JsonIOException e) {
			
			e.printStackTrace();
			
			return Stream.empty();
		}
	}
	
	// Listado de países dada una región
	
	public List<Pais> paisesEnRegion(String region) {
		
		return leerDatosJson()
				    .filter(d -> d.getRegion().equalsIgnoreCase(region))
				    .collect(Collectors.toList());
	}
	
	// País más poblado
	
	public Pais paisMasPoblado() {
		
		return leerDatosJson()
				     .max((p1, p2) -> (int) (p1.getPoblacion() - p2.getPoblacion()))
				     .get();
	}
	
	// Países que superan la población dada
	
	public List<Pais> paisesPoblacionSuperior(long minimo) {
		
		return leerDatosJson()
				     .filter(p -> p.getPoblacion() > minimo)
				     .collect(Collectors.toList());
	}
	
	// Países con combinación de letras dada
	
	public List<Pais> paisesPorLetras(String letras) {
		
		return leerDatosJson()
				     .filter(p -> p.getNombre().toLowerCase().contains(letras.toLowerCase()))
				     .collect(Collectors.toList());
	}
	
	// Posición del país dado
	
	public double[] posicionPais(String alpha2Code) {
		
		return leerDatosJson()
				     .filter(d -> d.getAlpha2Code().equalsIgnoreCase(alpha2Code))
				     .findFirst()
				     .get().getPosicion();
	}
	
	// Población media de la región dada
	
	public double poblacionMedia(String region) {
		
		return leerDatosJson()
		             .filter(p -> p.getRegion().equalsIgnoreCase(region))
		             .collect(Collectors.averagingDouble(p -> p.getPoblacion()));
	}
	
	// Tabla de las regiones y su población total
	
	public Map<String, Long> regionesYPoblacion() {
		
		return leerDatosJson()
		           .collect(Collectors.groupingBy(p -> p.getRegion(), Collectors.summingLong(p -> p.getPoblacion())));
	}
}
