package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {

	public static void main(String[] args) {
		
		ExecutorService exs = Executors.newCachedThreadPool();
		
		System.out.println("Servidor en espera ...");
		
		// El try con recursos garantiza el cierre del server
		
		try (ServerSocket server = new ServerSocket(2050);) {
			
			// Por cada conexión de un cliente se mete un hilo al pool
			
			while(true) {
				
				Socket sk = server.accept();
				System.out.println("Conexión cliente aceptada");
				exs.submit(new HiloAtencionCliente(sk));
			}
		
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
