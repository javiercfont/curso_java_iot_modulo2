package com.jcf.principal;

import java.util.Scanner;

import model.Cliente;
import model.Cuenta;
import model.Titular;
import service.BancaAdminService;

public class BancaAdmin {

	static BancaAdminService bas;
	static Scanner sc;
	
	// Lee los datos de una cuenta
	
	private static Cuenta leerCuenta() {
		
		int numeroCuenta;
		double saldo;
		String tipoCuenta;
		
		System.out.println("Introduce el número de cuenta:");
		
		sc = new Scanner(System.in);
		
		try {
			
		    numeroCuenta = Integer.parseInt(sc.nextLine());
		
		} catch (NumberFormatException e) {
			
			System.out.println("Número de cuenta NO válido");
			
			return null;
		}
		
		System.out.println("Introduce el saldo:");
		
		sc = new Scanner(System.in);
		
		try {
			
		    saldo = Double.parseDouble(sc.nextLine());
		
		} catch (NumberFormatException e) {
			
			System.out.println("Saldo no válido");
			
			return null;
		}
		
		System.out.println("Introduce el tipo de cuenta:");
		
		sc = new Scanner(System.in);
			
		tipoCuenta = sc.nextLine();
		
		return new Cuenta(numeroCuenta, saldo, tipoCuenta);
	}
	
	// Lee un cliente
	
	private static Cliente leerCliente() {
		
		int dni;
		String nombre;
		String direccion;
		int telefono;
		
		System.out.println("Introduce el DNI:");
		
		sc = new Scanner(System.in);
		
		try {
			
		    dni = Integer.parseInt(sc.nextLine());
		
		} catch (NumberFormatException e) {
			
			System.out.println("DNI no válido");
			
			return null;
		}
		
		System.out.println("Introduce el nombre:");
		
		sc = new Scanner(System.in);
			
		nombre = sc.nextLine();
		
		System.out.println("Introduce la dirección:");
		
		sc = new Scanner(System.in);
			
		direccion = sc.nextLine();
		
		System.out.println("Introduce el teléfono:");
		
		sc = new Scanner(System.in);
		
		try {
			
		    telefono = Integer.parseInt(sc.nextLine());
		
		} catch (NumberFormatException e) {
			
			System.out.println("Teléfono no válido");
			
			return null;
		}
		
		return new Cliente(dni, nombre, direccion, telefono);
	}
	
	// Lee los datos de un titular
	
	private static Titular leerTitular() {
		
		int idCuenta;
		int idCliente;
		
		System.out.println("Introduce el número de cuenta:");
		
		sc = new Scanner(System.in);
		
		try {
			
		    idCuenta = Integer.parseInt(sc.nextLine());
		
		} catch (NumberFormatException e) {
			
			System.out.println("Número de cuenta NO válido");
			
			return null;
		}
		
		System.out.println("Introduce el ID del cliente:");
		
		sc = new Scanner(System.in);
		
		try {
			
		    idCliente = Integer.parseInt(sc.nextLine());
		
		} catch (NumberFormatException e) {
			
			System.out.println("ID de cliente NO válido");
			
			return null;
		}
		
		return new Titular(idCuenta, idCliente);
	}
	
	// Agrega una cuenta
	
	private static void agregarCuenta() {
		
		if (bas.altaCuenta(leerCuenta())) {
			
			System.out.println("La cuenta se ha dado de alta");
			
		} else {
			
			System.out.println("La cuenta NO se ha dado de alta");
		}
	}
	
	// Agrega un cliente
	
	private static void agregarCliente() {
		
		if (bas.altaCliente(leerCliente())) {
			
			System.out.println("El cliente se ha dado de alta");
			
		} else {
			
			System.out.println("El cliente NO se ha dado de alta");
		}
	}
	
	// Agrega un titular
	
	private static void agregarTitular() {
		
		if (bas.altaTitular(leerTitular())) {
			
			System.out.println("El titular se ha dado de alta");
			
		} else {
			
			System.out.println("El titular NO se ha dado de alta");
		}
	}
	
	// Presenta un menú de opciones
	
	private static void presentarMenu() {
		
		System.out.println();
		System.out.println("----- MENÚ OPCIONES -----");
		System.out.println();
		System.out.println("1.- Agregar cuenta");
		System.out.println("2.- Agregar cliente");
		System.out.println("3.- Agregar titular");
		System.out.println("0.- Salir");
		System.out.println();
	}
	
	// Lee la opción elegida
	
	private static int leerOpcionMenu() {
		
		sc = new Scanner(System.in);
		
		try {
		
		    return Integer.parseInt(sc.nextLine());
		
		} catch (NumberFormatException e) {
			
			return -1;
		}
	}
	
	
	public static void main(String[] args) {

		// Opción del menú
		
		int opcion = -1;
		
		// Se instancia el servicio de banca
		
		bas = new BancaAdminService();
			
		do {

			presentarMenu();

			opcion = leerOpcionMenu();

			switch (opcion) {

			case 1:

				agregarCuenta();
				break;

			case 2:

				agregarCliente();
				break;

			case 3:

				agregarTitular();
				break;

			case 0:

				System.out.println("BYE!");
				break;

			default:

				System.out.println("Opción NO válida. Prueba otra vez");
				break;
			}

		} while (opcion != 0);
			
		
		// Se cierra el Scanner
		
		sc.close();
	}
}
