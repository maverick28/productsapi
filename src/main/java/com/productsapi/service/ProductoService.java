package com.productsapi.service;

import java.util.List;
import java.util.Optional;

import com.productsapi.dto.ProductoDTO;
import com.productsapi.model.Producto;

public interface ProductoService {
	
	Optional<Producto> get(Integer id);
	    
	List<Producto> getAllPaged(int page, int size);
	
	String create(ProductoDTO productoDTO);
	
    String update(ProductoDTO productoDTO);
    
    String delete(Integer id);
}
