package com.jcf.principal;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class EscrituraAppend {

	public static void main(String[] args) {

		String ruta = "d:\\FicherosTestJava\\pruebas.txt";
		
		// Modo append
		
		try (PrintStream out = new PrintStream(new FileOutputStream(ruta, true));) {
			
			out.println("M�s l�neas");
			out.println(700);
			out.println("Otras l�neas");
		
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}

	}

}
