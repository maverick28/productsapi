package com.productsapi.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Producto")
@NoArgsConstructor
@Getter
@Setter
public class Producto implements Serializable{
	
	private static final long serialVersionUID = 7022050908465628534L;
	
	public Producto (String nombre, Integer cantidad) {
		this.nombre = nombre;
		this.cantidad = cantidad;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "Nombre", length = 256, unique = true, nullable = false)
	private String nombre;
	
	@Column(name = "Cantidad", nullable = false)
	private Integer cantidad = 0;

}
