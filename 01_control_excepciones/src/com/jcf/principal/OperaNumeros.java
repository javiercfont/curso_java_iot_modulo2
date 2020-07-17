package com.jcf.principal;

import java.util.Scanner;

public class OperaNumeros {

	public static void main(String[] args) {
		
		int numerador;
		int denominador;
		int resultado;
		
		Scanner sc = new Scanner(System.in);
		
		try {
			
			System.out.println("Numerador: ");

			numerador = Integer.parseInt(sc.nextLine());

			System.out.println("Denominador: ");

			denominador = Integer.parseInt(sc.nextLine());

			resultado = numerador / denominador;

			System.out.println("El resultado es: " + resultado);
			
		} catch (NumberFormatException e) {
			
			System.out.println("Datos erroneos");
			
		} catch (ArithmeticException e) {

			System.out.println("División por 0");
			
		} catch (Exception e) { // Este siempre al final (si se pone)
			
			e.printStackTrace(); // Esto conviene ponerlo siempre en este caso
			
			System.out.println("Error inesperado");
			
		} finally {
			
			System.out.println("Programa finalizado");
			
			sc.close();
		}
		
		// Se puede usar multicatch desde java 8. Las excepciones no pueden
		// estar relacionadas por la herencia
		
		// catch (NumberFormatException | ArithmeticException e) {}
	}

}
