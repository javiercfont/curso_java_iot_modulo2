package com.jcf.principal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LecturaFichero {

	public static void main(String[] args) {
		
		String ruta = "d:\\FicherosTestJava\\pruebas.txt";
		
		try (FileReader fr = new FileReader(ruta);
				BufferedReader br = new BufferedReader(fr);) {
			
			String linea;
			
			while ((linea = br.readLine()) != null) {
				
				System.out.println(linea);
			}
			
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
