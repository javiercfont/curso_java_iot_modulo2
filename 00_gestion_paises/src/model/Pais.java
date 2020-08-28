package model;

public class Pais {
	
	private String nombre;
	private String capital;
	private long area;
	private long poblacion;
	
	public Pais(String nombre, String capital, long area, long poblacion) {
		super();
		this.nombre = nombre;
		this.capital = capital;
		this.area = area;
		this.poblacion = poblacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public long getArea() {
		return area;
	}

	public void setArea(long area) {
		this.area = area;
	}

	public long getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(long poblacion) {
		this.poblacion = poblacion;
	}

	@Override
	public String toString() {
		return "Pais [nombre=" + nombre + ", capital=" + capital + ", area=" + area + ", poblacion=" + poblacion + "]";
	}
}
