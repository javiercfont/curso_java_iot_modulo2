package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.PedidoTienda;


public class Cliente {

	// Formato fecha
	
	private final static String FORMATO_FECHA = "dd/MM/yyyy";
	
	// Scanner
	
	static Scanner sc;
	
	public static void main(String[] args) {
		
		try (Socket sk = new Socket("localhost", 2050)) {
	        
			// Se pide la tienda al usuario
			
	        System.out.println("Introduce el nombre de la tienda:");
	        
	        sc = new Scanner(System.in);
	        
	        String tienda = sc.nextLine();
	        
	        // Se envía la tienda para que el server recupere los pedidos
	        
	        PrintStream out = new PrintStream(sk.getOutputStream());
	        
	        out.println(tienda);
			
	        // Se leen los datos (el server manda String json)
	        
			BufferedReader br = new BufferedReader(new InputStreamReader(sk.getInputStream()));
	        
			String json = br.readLine();

			// Traza para ver la cadena (String) de datos JSON que manda el server
			
			// System.out.println("Respuesta del server: " + json);
			
			// Se crea el Gson con la opción del formato de fecha que tenemos en los datos
			
			Gson gson = new GsonBuilder().setDateFormat(FORMATO_FECHA).create();
			
			// Se crea un array PedidoTienda usando un método de la librería Gson
			
			PedidoTienda[] pedidos = gson.fromJson(json, PedidoTienda[].class);
			
			// Se muestran los pedidos de tienda recuperados
			
			Arrays.stream(pedidos).forEach(p -> System.out.println(p));
		
		} catch (UnknownHostException e) {

			e.printStackTrace();
			
		} catch (IOException e) {

			e.printStackTrace();
			
		}
		
		sc.close();
	}
}
