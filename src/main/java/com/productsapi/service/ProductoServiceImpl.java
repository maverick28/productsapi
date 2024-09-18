package com.productsapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.productsapi.dto.ProductoDTO;
import com.productsapi.exception.DuplicateProductNameException;
import com.productsapi.exception.NoProductNameException;
import com.productsapi.exception.NotFoundProductException;
import com.productsapi.model.Producto;
import com.productsapi.repositories.ProductoRepository;

@Service("ProductoService")
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	ProductoRepository productoRepository;

	@Override
	public ProductoDTO get(Integer id) throws NotFoundProductException {
		if (id == null || id <= 0)
			throw new NotFoundProductException("El producto no existe");
		else
			return new ProductoDTO(productoRepository.findById(id.longValue()));
	}

	@Override
	public List<ProductoDTO> getAllPaged(int page, int size) {
		List<Producto> listaProducto = productoRepository.findAll(PageRequest.of(page, size)).getContent();
		return listaProducto.stream().map(p -> new ProductoDTO(p)).collect(Collectors.toList());
	}

	@Override
	public ProductoDTO create(ProductoDTO productoDTO) throws NoProductNameException, DuplicateProductNameException {
		if (productoDTO.getNombre() == null || productoDTO.getNombre().isBlank())
			throw new NoProductNameException("No se puede guardar un producto sin nombre");
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Producto productoEntity = modelMapper.map(productoDTO, Producto.class);
		ProductoDTO returnProdDTO = new ProductoDTO();
		try {
			returnProdDTO = new ProductoDTO(productoRepository.save(productoEntity));
		} catch (DataIntegrityViolationException e) {
			if (e.getMessage().contains("Duplicate entry"))
				throw new DuplicateProductNameException("No se puede guardar mas de un producto con el mismo nombre");
		}
		return returnProdDTO;
	}

	@Override
	public ProductoDTO update(ProductoDTO productoDTO)
			throws NotFoundProductException, NoProductNameException, DuplicateProductNameException {
		if (productoDTO.getNombre() == null || productoDTO.getNombre().isBlank())
			throw new NoProductNameException("No se puede guardar un producto sin nombre");
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Producto productoEntity = modelMapper.map(productoDTO, Producto.class);
		ProductoDTO returnProdDTO = new ProductoDTO();
		try {
			if (!productoRepository.existsById(productoEntity.getId().longValue()))
				throw new NotFoundProductException("El producto no existe");
			else
				returnProdDTO = new ProductoDTO(productoRepository.save(productoEntity));
		} catch (DataIntegrityViolationException e) {
			if (e.getMessage().contains("Duplicate entry"))
				throw new DuplicateProductNameException("No se puede guardar mas de un producto con el mismo nombre");
		}
		return returnProdDTO;
	}

	@Override
	public void delete(Integer id) throws NotFoundProductException {
		if (!productoRepository.existsById(id.longValue()))
			throw new NotFoundProductException("El producto con ID " + id + " no existe");
		else
			productoRepository.deleteById(id.longValue());
	}

}
