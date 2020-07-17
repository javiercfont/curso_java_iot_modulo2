package com.jcf.principal;

import java.util.Scanner;

import exceptions.SaldoNegativoException;
import model.Movimiento;
import service.CuentaServiceMovimiento;

public class Cajero {
	
	private static Scanner sc;
	
	
	// Presenta un menú de opciones
	
	static void presentarMenu() {
		
		System.out.println("----- MENÚ OPCIONES -----");
		System.out.println();
		System.out.println("1.- Ingresar");
		System.out.println("2.- Extraer");
		System.out.println("3.- Movimientos y saldo");
		System.out.println("4.- Salir");
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
	
	// Lee el saldo y el límite de la cuenta
	
	static void leerSaldoLimite(CuentaServiceMovimiento cuenta) {
		
		System.out.println("Introduce el saldo de la cuenta: ");
		
		sc = new Scanner(System.in);
		
		cuenta.setSaldo(sc.nextDouble());
		
		System.out.println("Introduce el límite de los movimientos de la cuenta: ");
		
		sc = new Scanner(System.in);
		
		cuenta.setLimite(sc.nextDouble());
		
	}
	
	// Ingresa una cantidad en la cuenta
	
	static void ingresar(CuentaServiceMovimiento cuenta) {
		
		double cantidad;
		
		System.out.println("Introduce la cantidad a ingresar: ");
		
		sc = new Scanner(System.in);
		
		cantidad = sc.nextDouble();
		
		cuenta.ingresar(cantidad);
	}

	// Extrae una cantidad en la cuenta
	
	static void extraer(CuentaServiceMovimiento cuenta) {
		
		System.out.println("Introduce la cantidad a extraer: ");
		
		sc = new Scanner(System.in);
		

			
	    try {
	    	
			cuenta.extraer(Double.parseDouble(sc.nextLine()));
			
		} catch (NumberFormatException e) {
			
			System.out.println("La cantidad no es numérica");
			
		} catch (SaldoNegativoException e) {

			e.getMessage();
		}
			

	}
	
	// Consulta los movimientos
	
	static void verMovimientos(CuentaServiceMovimiento cuenta) {
		
		if (cuenta.obtenerMovimientos().size() != 0) {
			
			for (Movimiento m : cuenta.obtenerMovimientos()) {
				
				System.out.println(m);
			}
			
		} else {
			
			System.out.println("\r\nNo hay movimientos en la cuenta\r\n");
		}
		
		System.out.println("\r\nSALDO ACTUAL: " + cuenta.getSaldo());
		System.out.println("\r\nLIMITE ACTUAL: " + cuenta.getLimite() + "\r\n");
	}
	
	public static void main(String[] args) {
		
		CuentaServiceMovimiento cuenta = new CuentaServiceMovimiento(0, 0);
		
		int opcion;
		
		leerSaldoLimite(cuenta);
		
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
				
					ingresar(cuenta);
					break;
					
				case 2:
					
					extraer(cuenta);
					break;
					
				case 3:
					
					verMovimientos(cuenta);
					break;
					
				case 4:
					
					System.out.println("BYE!\r\n");
					break;
					
				default:
					
					System.out.println("\r\nLa opción no es válida. Prueba otra vez\r\n");
					break;
			}
			
		} while (opcion != 4);
		
		// Ya no vamos a usar el scanner
		
		cerrarScanner();

	}

}
