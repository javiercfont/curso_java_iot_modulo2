package model;

import java.util.Date;

public class DatoCovid {
	
	private String ccaa_iso;
	private Date fecha;
	private long num_casos;
	private long num_casos_prueba_pcr;
	private long num_casos_prueba_test_ac;
	private long num_casos_prueba_otras;
	private long num_casos_prueba_desconocida;
	
	public DatoCovid(String ccaa_iso, Date fecha, long num_casos, long num_casos_prueba_pcr,
			long num_casos_prueba_test_ac, long num_casos_prueba_otras, long num_casos_prueba_desconocida) {
		super();
		this.ccaa_iso = ccaa_iso;
		this.fecha = fecha;
		this.num_casos = num_casos;
		this.num_casos_prueba_pcr = num_casos_prueba_pcr;
		this.num_casos_prueba_test_ac = num_casos_prueba_test_ac;
		this.num_casos_prueba_otras = num_casos_prueba_otras;
		this.num_casos_prueba_desconocida = num_casos_prueba_desconocida;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ccaa_iso == null) ? 0 : ccaa_iso.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + (int) (num_casos ^ (num_casos >>> 32));
		result = prime * result + (int) (num_casos_prueba_desconocida ^ (num_casos_prueba_desconocida >>> 32));
		result = prime * result + (int) (num_casos_prueba_otras ^ (num_casos_prueba_otras >>> 32));
		result = prime * result + (int) (num_casos_prueba_pcr ^ (num_casos_prueba_pcr >>> 32));
		result = prime * result + (int) (num_casos_prueba_test_ac ^ (num_casos_prueba_test_ac >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatoCovid other = (DatoCovid) obj;
		if (ccaa_iso == null) {
			if (other.ccaa_iso != null)
				return false;
		} else if (!ccaa_iso.equals(other.ccaa_iso))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (num_casos != other.num_casos)
			return false;
		if (num_casos_prueba_desconocida != other.num_casos_prueba_desconocida)
			return false;
		if (num_casos_prueba_otras != other.num_casos_prueba_otras)
			return false;
		if (num_casos_prueba_pcr != other.num_casos_prueba_pcr)
			return false;
		if (num_casos_prueba_test_ac != other.num_casos_prueba_test_ac)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DatoCovid [ccaa_iso=" + ccaa_iso + ", fecha=" + fecha + ", num_casos=" + num_casos + "]";
	}
}