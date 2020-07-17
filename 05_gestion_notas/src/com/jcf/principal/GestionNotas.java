package com.jcf.principal;

import java.util.Scanner;

import service.NotasService;

public class GestionNotas {

	private static Scanner sc;
	
	// Presenta un menú de opciones
	
	static void presentarMenu() {
		
		System.out.println("----- MENÚ OPCIONES -----");
		System.out.println();
		System.out.println("1.- Registrar nota");
		System.out.println("2.- Calcular media");
		System.out.println("3.- Aprobados");
		System.out.println("4.- Mostrar notas");
		System.out.println("5.- Salir");
		System.out.println();
	}

	// Lee la opción elegida
	
	static int leerOpcionMenu() {
		
		sc = new Scanner(System.in);
		
		return sc.nextInt();
	}
	
	// Cierra el scanner
	
	static void cerrarScanner() {
		
		sc.close();
	}
	
	// Ingresar nota
	
	static void ingresar(NotasService notas) {
		
		System.out.println("\r\nIntroduce la nota a ingresar: ");
		
		sc = new Scanner(System.in);
		
		try {
			
			notas.registrarNota(Double.parseDouble(sc.nextLine()));
			
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		NotasService notas = new NotasService();
		
		int opcion;
		
		do {
			
			opcion = 0;
			
			presentarMenu();
			
			/* Captura el error de usuario al marcar una opción distinta
			   de int y evita que el programa caiga */
			
			try {
				
				opcion = leerOpcionMenu();
				
			} catch (java.util.InputMismatchException e) {
				
				System.out.println();
			}
			
			switch(opcion) {
			
				case 1:
				
					ingresar(notas);
					break;
					
				case 2:
					
					System.out.printf("\r\nLa media es: %2.2f\r\n\r\n", notas.calcularMedia());
					break;
					
				case 3:
					
					System.out.println("\r\nNúmero de aprobados: " + notas.calcularAprobados() + "\r\n\r\n");
					break;
					
				case 4:
					
					System.out.println("\r\nListado de notas: ");
					notas.leerNotas().forEach(n -> System.out.println(n));
					System.out.println();
					break;
					
				case 5:
					
					System.out.println("\r\nBYE !");
					break;
					
				default:
					
					System.out.println("\r\nLa opción no es válida. Prueba otra vez\r\n");
					break;
			}
			
		} while (opcion != 5);
		
		// Ya no vamos a usar el scanner
		
		cerrarScanner();
	}

}
