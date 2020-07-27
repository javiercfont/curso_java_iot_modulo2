package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import model.DatoCovid;
import util.Utilidades;

public class AppCovidService {
	
	private Path path;
	
	private String tipoArchivo;
	
	// Rutas de los archivos de datos
	
	private final String archivoDatosCSV = "data\\datos_5_7_2020.csv";
	private final String archivoDatosJSON = "data\\datos_ccaas.json";
	
	// Tipos de archivo admitidos
	
	public static final String CSV = "CSV";
	public static final String JSON = "JSON";
	
	// Separador utilizado en los datos
	
	private final String SEPARADOR_DATOS = ",";
	
	// Constructor
	
	public AppCovidService(String tipoArchivo) {
		
		// Se inicializa el servicio teniendo en cuenta el tipo de archivo
		
		this.tipoArchivo = tipoArchivo;
		
		switch (tipoArchivo) {
		
			case CSV:
				
				path = Paths.get(archivoDatosCSV);
				tipoArchivo = CSV;
				break;
				
			case JSON:
				
				path = Paths.get(archivoDatosJSON);
				tipoArchivo = JSON;
				break;
				
			default:
				
				break;
		}
		
		// Si no existiera el archivo, se crea
		
		if (!Files.exists(path)) {
			
			try {
				
				Files.createFile(path);
				
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
	
	// Devuelve todos los datos almacenados
	
	private Stream<DatoCovid> leerDatos() {
	
		if (tipoArchivo.equals(CSV)) {
			
			// Obtenemos el Stream haciendo skip de la primera línea (cabecera CSV)
			
			try (Stream<String> st = Files.lines(path).skip(1)) {
				
				return st
						.map(s -> Utilidades.cadenaADatoCovid(s, SEPARADOR_DATOS))
						.collect(Collectors.toList())
						.stream();
				
			} catch (IOException e) {
	
				e.printStackTrace();
				
				return Stream.empty();
			}
			
		} else if (tipoArchivo.equals(JSON)) {

			try (Stream<String> st = Files.lines(path)) {
				
				// Se crea el Gson con la opción del formato de fecha que tenemos en los datos
				
				Gson gson = new GsonBuilder().setDateFormat(Utilidades.FORMATO_FECHA).create();
				
				// Se crea una cadena con todos los datos del stream
				
				String json = st.collect(Collectors.joining());
				
				// Se crea un array DatoCovid usando un método de la librería Gson
				
				DatoCovid[] datosCovid = gson.fromJson(json, DatoCovid[].class);
				
				// Se devuelve el array transformado a Stream
				
				return Arrays.stream(datosCovid);
				
			} catch (JsonSyntaxException | IOException e) {
				
				e.printStackTrace();
				
				return Stream.empty();
			}
			
		} else {
			
			return Stream.empty();
		}
	}
	
	// Lista de casos producidos entre dos fechas
	
	public List<DatoCovid> casosEntreFechas(Date f1, Date f2) {
		
		// Se filtran los datos entre las fechas
		// Se transforman los datos en una lista
		
		return leerDatos()
		         .filter(d -> d.getFecha().after(f1))
		         .filter(d -> d.getFecha().before(f2))
		         .collect(Collectors.toList());
	}

	// Total de positivos producidos en un día
	
	public long casosEnUnDia(Date f) {
		
		// Se filtran los datos de una fecha determinada
		// Se suman todos los casos resultantes
		
		return leerDatos()
		         .filter(d -> d.getFecha().equals(f))
		         .collect(Collectors.summingLong(d -> d.getNum_casos()));
	}
	
	// Fecha del pico de contagios
	
	public Date fechaPicoContagios() {
		
		// Se agrupan los datos por fecha (clave)
		// y se suman todos los casos de esa fecha (valor)
		
		Map<Date, Long> totalPorFecha = leerDatos()
		                                     .collect(Collectors.groupingBy(d -> d.getFecha(), 
		                                    		  Collectors.summingLong(d -> d.getNum_casos())));
		
		
		// Se busca el máximo de casos que hay en el mapa de fechas
		// Se recupera este objeto
		
		return totalPorFecha.keySet().stream()
				                         .max((d1, d2) -> (int) (totalPorFecha.get(d1) - totalPorFecha.get(d2)))
				                         .get();
	}
	
	// Media de positivos en un día
	
	public double mediaPositivosEnUnDia(Date f) {
		
		// Se filtra el día del que queremos obtener la media
		// Se hace una media del número de casos de ese día
		
		return leerDatos()
		           .filter(d -> d.getFecha().equals(f))
		           .collect(Collectors.averagingDouble(d -> d.getNum_casos()));
	}
	
	// Media de positivos por día
	
	public Map<Date, Double> mediaPositivosDiarios() {
		
		// Se agrupan en un mapa con clave de la fecha de cada caso y valor de la media de casos de esa fecha
		
		return leerDatos()
		         .collect(Collectors.groupingBy(c -> c.getFecha(), 
		            		                    Collectors.averagingDouble(c -> c.getNum_casos())));
	}
	
	// Total positivos de una comunidad
	
	public long totalPositivosComunidad(String codigo_ca) {
		
		// Se filtran los datos para el código de la comunidad
		// Se suma el número de casos resultantes
		
		return leerDatos()
		          .filter(d -> d.getCcaa_iso().equals(codigo_ca))
		          .collect(Collectors.summingLong(d -> d.getNum_casos()));
	}
	
	// Total positivos por cada comunidad
	
	public Map<String, Long> totalPositivosPorComunidad() {
		
		// Se agrupan en un mapa con clave del nombre ISO de la comunidad y suma de los casos en dicha comunidad
		
		return leerDatos()
		      .collect(Collectors.groupingBy(c -> c.getCcaa_iso(), 
		    		                            Collectors.summingLong(c -> c.getNum_casos())));
	}
	
	// Tabla con listas de casos agrupados por comunidad
	
	public Map<String, List<DatoCovid>> tablaListaCasosPorComunidad() {
		
		// Se agrupan en un mapa con clave del nombre ISO de la comunidad y lista de objetos DatoCovid
		
		return leerDatos()
				.collect(Collectors.groupingBy(c -> c.getCcaa_iso()));
	}
}
