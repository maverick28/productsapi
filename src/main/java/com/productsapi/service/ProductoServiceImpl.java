package com.productsapi.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.productsapi.dto.ProductoDTO;
import com.productsapi.model.Producto;
import com.productsapi.repositories.ProductoRepository;

@Service("ProductoService")
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	ProductoRepository productoRepository;

	@Override
	public Optional<Producto> get(Integer id) {
		if(id==null || id<=0)
			return Optional.empty();
		else
			return productoRepository.findById(id.longValue());
	}

	@Override
	public List<Producto> getAllPaged(int page, int size) {
		return productoRepository.findAll(PageRequest.of(page, size)).getContent();
	}

	@Override
	public String create(ProductoDTO productoDTO) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Producto productoEntity = modelMapper.map(productoDTO, Producto.class);
		if (productoDTO.getNombre() == null || productoDTO.getNombre().isBlank())
			return "No se puede guardar un producto sin nombre";
		try {
			productoRepository.save(productoEntity);
		} catch (DataIntegrityViolationException e) {
			if (e.getMessage().contains("Duplicate entry"))
				return "No se puede guardar mas de un producto con el mismo nombre";
			else
				return "ERROR desconocido";
		}
		return "Producto " + productoEntity.getNombre() + " creado";
	}

	@Override
	public String update(ProductoDTO productoDTO) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Producto productoEntity = modelMapper.map(productoDTO, Producto.class);
		if (productoDTO.getNombre() == null || productoDTO.getNombre().isBlank())
			return "No se puede guardar un producto sin nombre";
		try {
			if(!productoRepository.existsById(productoEntity.getId().longValue()))
				return "El producto no existe";
			else
				productoRepository.save(productoEntity);
		} catch (DataIntegrityViolationException e) {
			if (e.getMessage().contains("Duplicate entry"))
				return "No se puede guardar mas de un producto con el mismo nombre";
			else
				return "ERROR desconocido";
		}
		return "Producto " + productoEntity.getNombre() + " actualizado";
	}

	@Override
	public String delete(Integer id) {
		if(!productoRepository.existsById(id.longValue()))
			return "El producto no existe";
		else
			productoRepository.deleteById(id.longValue());
		return "Producto ID" + id + " eliminado";
	}

};