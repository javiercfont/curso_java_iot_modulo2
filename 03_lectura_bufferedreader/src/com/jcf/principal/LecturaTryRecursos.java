package com.jcf.principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LecturaTryRecursos {

	public static void main(String[] args) {

		// Puede haber m�s de un objeto dentro de los par�ntesis
		// Todos ellos se cerrar�an
		
		try (InputStreamReader sr = new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(sr)) {
			
			System.out.println("Introduce el nombre: ");
			System.out.println("Tu nombre es: " + br.readLine());
			
		} catch (IOException e) {

			e.printStackTrace();	
		}
	}
}
