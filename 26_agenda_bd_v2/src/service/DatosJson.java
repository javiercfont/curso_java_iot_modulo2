package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class DatosJson extends BaseDatos {
	
	private Path path;

	@Override
	public Connection leerConfiguracionBD(String ruta) {
		
		path = Paths.get(ruta);
		
		// Si no existiera el archivo, se crea
		
		if (!Files.exists(path)) {
			
			try {
				
				Files.createFile(path);
				
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		
		try (Stream<String> st = Files.lines(path)) {
			
			// Se crea objeto de librería Gson
			
			Gson gson = new Gson();
			
			// Se crea una cadena con todos los datos del stream
			
			String json = st.collect(Collectors.joining());
			
			// Se devuelve un objeto Connection con los datos del JSON
			
			return getConexion(gson.fromJson(json, Conexion.class));
		
		} catch (JsonSyntaxException | IOException e) {
			
			e.printStackTrace();
			
			return null;
			
		} catch (SQLException e) {

			e.printStackTrace();
			
			return null;
		}	
	}
	

}
