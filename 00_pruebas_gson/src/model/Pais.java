package model;

public class Pais {
	
	private String country;
	private long population;
	private String capital;
	private double temperature;
	private long foundation;
	private String continent;
	
	public Pais(String country, long population, String capital, double temperature, long foundation,
			String continent) {

		this.country = country;
		this.population = population;
		this.capital = capital;
		this.temperature = temperature;
		this.foundation = foundation;
		this.continent = continent;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public long getFoundation() {
		return foundation;
	}

	public void setFoundation(long foundation) {
		this.foundation = foundation;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((capital == null) ? 0 : capital.hashCode());
		result = prime * result + ((continent == null) ? 0 : continent.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + (int) (foundation ^ (foundation >>> 32));
		result = prime * result + (int) (population ^ (population >>> 32));
		long temp;
		temp = Double.doubleToLongBits(temperature);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Pais other = (Pais) obj;
		if (capital == null) {
			if (other.capital != null)
				return false;
		} else if (!capital.equals(other.capital))
			return false;
		if (continent == null) {
			if (other.continent != null)
				return false;
		} else if (!continent.equals(other.continent))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (foundation != other.foundation)
			return false;
		if (population != other.population)
			return false;
		if (Double.doubleToLongBits(temperature) != Double.doubleToLongBits(other.temperature))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pais [country=" + country + ", population=" + population + ", capital=" + capital + ", temperature="
				+ temperature + ", foundation=" + foundation + ", continent=" + continent + "]";
	}
	
}