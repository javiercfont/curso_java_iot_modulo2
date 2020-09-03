package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import service.DatosService;
import util.Utilidades;

public class HiloAtencionCliente implements Runnable {

	private Socket sk;
	
	static Lock lk = new ReentrantLock();
	
	public HiloAtencionCliente(Socket sk) {
		
		this.sk = sk;
	}
	
	@Override
	public void run() {
		
		try (PrintWriter out = new PrintWriter(sk.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(sk.getInputStream()));) {
			
			// Bloqueamos el acceso a recursos para otros clientes
			
			lk.lock();
			
			// Se recupera el nombre de la tienda que nos manda el cliente
			
			String nombre_ca = br.readLine();
			
			// Comprobamos si existe la CA para mandar los datos
			
			if (Utilidades.NOMBRE_CA.containsKey(nombre_ca)) {
				
				// Se instancia el GestorPedidos para trabajar contra BD (constructor sin parámetros)
				
				DatosService ds = new DatosService(nombre_ca);
				
				// Se envía al cliente toda la información para la CA
				
				out.println(ds.totalPositivos());
				out.println(ds.mediaPositivosDiarios());
				out.println(ds.fechaPicoContagios());
			}
			
			// Desbloqueamos el acceso a recursos para otros clientes
			
			lk.unlock();
			
		} catch (IOException e) {

			e.printStackTrace();
			
		} finally {
			
			// No podemos aprovechar el try con recursos porque el Socket
			// nos lo están pasando en el contructor
			
			try {
				
				sk.close();
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
}
