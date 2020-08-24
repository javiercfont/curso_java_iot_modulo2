package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.DatoCovid;
import util.Utilidades;

public class CsvService extends BaseService {

	private Path path;
	
	public CsvService(String ruta) {
		
		path = Paths.get(ruta);
	}
	
	@Override
	public Stream<DatoCovid> streamDato() {
		
		// Si existe el archivo de datos
		
		if (Files.exists(path)) {
		
			// Obtenemos el Stream haciendo skip de la primera línea (cabecera CSV)
			
			try (Stream<String> st = Files.lines(path).skip(1)) {
				
				return st
						.map(s -> Utilidades.cadenaADatoCovid(s, Utilidades.SEPARADOR_DATOS))
						.collect(Collectors.toList())
						.stream();
				
			} catch (IOException e) {
	
				e.printStackTrace();
				
				return Stream.empty();
			}
			
		} else {
			
			return Stream.empty();
		}
	}
}
