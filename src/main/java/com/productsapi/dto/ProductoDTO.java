package com.productsapi.dto;

import java.io.Serializable;
import java.util.Optional;

import com.productsapi.model.Producto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductoDTO implements Serializable{
	
	private static final long serialVersionUID = 4783084350982874513L;
	
	public ProductoDTO(Producto producto) {
		id = producto.getId();
		nombre = producto.getNombre();
		cantidad = producto.getCantidad();
	}
	
	public ProductoDTO(Optional<Producto> optional) {
		if(optional.isEmpty())
			new Producto();
		else {
			id = optional.get().getId();
			nombre = optional.get().getNombre();
			cantidad = optional.get().getCantidad();
		}
	}
	
	private Integer id;
	
	private String nombre;
	
	private Integer cantidad = 0;
	
}
