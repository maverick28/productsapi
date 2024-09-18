package com.productsapi.service;

import java.util.List;

import com.productsapi.dto.ProductoDTO;

public interface ProductoService {
	
	ProductoDTO get(Integer id);
	    
	List<ProductoDTO> getAllPaged(int page, int size);
	
	ProductoDTO create(ProductoDTO productoDTO);
	
	ProductoDTO update(ProductoDTO productoDTO);
    
    void delete(Integer id);
}
