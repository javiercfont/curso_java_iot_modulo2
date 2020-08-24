package com.jcf.principal;

import java.util.Scanner;
import java.util.stream.Collectors;

import service.BaseService;
import service.CsvService;
import service.JsonService;

public class AppCovid_bd {

	private static Scanner sc;
	private static BaseService bs;
	
	private static void seleccionarArchivoDatos() {

		System.out.println("Introduce la ruta del archivo de datos:");
		
		sc = new Scanner(System.in);
		
		String ruta_archivo = sc.nextLine();
		
        if (ruta_archivo.endsWith(".csv")) {

        	// Se instancia el servicio para archivo de datos CSV

        	bs = new CsvService(ruta_archivo);  
        }

        if (ruta_archivo.endsWith(".json")) {
				
        	// Se instancia el servicio para archivo de datos JSON

        	bs = new JsonService(ruta_archivo);
        }
	}


	public static void main(String[] args) {
		
		// Preguntamos por la ruta del archivo de datos
		// hasta que se inicialice el servicio
		
        do {
        	
        	seleccionarArchivoDatos();
        	
        } while (bs == null);
        
        // Volcamos los datos y presentamos el total de registros nuevos insertados
        
        System.out.println("Registros insertados: " + bs.volcarDatosABD(bs.streamDato().collect(Collectors.toList())));
		
		// Ya no vamos a usar el Scanner
		
		sc.close();
	}
}
