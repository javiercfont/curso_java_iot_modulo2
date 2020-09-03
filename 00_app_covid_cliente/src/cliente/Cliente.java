package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import util.Utilidades;

public class Cliente {
	
	// Scanner
	
	static Scanner sc;
	
	public static void main(String[] args) {
		
		try (Socket sk = new Socket("localhost", 2050)) {
	        
			// Lista de datos recuperados del server
			
			List<String> datos = new ArrayList<>();
			
			// Se pide el nombre ISO de la CA al usuario
			
	        System.out.println("Introduce el nombre ISO de la CA:");
	        
	        sc = new Scanner(System.in);
	        
	        String nombre_ca = sc.nextLine();
	        
	        // Se envía el nombre ISO de la CA para que el server recupere los datos
	        
	        PrintStream out = new PrintStream(sk.getOutputStream());
	        
	        out.println(nombre_ca);
			
	        // Se leen los datos
	        
			BufferedReader br = new BufferedReader(new InputStreamReader(sk.getInputStream()));
	        
			// Se recuperan los 3 datos del server
			
			datos.add(br.readLine());
			datos.add(br.readLine());
			datos.add(br.readLine());
			
			// Si no hay datos null
			
			if (!datos.contains(null)) {
				
				long total_positivos = Long.parseLong(datos.get(0));
				double media_positivos_diarios = Double.parseDouble(datos.get(1));
				Date fecha_pico = Utilidades.convertirCadenaADate(datos.get(2));

				// Se presentan los datos

				System.out.println("Resumen para la comunidad de " + Utilidades.NOMBRE_CA.get(nombre_ca));
				System.out.println();
				System.out.println("Total de positivos          : " + total_positivos);
				System.out.printf("Media de positivos diarios  : %2.1f", media_positivos_diarios);
				System.out.println();
				System.out.println("Fecha del pico de contagios : " + Utilidades.convertirALocalDate(fecha_pico));
				
			} else {
				
				System.out.println("NO existe la CA");
			}
		
		} catch (UnknownHostException e) {

			e.printStackTrace();
			
		} catch (IOException e) {

			e.printStackTrace();
			
		}
		
		// Se cierra el Scanner
		
		sc.close();
	}
}
