package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.DatoCovid;

public abstract class BaseService {
	
	// Representa la recuperación de datos de un archivo en forma de Stream
	
	public abstract Stream<DatoCovid> streamDato();
	
	// Vuelca el contenido de la BD a memoria
	
	private Stream<DatoCovid> leerDatosBD() {
		
		// El try con recursos garantiza el cierre de la conexión
		
		try (Connection con = Datos.getConnection()) {
			
			String sql = "SELECT * FROM datos_covid";
			
			PreparedStatement st = con.prepareStatement(sql);
			
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
	
	// Se vuelcan los datos a la BD y se devuelve el número de registros insertados
	
	public int volcarDatosABD(List<DatoCovid> datos) {
		
		int registros_insertados = 0;
		long registros_encontrados = 0;
		
		// Se pasan todos los datos de la BD a memoria
		
		Stream<DatoCovid> datos_bd = leerDatosBD();
		
		// Se pasan todos los datos a una lista para evitar el consumo del Stream por cada consulta
		
		List<DatoCovid> lista_datos_bd = datos_bd.collect(Collectors.toList());
		
		// El try con recursos garantiza el cierre de la conexión
		
		try (Connection con = Datos.getConnection()) {
			
			String sql = "INSERT INTO datos_covid(ccaa_iso, "
					+ "fecha, num_casos, num_casos_prueba_pcr, "
					+ "num_casos_prueba_test_ac, num_casos_prueba_otras,"
					+ "num_casos_prueba_desconocida) VALUES(?,?,?,?,?,?,?)";
			
			PreparedStatement st = con.prepareStatement(sql);
			
			// Por cada pedido, lo insertamos en la BD
			
			for (DatoCovid d : datos) {
				
				// Se buscan registros que contengan la misma CA y fecha. parallelStream aumenta rendimiento ?
				
				registros_encontrados = lista_datos_bd.parallelStream()
						                   .filter(dbd -> dbd.getCcaa_iso().equals(d.getCcaa_iso()))
						                   .filter(dbd -> dbd.getFecha().equals(d.getFecha()))
						                   .count();
				
				// Si no se encuentra el registro, se inserta
				
				if (registros_encontrados == 0) {
					
					// La fecha hay que pasarla en sql.Date
	
					st.setString(1, d.getCcaa_iso());
					st.setDate(2, new java.sql.Date(d.getFecha().getTime()));
					st.setLong(3, d.getNum_casos());
					st.setLong(4, d.getNum_casos_prueba_pcr());
					st.setLong(5, d.getNum_casos_prueba_test_ac());
					st.setLong(6, d.getNum_casos_prueba_otras());
					st.setLong(7, d.getNum_casos_prueba_desconocida());
					
					// Se ejecuta la consulta y
					// se actualiza el total de registros insertados
				
					registros_insertados += st.executeUpdate();
				}
			}
			
			return registros_insertados;
			
		} catch (SQLException e) {

			e.printStackTrace();
			
			return registros_insertados;
		}
	}
}
