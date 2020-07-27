package com.jcf.principal;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;
import java.util.TreeMap;

import service.AppCovidService;
import util.Utilidades;

public class AppCovid {

	private static Scanner sc;
	private static AppCovidService cs;
	
	// Presenta un men� de opciones
	
	private static void presentarMenu() {
		
		System.out.println();
		System.out.println("----- MEN� OPCIONES -----");
		System.out.println();
		System.out.println("1.- Seleccionar archivo de datos");
		System.out.println("2.- Lista de casos entre fechas");
		System.out.println("3.- N�mero de positivos dado un d�a");
		System.out.println("4.- Fecha de pico de contagios");
		System.out.println("5.- Media de positivos dado un d�a");
		System.out.println("6.- Total de positivos en una comunidad");
		System.out.println("7.- Total de positivos por cada comunidad");
		System.out.println("8.- Lista de casos por comunidad");
		System.out.println("9.- Lista con la media de positivos por d�a");
		System.out.println("0.- Salir");
		System.out.println();
	}
	
	private static void seleccionarArchivoDatos() {

		System.out.println("Introduce el tipo de archivo (CSV o JSON):");
		
		sc = new Scanner(System.in);
		
		String tipo = sc.nextLine().toUpperCase();
		
		switch (tipo) {
		
		    case AppCovidService.CSV:
			
			   // Se instancia el servicio para archivo de datos CSV
			
			   cs = new AppCovidService(AppCovidService.CSV);
			   break;
			   
		    case AppCovidService.JSON:
				
			   // Se instancia el servicio para archivo de datos JSON
			
			   cs = new AppCovidService(AppCovidService.JSON);
			   break;
			   
			default:
				
			   System.out.println(System.lineSeparator() + "Tipo de archivo NO soportado" + System.lineSeparator());
			   break;
		}
	}
	
	// Lee la opci�n elegida
	
	private static int leerOpcionMenu() {
		
		sc = new Scanner(System.in);
		
		return Integer.parseInt(sc.nextLine());
	}
	
	// Lee un dato de una fecha
	
	private static int leerDatoFecha() {
		
		sc = new Scanner(System.in);
		
		return Integer.parseInt(sc.nextLine());
	}
	
	// Lee una fecha LocalDate
	
	private static LocalDate leerFecha() {
		
		int dia;
		int mes;
		int anio;

		try {
			
			System.out.println("Introduce d�a:");
			
			dia = leerDatoFecha();
			
		} catch (NumberFormatException e) {
			
			System.out.println("El d�a no es v�lido");
			
			return null;
		}
		
		try {
			
			System.out.println("Introduce mes:");
			
			mes = leerDatoFecha();
			
		} catch (NumberFormatException e) {
			
			System.out.println("El mes no es v�lido");
			
			return null;
		}
		
		try {
			
			System.out.println("Introduce a�o:");
			
			anio = leerDatoFecha();
			
		} catch (NumberFormatException e) {
			
			System.out.println("El a�o no es v�lido");
			
			return null;
		}
		
		try {
			
		    return LocalDate.of(anio, mes, dia);
		
		} catch (DateTimeException e) {
			
			e.printStackTrace();
			
			return null;
		}
	}
	
	// Busca los casos entre fechas
	
	private static void buscarCasosEntreFechas() {

        System.out.println("Introduce la fecha de inicio:");
		LocalDate fecha_inicio = leerFecha();
		System.out.println("Introduce la fecha de fin:");
		LocalDate fecha_fin = leerFecha();
		
		if ((fecha_inicio != null) && (fecha_fin != null)) {
		
			System.out.println("Lista de casos entre: " + fecha_inicio + " y " + fecha_fin + System.lineSeparator());
			
			cs.casosEntreFechas(Utilidades.convertirADate(fecha_inicio), Utilidades.convertirADate(fecha_fin))
			      .forEach(c -> System.out.println(Utilidades.DatoCovidACadenaCasoCovid(c)));
		}
	}
	
	// Presenta el n�mero de positivos en un d�a
	
	private static void numeroPositivosEnUnDia() {

		System.out.println("Introduce la fecha:");
		LocalDate fecha = leerFecha();
		
		if (fecha != null) {
			
			System.out.println(System.lineSeparator() + 
					              "Total de casos en la fecha " + 
					               fecha + 
					               " ----> " + 
					               cs.casosEnUnDia(Utilidades.convertirADate(fecha)));
		}
	}
	
	// Presenta la fecha con el mayor n�mero de contagios
	
	private static void mostrarFechaPico() {
	
		Date fecha_pico = cs.fechaPicoContagios();
		
		System.out.println(System.lineSeparator() + 
				             "Fecha de pico de contagios: " +
				              Utilidades.convertirALocalDate(fecha_pico) +
				              " con un total de: " + cs.casosEnUnDia(fecha_pico));
	}
	
	
	// Presenta el n�mero de positivos en una comunidad
	
