package principal;

import java.util.Scanner;

import model.Contacto;
import service.AgendaService;

public class AgendaBD {

	private static Scanner sc;
	private static AgendaService as;
	
	// Presenta un menú de opciones
	
	private static void presentarMenu() {
		
		System.out.println();
		System.out.println("----- MENÚ OPCIONES -----");
		System.out.println();
		System.out.println("1.-  Insertar contacto");
		System.out.println("2.-  Eliminar contacto");
		System.out.println("3.-  Buscar contacto");
		System.out.println("4.-  Mostrar contactos");
		System.out.println("5.-  Salir");
		System.out.println();
	}
	
	// Lee la opción elegida
	
	private static int leerOpcionMenu() {
		
		sc = new Scanner(System.in);
		
		return Integer.parseInt(sc.nextLine());
	}
	
	// Inserta un contacto
	
	private static void insertarContacto() {
		
		String nombre;
		String email;
		int edad;
		
		System.out.println("Introduce nombre: ");
		
		sc = new Scanner(System.in);
		
		nombre = sc.nextLine();
		
		System.out.println("Introduce email: ");
		
		sc = new Scanner(System.in);
		
		email = sc.nextLine();
		
		System.out.println("Introduce edad: ");
		
		sc = new Scanner(System.in);
		
		edad = Integer.parseInt(sc.nextLine());
		
		if (!as.insertarContacto(new Contacto(nombre, email, edad))) {
			
			System.out.println("Contacto insertado");
			
		} else {
			
			System.out.println("NO se ha insertado el contacto");
		}
	}
	
	// Elimina un contacto
	
	private static void eliminarContacto() {
		
		String email;
		
		System.out.println("Introduce email: ");
		
		sc = new Scanner(System.in);
		
		email = sc.nextLine();
		
		if (as.eliminarContacto(email)) {
			
			System.out.println("Contacto eliminado");
			
		} else {
			
			System.out.println("NO se ha eliminado el contacto");
		}
	}
	
	// Monstrar todos los contactos
	
	private static void mostrarContactos() {
		
		as.leerContactos().forEach(c -> System.out.println(c));
	}
	
	// Busca un contacto
	
	private static void buscarContacto() {
		
		String email;
		Contacto encontrado;
		
		System.out.println("Introduce email: ");
		
		sc = new Scanner(System.in);
		
		email = sc.nextLine();
		
		encontrado = as.buscarContacto(email);
		
		if (encontrado != null) {
			
			System.out.println("Contacto encontrado: " + encontrado);
			
		} else {
			
			System.out.println("NO se ha encontrado el contacto");
		}
	}
	
	public static void main(String[] args) {
		
		as = new AgendaService();
		
		int opcionMenu = -1;
		
        // Presentamos el menú de opciones
        
        do {
        	
        	presentarMenu();
        	
        	opcionMenu = leerOpcionMenu();
        	
        	switch (opcionMenu) {
        	
        	   case 1:
        		   
        		   insertarContacto();
        		   break;
        		   
        	   case 2:
        		   
        		   eliminarContacto();
        		   break;
        		   
        	   case 3:
        		   
        		   buscarContacto();
        		   break;
        		   
        	   case 4:
        		   
        		   mostrarContactos();
        		   break;
        		   
        	   case 5:
        		   
        		   System.out.println("BYE!");
        		   break;
        		   
        	   default:
        		   
        		   System.out.println("La opción no es válida. Prueba otra vez");
        		   break;
        		   
        	}
        	
        } while (opcionMenu != 5);
        
        sc.close();
	}
}
