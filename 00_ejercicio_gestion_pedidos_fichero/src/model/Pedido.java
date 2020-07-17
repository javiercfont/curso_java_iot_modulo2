package model;

public class Pedido {
	
	private int id;
	private String producto;
	private double precio;
	
	public Pedido(int id, String producto, double precio) {
		
		this.id = id;
		this.producto = producto;
		this.precio = precio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}


	// Para equals y hashCode sólo nos importa el número de pedido (id)

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Pedido other = (Pedido) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		return "Pedido [Número de pedido = " + id + ", producto = " + producto + ", precio = " + precio + "]";
	}


}
