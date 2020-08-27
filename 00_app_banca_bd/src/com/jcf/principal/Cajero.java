package com.jcf.principal;

import java.util.Scanner;

import service.BancaService;

public class Cajero {

	static BancaService bs;
	static Scanner sc;
	static int cuenta_cliente;
	
	// Comprueba un n�mero de cuenta
	
	private static boolean testCuenta() {
		
		System.out.println("Introduce el n�mero de cuenta:");
		
		sc = new Scanner(System.in);
		
		try {
			
		    cuenta_cliente = Integer.parseInt(sc.nextLine());
		
		    return bs.comprobarCuenta(cuenta_cliente);
		
		} catch (NumberFormatException e) {
			
			return false;
		}
	}
	
	// Presenta un men� de opciones
	
	private static void presentarMenu() {
		
		System.out.println();
		System.out.println("----- MEN� OPCIONES -----");
		System.out.println();
		System.out.println("1.- Sacar dinero");
		System.out.println("2.- Ingresar dinero");
		System.out.println("3.- Mostrar saldo");
		System.out.println("4.- �ltimos movimientos");
		System.out.println("5.- Transferencia a otra cuenta");
		System.out.println("0.- Salir");
		System.out.println();
		
		System.out.println("Est�s operando con la cuenta: " + cuenta_cliente);
	}
	
	// Lee la opci�n elegida
	
	private static int leerOpcionMenu() {
		
		sc = new Scanner(System.in);
		
		try {
		
		    return Integer.parseInt(sc.nextLine());
		
		} catch (NumberFormatException e) {
			
			return -1;
		}
	}
	
	
	// Extrae dinero de la cuenta
	
	private static void sacarDinero() {
		
		System.out.println("Introduce la cantidad a extraer:");
		
		sc = new Scanner(System.in);
		
		try {
			
		    double cantidad = Double.parseDouble(sc.nextLine());
		
		    if(bs.sacar(cantidad, cuenta_cliente) > 0) {
		    	
		    	System.out.println("Se ha extraido la cantidad: " + cantidad);
		    	
		    } else {
		    	
		    	System.out.println("No se ha podido realizar la operaci�n");
		    }
		
		} catch (NumberFormatException e) {
			
			System.out.println("Cantidad no v�lida");
		}
	}
	
	// Extrae dinero de la cuenta
	
	private static void ingresarDinero() {
		
		System.out.println("Introduce la cantidad a ingresar:");
		
		sc = new Scanner(System.in);
		
		try {
			
		    double cantidad = Double.parseDouble(sc.nextLine());
		
		    if(bs.ingresar(cantidad, cuenta_cliente) > 0) {
		    	
		    	System.out.println("Se ha ingresado la cantidad: " + cantidad);
		    	
		    } else {
		    	
		    	System.out.println("No se ha podido realizar la operaci�n");
		    }
		
		} catch (NumberFormatException e) {
			
			System.out.println("Cantidad no v�lida");
		}
	}
	
	// Muestra el saldo de la cuenta
	
	private static void mostrarSaldo() {
		
		System.out.println();
		System.out.println("El saldo de la cuenta: " + cuenta_cliente + " es: " + bs.saldo(cuenta_cliente));
		System.out.println();
	}
	
	// Transfiere dinero entre cuentas
	
	private static void transferirDinero() {
		
		System.out.println("Introduce el n�mero de cuenta destino:");
		
		sc = new Scanner(System.in);
		
		try {
			
		    int cuenta_destino = Integer.parseInt(sc.nextLine());
		
		    // Se comprueba si existe la cuenta destino antes de operar
		    
		    if(bs.comprobarCuenta(cuenta_destino)) {
		    	
				System.out.println("Introduce la cantidad a transferir:");
				
				sc = new Scanner(System.in);
				
				try {
					
				    double cantidad = Double.parseDouble(sc.nextLine());
				
				    // Los m�todos sacar e ingresar se encargan de registrar los movimientos en las dos cuentas
				    // adem�s de actualizarlas
				    
				    if((bs.sacar(cantidad, cuenta_cliente) > 0) && (bs.ingresar(cantidad, cuenta_destino) > 0)) {
				    	
				    	System.out.println("Se ha transferido la cantidad: " + cantidad);
				    	
				    } else {
				    	
				    	System.out.println("No se ha podido realizar la operaci�n");
				    }
				
				} catch (NumberFormatException e) {
					
					System.out.println("Cantidad no v�lida");
				}
				
		    } else {
		    	
		    	System.out.println("NO existe el n�mero de cuenta destino");
		    }
		
		} catch (NumberFormatException e) {
			
			System.out.println("N�mero de cuenta NO v�lido");
		}
		
	}
	
	private static void presentarUltimosMovimientos() {
	
		System.out.println("Movimientos en los �ltimos 30 d�as:");
		System.out.println();
		
		bs.ultimosMovimientos(cuenta_cliente).forEach(m -> System.out.println(m));
		
		System.out.println();
	}
	
	public static void main(String[] args) {

		// Opci�n del men�
		
		int opcion = -1;
		
		// Se instancia el servicio de banca
		
		bs = new BancaService();
		
		if(testCuenta()) {
			
			do {
				
				presentarMenu();
				
				opcion = leerOpcionMenu();
				
				switch (opcion) {
				
				   case 1:
					   
					   sacarDinero();
					   break;
					   
				   case 2:
					   
					   ingresarDinero();
					   break;
					   
				   case 3:
					   
					   mostrarSaldo();
					   break;
					   
				   case 4:
					   
					   presentarUltimosMovimientos();
					   break;
					   
				   case 5:
					   
					   transferirDinero();
					   break;
					   
				   case 0:
					   
					   System.out.println("BYE!");
					   break;
					   
				   default:
					   
					   System.out.println("Opci�n NO v�lida. Prueba otra vez");
					   break;
				}
				
			} while (opcion != 0);
			
		} else {
			
			System.out.println("El n�mero de cuenta no es v�lido. Se ha cerrado el cajero");
		}
		
		// Se cierra el Scanner
		
		sc.close();
	}
}
