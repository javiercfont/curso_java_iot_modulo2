package com.jcf.principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Lectura {

	public static void main(String[] args) {
		
		InputStreamReader sr = new InputStreamReader(System.in);
		
		BufferedReader br = null;
		
		try {
			
			br = new BufferedReader(sr);
			
			System.out.println("Introduce el nombre: ");
			System.out.println("Tu nombre es: " + br.readLine());
			
		} catch (IOException e) {

			e.printStackTrace();
			
		} finally {
			
			try {
				
				if(br != null) {
					
					br.close();
				}
				
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}
}
