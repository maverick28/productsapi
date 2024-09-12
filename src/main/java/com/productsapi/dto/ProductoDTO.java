package com.productsapi.dto;

import java.io.Serializable;

public class ProductoDTO implements Serializable{
	
	private static final long serialVersionUID = 4783084350982874513L;

	public ProductoDTO() {}
	
	private Integer id;
	
	private String nombre;
	
	private Integer cantidad = 0;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
}
