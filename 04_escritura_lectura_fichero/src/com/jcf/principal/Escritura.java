package com.jcf.principal;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Escritura {

	public static void main(String[] args) {
		
		String ruta = "d:\\FicherosTestJava\\pruebas.txt";
		
		// Modo sobreescritura
		
		try (PrintStream out = new PrintStream(ruta);) {
			
		out.println("Es línea 1");
		out.println(4500);
		out.println("Fin archivo");
		
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
}
