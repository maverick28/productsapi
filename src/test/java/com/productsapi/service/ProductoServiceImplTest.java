package com.productsapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.productsapi.dto.ProductoDTO;
import com.productsapi.model.Producto;
import com.productsapi.repositories.ProductoRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProductoServiceImplTest {

	@Mock
	private ProductoRepository productoRepository;

	@InjectMocks
	private ProductoServiceImpl productoService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGet_ValidId_ReturnsProduct() {
		Producto producto = new Producto();
		producto.setId(1);
		when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

		Optional<Producto> result = productoService.get(1);

		assertTrue(result.isPresent());
		assertEquals(producto, result.get());
	}

	@Test
	void testGet_InvalidId_ReturnsEmpty() {
		when(productoRepository.findById(anyLong())).thenReturn(Optional.empty());

		Optional<Producto> result = productoService.get(-1);

		assertFalse(result.isPresent());
	}

	@Test
	void testGetAllPaged_ReturnsProductList() {
		List<Producto> listProductos = new ArrayList<>();
		listProductos.add(Mockito.mock(Producto.class));
		listProductos.add(Mockito.mock(Producto.class));
		listProductos.add(Mockito.mock(Producto.class));
		listProductos.add(Mockito.mock(Producto.class));
		listProductos.add(Mockito.mock(Producto.class));
		
		Page<Producto> pageProductos = new PageImpl<>(listProductos);

		when(productoRepository.findAll(PageRequest.of(0, 10)))
		.thenReturn(pageProductos);

		List<Producto> result = productoService.getAllPaged(0, 10);

		assertEquals(5, result.size());
	}

	@Test
	void testCreate_ValidProductoDTO_ReturnsSuccessMessage() {
		ProductoDTO productoDTO = new ProductoDTO();
		productoDTO.setNombre("Product");
		Producto producto = new Producto();

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		when(productoRepository.save(any(Producto.class))).thenReturn(producto);
		productoService = new ProductoServiceImpl();
		productoService.productoRepository = productoRepository;

		String result = productoService.create(productoDTO);

		assertEquals("Producto Product creado", result);
	}

	@Test
	void testCreate_InvalidProductoDTO_ReturnsErrorMessage() {
		ProductoDTO productoDTO = new ProductoDTO();
		productoDTO.setNombre(""); // Invalid name

		String result = productoService.create(productoDTO);

		assertEquals("No se puede guardar un producto sin nombre", result);
	}

	@Test
	void testUpdate_ExistingProduct_ReturnsSuccessMessage() {
		ProductoDTO productoDTO = new ProductoDTO();
		productoDTO.setId(1);
		productoDTO.setNombre("UpdatedProduct");
		Producto producto = new Producto();
		producto.setId(1);

		when(productoRepository.existsById(1L)).thenReturn(true);
		when(productoRepository.save(any(Producto.class))).thenReturn(producto);

		String result = productoService.update(productoDTO);

		assertEquals("Producto UpdatedProduct actualizado", result);
	}

	@Test
	void testUpdate_NonExistingProduct_ReturnsErrorMessage() {
		ProductoDTO productoDTO = new ProductoDTO();
		productoDTO.setId(1);
		productoDTO.setNombre("UpdatedProduct");

		when(productoRepository.existsById(1L)).thenReturn(false);

		String result = productoService.update(productoDTO);

		assertEquals("El producto no existe", result);
	}

	@Test
	void testDelete_ExistingProduct_ReturnsSuccessMessage() {
		when(productoRepository.existsById(1L)).thenReturn(true);

		String result = productoService.delete(1);

		assertEquals("Producto ID1 eliminado", result);
	}

	@Test
	void testDelete_NonExistingProduct_ReturnsErrorMessage() {
		when(productoRepository.existsById(1L)).thenReturn(false);

		String result = productoService.delete(1);

		assertEquals("El producto no existe", result);
	}
}
