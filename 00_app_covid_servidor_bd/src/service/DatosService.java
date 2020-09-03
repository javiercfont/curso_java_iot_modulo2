package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.DatoCovid;

public class DatosService {
	
	private String nombre_ca;

	public DatosService(String nombre_ca) {
		
		this.nombre_ca = nombre_ca;
	}

	// Vuelca el contenido de la BD para una CA, a memoria
	
	private Stream<DatoCovid> leerDatosBD() {
		
		// El try con recursos garantiza el cierre de la conexión
		
		try (Connection con = Datos.getConnection()) {
			
			String sql = "SELECT * FROM datos_covid WHERE ccaa_iso=?";
			
			PreparedStatement st = con.prepareStatement(sql);
			
			// Se inserta la CA en el PreparedStatement
			
			st.setString(1, nombre_ca);
			
			// Se obtine el resultset de la consulta
			
			ResultSet rs = st.executeQuery();
			
			// Se genera un Stream personalizado con los datos
			
			Stream.Builder<DatoCovid> builder = Stream.builder();
			
	        while (rs.next()) {
	        	
	            DatoCovid dato = new DatoCovid(rs.getInt("idDato"),
	            		                       rs.getString("ccaa_iso"),
	            		                       rs.getDate("fecha"),
	            		                       rs.getLong("num_casos"),
	            		                       rs.getLong("num_casos_prueba_pcr"),
	            		                       rs.getLong("num_casos_prueba_test_ac"),
	            		                       rs.getLong("num_casos_prueba_otras"),
	            		                       rs.getLong("num_casos_prueba_desconocida"));
	            
	            builder.add(dato);
	        }
	        
			// Devuelve un Stream de datos
			
            return builder.build();
			
		} catch (SQLException e) {

			e.printStackTrace();
			
			return Stream.empty();
		}
	}
	
	// Total de positivos de una CA
	
	public long totalPositivos() {
		
		// Se recuperan los datos de la BD para la CA
		// Se mapean a long
		// Se suman
		
		return leerDatosBD()
				   .mapToLong(d -> d.getNum_casos())
				   .sum();
	}
	
	// Media de positivos diarios de una CA
	
	public double mediaPositivosDiarios() {
		
		// Mapa con las listas de casos por fecha
		
		Map<Date, List<DatoCovid>> listaCasosPorFecha = leerDatosBD()
                                                        .collect(Collectors.groupingBy(DatoCovid::getFecha));
		
		// Se obtienen las listas de casos por fechas
		// Se calcula la media de la suma total de casos de cada lista
		
		return listaCasosPorFecha.values().stream()
				                              .collect(Collectors.averagingDouble(l -> l.stream()
				        		                                                           .mapToDouble(d -> d.getNum_casos())
				        		                                                           .sum()));
	}
	
	// Fecha del pico de contagios de una CA
	
	public Date fechaPicoContagios() {
		
		// Se agrupan los datos por fecha (clave)
		// y se suman todos los casos de esa fecha (valor)
		
		Map<Date, Long> totalPorFecha = leerDatosBD()
		                                     .collect(Collectors.groupingBy(d -> d.getFecha(), 
		                                    		  Collectors.summingLong(d -> d.getNum_casos())));
		
		// Se busca el máximo de casos que hay en el mapa de fechas
		// Se recupera este objeto
		
		return totalPorFecha.keySet().stream()
				                         .max((d1, d2) -> (int) (totalPorFecha.get(d1) - totalPorFecha.get(d2)))
				                         .get();
	}
}
