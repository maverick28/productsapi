package com.productsapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productsapi.dto.ProductoDTO;
import com.productsapi.model.Producto;
import com.productsapi.service.ProductoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api/productos", produces = { "application/json" })
public class ProductoController {
	@Autowired
	private ProductoService productoService;

	@GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Optional<Producto>> getProducto(@RequestParam Integer id) {
		return ResponseEntity.ok(productoService.get(id));
	}

	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Producto>> getAllProductos(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "2") int size) {
		return ResponseEntity.ok(productoService.getAllPaged(page, size));
	}

	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createProducto(@RequestBody ProductoDTO producto) {
		return ResponseEntity.ok(productoService.create(producto));
	}

	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateProducto(@RequestBody ProductoDTO producto) {
		return ResponseEntity.ok(productoService.update(producto));
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<String> deleteProducto(@RequestParam Integer id) {
		return ResponseEntity.ok(productoService.delete(id));
	}
}
