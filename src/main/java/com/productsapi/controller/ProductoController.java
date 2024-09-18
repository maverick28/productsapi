package com.productsapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.productsapi.dto.ProductoDTO;
import com.productsapi.service.ProductoService;

@RestController
@RequestMapping(value = "/api/productos", produces = { "application/json" })
public class ProductoController {
	@Autowired
	private ProductoService productoService;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductoDTO> getProducto(@PathVariable(required = true) Integer id) {
		return ResponseEntity.ok(productoService.get(id));
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductoDTO>> getAllProductos(@RequestParam(defaultValue = "0", required = true) int page,
			@RequestParam(defaultValue = "2", required = true) int size) {
		return ResponseEntity.ok(productoService.getAllPaged(page, size));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductoDTO> createProducto(@RequestBody ProductoDTO producto) {
		return ResponseEntity.created(null).body(productoService.create(producto));
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductoDTO> updateProducto(@PathVariable(required = true) Integer id,
			@RequestBody ProductoDTO producto) {
		producto.setId(id);
		return ResponseEntity.ok(productoService.update(producto));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteProducto(@PathVariable(required = true) Integer id) {
		productoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