	private static void numeroPositivosEnUnaComunidad() {
		
		System.out.println("Introduce el c�digo de la comunidad:");
		
		sc = new Scanner(System.in);
		
		String codigo_ca = sc.nextLine();
		
		if (Utilidades.NOMBRE_CA.containsKey(codigo_ca)) {
			
			System.out.println(System.lineSeparator() +
					           "Total de positivos en " +
					           Utilidades.NOMBRE_CA.get(codigo_ca) +
					           " ----> " +
					           cs.totalPositivosComunidad(codigo_ca));
		} else {
			
			System.out.println(System.lineSeparator() + "C�digo ISO de comunidad no v�lido" + System.lineSeparator());
		}
	}
	
	// Presenta un listado del total de casos positivos en una comunidad
	
	private static void listadoTotalPositivosPorComunidad() {
		
		System.out.println();
		
		for (String k : cs.totalPositivosPorComunidad().keySet()) {
		
			System.out.println("Comunidad: " + 
					           Utilidades.NOMBRE_CA.get(k) + " ----> " +
					           "Total positivos: " + cs.totalPositivosPorComunidad().get(k));
		}
		
		System.out.println();
	}
	
	// Presenta la media de positivos dado un d�a
	
	private static void mediaPositivosDadoUnDia() {

		System.out.println("Introduce la fecha:");
		LocalDate fecha = leerFecha();
		
		System.out.println(System.lineSeparator() +
		           "Media de positivos en la fecha: " +
		           fecha +
		            " ----> " +
		            cs.mediaPositivosEnUnDia(Utilidades.convertirADate(fecha)));
	}
	
	// Tabla de datos de casos por comunidad
	
	private static void tablaCasosPorComunidad() {
		
		for (String k : cs.tablaListaCasosPorComunidad().keySet()) {
			
			System.out.println(System.lineSeparator() + "Comunidad " + Utilidades.NOMBRE_CA.get(k) + " Casos: "  + System.lineSeparator());
			
			cs.tablaListaCasosPorComunidad().get(k)
			      .forEach(c -> System.out.println(Utilidades.DatoCovidACadenaCasoCovid(c)));
			
			System.out.println(System.lineSeparator() + "Introduce un car�cter para mostrar m�s:");
			
			sc = new Scanner(System.in);
			
			sc.nextLine();
		}
		
		System.out.println(System.lineSeparator() + "NO hay m�s datos" + System.lineSeparator());
	}
	
	// Listado de la media de positivos diarios
	
	private static void listadoMediaPositivosDiarios() {
		
		// Se construye un mapa ordenado por fecha con los datos obtenidos del servicio
		
		TreeMap<Date, Double> mapaOrdenado = new TreeMap<>(cs.mediaPositivosDiarios());
		
		// Se presentan los datos ordenados por fecha
		
		System.out.println();

		for (Date f : mapaOrdenado.keySet()) {
			
			System.out.print("Fecha: " + Utilidades.convertirALocalDate(f) + 
					              " ----> Media casos: ");
		    System.out.printf("%2.2f", mapaOrdenado.get(f));
		    System.out.println();
		}
		
		System.out.println();
	}
	

	public static void main(String[] args) {

		int opcionMenu = -1;
		
		// Preguntamos por el tipo de archivo hasta que el servicio se inicialice
		
        do {
        	
        	seleccionarArchivoDatos();
        	
        } while (cs == null);

		
        // Presentamos el men� de opciones
        
        do {
        	
        	presentarMenu();
        	
        	opcionMenu = leerOpcionMenu();
        	
        	switch (opcionMenu) {
        	
        	   case 1:
        		   
        		   seleccionarArchivoDatos();
        		   break;
        		   
        	   case 2:
        		   
        		   buscarCasosEntreFechas();
        		   break;
        		   
        	   case 3:
        		   
        		   numeroPositivosEnUnDia();
        		   break;
        		   
        	   case 4:
        		   
        		   mostrarFechaPico();
        		   break;
        		   
        	   case 5:
        		   
        		   mediaPositivosDadoUnDia();
        		   break;
        	
        	   case 6:
        		   
        		   numeroPositivosEnUnaComunidad();
        		   break;
        		   
        	   case 7:
        		   
        		   listadoTotalPositivosPorComunidad();
        		   break;
        		   
        	   case 8:
        		   
        		   tablaCasosPorComunidad();
        		   break;
        		   
        	   case 9:
        		   
        		   listadoMediaPositivosDiarios();
        		   break;
        		   
        	   case 0:
        		   
        		   System.out.println("BYE!");
        		   break;
        		   
        	   default:
        		   
        		   System.out.println("Opci�n no v�lida. Prueba otra vez");
        		   break;
        	}
        	
        } while (opcionMenu != 0);
		
		// Ya no vamos a usar el Scanner
		
		sc.close();
	}
}
