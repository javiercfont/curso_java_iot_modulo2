package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import service.GestorPedidos;
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
			
			String nombre_tienda = br.readLine();
			
			// Se instancia el GestorPedidos para trabajar contra BD (constructor sin parámetros)
			
			GestorPedidos gp = new GestorPedidos();
			
			// Se crea el Gson con la opción del formato de fecha que tenemos en los datos
			
			Gson gson = new GsonBuilder().setDateFormat(Utilidades.FORMATO_FECHA).create();
			
			// Se envía al cliente una línea con la cadena (string) JSON que genera el método toJson
			
			out.println(gson.toJson(gp.recuperarPedidosTienda(nombre_tienda)));
			
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
