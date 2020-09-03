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

import service.FicherosService;
import util.Utilidades;

public class Cliente {
	
	// Scanner
	
	static Scanner sc;
	
	// Método para crear un Socket en un puerto y enviar y recibir datos
	
	private static void crearSocket(int puerto, String ca_iso) {
		
		try (Socket sk = new Socket("localhost", puerto)) {
			
			List<String> datos = new ArrayList<>();
			
			PrintStream out = new PrintStream(sk.getOutputStream());
			
			// Se envía el ISO de la CA
			
			out.println(ca_iso);
			
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

				// Se guardan los datos en un fichero
				
				FicherosService fs = new FicherosService();
				
				fs.volcarDatosAlFichero(Utilidades.NOMBRE_CA.get(ca_iso), total_positivos, media_positivos_diarios, fecha_pico);
				
				System.out.println("Se han guardado los datos");
				
			} else {
				
				System.out.println("NO existe la CA");
			}
			
		} catch (UnknownHostException e) {

		     e.printStackTrace();
		
	    } catch (IOException e) {

		     e.printStackTrace();
	    }
	}
	
	// Método de prueba para realizar todas las consultas
	
	private static void testAll() {
		
		// Se lanza una petición al server, por cada CA
		
		for (String ca_iso : Utilidades.NOMBRE_CA.keySet()) {
			
			crearSocket(2050, ca_iso);
		}
	}
	
	public static void main(String[] args) {
		
		// Se pide el nombre ISO de la CA al usuario
		
        System.out.println("Introduce el nombre ISO de la CA (ALL = TODAS):");
        
        sc = new Scanner(System.in);
        
        String ca_iso = sc.nextLine();
        
        if (ca_iso.equalsIgnoreCase("ALL")) {
        	
        	// Se lanzan las peticiones para todas las CAs
        	
        	testAll();

        } else {
        	
        	// Se lanza la petición para una CA

        	crearSocket(2050, ca_iso);
        }
		
		// Se cierra el Scanner
		
		sc.close();
	}
}
