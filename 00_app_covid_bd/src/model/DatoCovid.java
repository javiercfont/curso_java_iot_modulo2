package model;

import java.util.Date;

public class DatoCovid {
	
	private int idDato;
	private String ccaa_iso;
	private Date fecha;
	private long num_casos;
	private long num_casos_prueba_pcr;
	private long num_casos_prueba_test_ac;
	private long num_casos_prueba_otras;
	private long num_casos_prueba_desconocida;
	
	public DatoCovid(int idDato, String ccaa_iso, Date fecha, long num_casos, long num_casos_prueba_pcr,
			long num_casos_prueba_test_ac, long num_casos_prueba_otras, long num_casos_prueba_desconocida) {
		super();
		this.idDato = idDato;
		this.ccaa_iso = ccaa_iso;
		this.fecha = fecha;
		this.num_casos = num_casos;
		this.num_casos_prueba_pcr = num_casos_prueba_pcr;
		this.num_casos_prueba_test_ac = num_casos_prueba_test_ac;
		this.num_casos_prueba_otras = num_casos_prueba_otras;
		this.num_casos_prueba_desconocida = num_casos_prueba_desconocida;
	}

	public int getIdDato() {
		return idDato;
	}

	public void setIdDato(int idDato) {
		this.idDato = idDato;
	}

	public String getCcaa_iso() {
		return ccaa_iso;
	}

	public void setCcaa_iso(String ccaa_iso) {
		this.ccaa_iso = ccaa_iso;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public long getNum_casos() {
		return num_casos;
	}

	public void setNum_casos(long num_casos) {
		this.num_casos = num_casos;
	}

	public long getNum_casos_prueba_pcr() {
		return num_casos_prueba_pcr;
	}

	public void setNum_casos_prueba_pcr(long num_casos_prueba_pcr) {
		this.num_casos_prueba_pcr = num_casos_prueba_pcr;
	}

	public long getNum_casos_prueba_test_ac() {
		return num_casos_prueba_test_ac;
	}

	public void setNum_casos_prueba_test_ac(long num_casos_prueba_test_ac) {
		this.num_casos_prueba_test_ac = num_casos_prueba_test_ac;
	}

	public long getNum_casos_prueba_otras() {
		return num_casos_prueba_otras;
	}

	public void setNum_casos_prueba_otras(long num_casos_prueba_otras) {
		this.num_casos_prueba_otras = num_casos_prueba_otras;
	}

	public long getNum_casos_prueba_desconocida() {
		return num_casos_prueba_desconocida;
	}

	public void setNum_casos_prueba_desconocida(long num_casos_prueba_desconocida) {
		this.num_casos_prueba_desconocida = num_casos_prueba_desconocida;
	}

	@Override
	public String toString() {
		return "DatoCovid [idDato=" + idDato + ", ccaa_iso=" + ccaa_iso + ", fecha=" + fecha + ", num_casos="
				+ num_casos + ", num_casos_prueba_pcr=" + num_casos_prueba_pcr + ", num_casos_prueba_test_ac="
				+ num_casos_prueba_test_ac + ", num_casos_prueba_otras=" + num_casos_prueba_otras
				+ ", num_casos_prueba_desconocida=" + num_casos_prueba_desconocida + "]";
	}
}