package model;

import java.util.Date;

public class PedidoTienda extends Pedido {
	
	private int idPedido;
	private String tienda;
	
	public PedidoTienda(String producto, int unidades, double precioUnitario, String seccion, Date fecha,
			String tienda) {
		super(producto, unidades, precioUnitario, seccion, fecha);
		this.tienda = tienda;
	}
	
	public PedidoTienda(int idPedido, String producto, int unidades, double precioUnitario, String seccion, Date fecha,
			String tienda) {
		super(producto, unidades, precioUnitario, seccion, fecha);
		this.tienda = tienda;
		this.idPedido = idPedido;
	}

	public String getTienda() {
		return tienda;
	}

	public void setTienda(String tienda) {
		this.tienda = tienda;
	}

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}	

	@Override
	public String toString() {
		return "PedidoTienda [idPedido=" + idPedido + ", tienda=" + tienda + ", producto=" + super.getProducto() + ", unidades=" + super.getUnidades()
				+ ", precioUnitario=" + super.getPrecioUnitario() + ", seccion=" + super.getSeccion() + ", fecha=" + super.getFecha() + "]";
	}
}
