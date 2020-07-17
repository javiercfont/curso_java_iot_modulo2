package service;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class NotasService {
	
	private final String ruta = "d:\\FicherosTestJava\\notas.txt";
	
	public double registrarNota(double nota) {
		
		try (PrintStream out = new PrintStream(new FileOutputStream(ruta, true));) {
			
			out.println(nota);
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		return nota;
	}
	
	public double calcularMedia() {
		
		double media = 0.0;
		int elementos_totales = 0;
		String linea;
		
		try (FileReader fr = new FileReader(ruta);
				BufferedReader br = new BufferedReader(fr);) {
			
			while ((linea = br.readLine()) != null) {
				
				media += Double.parseDouble(linea);
				elementos_totales++;
			}
			
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		
		return media / elementos_totales;
	}
	
	public int calcularAprobados() {
		
		int aprobados = 0;
		String linea;
		
		try (FileReader fr = new FileReader(ruta);
				BufferedReader br = new BufferedReader(fr);) {
			
			while ((linea = br.readLine()) != null) {
				
				if (Double.parseDouble(linea) >= 5.0) {

					aprobados++;
				}
			}
			
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		
		return aprobados;
	}

	public List<Double> leerNotas() {
		
		String linea;
		
		List<Double> notas = new ArrayList<>();
		
		try (FileReader fr = new FileReader(ruta);
				BufferedReader br = new BufferedReader(fr);) {
			
			while ((linea = br.readLine()) != null) {
				
				notas.add(Double.parseDouble(linea));
			}
			
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		
		return notas;
	}
}
