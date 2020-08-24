package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import model.DatoCovid;
import util.Utilidades;

public class JsonService extends BaseService {

	private Path path;
	
	public JsonService(String ruta) {
		
		path = Paths.get(ruta);
	}
	
	@Override
	public Stream<DatoCovid> streamDato() {
		
		// Si existe el archivo de datos
		
		if (Files.exists(path)) {
			
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
}
