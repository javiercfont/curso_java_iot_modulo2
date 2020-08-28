package model;

public class DatoPais extends Pais {
	
	private String alpha2Code;
	private String region;
	private double[] posicion;
	
	public DatoPais(String nombre, String capital, long area, long poblacion, String alpha2Code, String region,
			double[] posicion) {
		super(nombre, capital, area, poblacion);
		this.alpha2Code = alpha2Code;
		this.region = region;
		this.posicion = posicion;
	}

	public String getAlpha2Code() {
		return alpha2Code;
	}

	public void setAlpha2Code(String alpha2Code) {
		this.alpha2Code = alpha2Code;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public double[] getPosicion() {
		return posicion;
	}

	public void setPosicion(double[] posicion) {
		this.posicion = posicion;
	}
}
