package com.productsapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.productsapi.dto.ProductoDTO;
import com.productsapi.exception.DuplicateProductNameException;
import com.productsapi.exception.NoProductNameException;
import com.productsapi.exception.NotFoundProductException;
import com.productsapi.model.Producto;
import com.productsapi.repositories.ProductoRepository;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

	@Mock
	private ProductoRepository productoRepository;

	@InjectMocks
	private ProductoServiceImpl productoService;

	@Test
	void testGet_Success() throws NotFoundProductException {
		Producto producto = new Producto();
		producto.setId(1);
		ProductoDTO productoDTO = new ProductoDTO(producto);
		when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

		ProductoDTO result = productoService.get(1);

		assertEquals(productoDTO.getId(), result.getId());
		verify(productoRepository).findById(1L);
	}

	@Test
	void testGet_NotFound() {
		assertThrows(NotFoundProductException.class, () -> productoService.get(0));
		assertThrows(NotFoundProductException.class, () -> productoService.get(null));
	}

	@Test
	void testGetAllPaged() {
		Producto producto = new Producto();
		producto.setId(1);
		when(productoRepository.findAll(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(Arrays.asList(producto)));

		List<ProductoDTO> result = productoService.getAllPaged(0, 10);

		assertEquals(1, result.size());
		verify(productoRepository).findAll(PageRequest.of(0, 10));
	}

	@Test
	void testCreate_Success() throws NoProductNameException, DuplicateProductNameException {
		ProductoDTO productoDTO = new ProductoDTO();
		productoDTO.setNombre("Product A");
		Producto producto = new Producto();
		when(productoRepository.save(any(Producto.class))).thenReturn(producto);

		ProductoDTO result = productoService.create(productoDTO);

		assertNotNull(result);
		verify(productoRepository).save(any(Producto.class));
	}

	@Test
	void testCreate_NoProductName() {
		ProductoDTO productoDTO = new ProductoDTO();
		assertThrows(NoProductNameException.class, () -> productoService.create(productoDTO));
	}

	@Test
	void testUpdate_Success() throws NotFoundProductException, NoProductNameException, DuplicateProductNameException {
		ProductoDTO productoDTO = new ProductoDTO();
		productoDTO.setId(1);
		productoDTO.setNombre("Updated Product");
		Producto producto = new Producto();
		when(productoRepository.existsById(1L)).thenReturn(true);
		when(productoRepository.save(any(Producto.class))).thenReturn(producto);

		ProductoDTO result = productoService.update(productoDTO);

		assertNotNull(result);
		verify(productoRepository).save(any(Producto.class));
	}

	@Test
	void testUpdate_NotFound() {
		ProductoDTO productoDTO = new ProductoDTO();
		productoDTO.setId(0);
		byte[] array = new byte[7];
	    new Random().nextBytes(array);
	    String randomNombre = new String(array, Charset.forName("UTF-8"));
		productoDTO.setNombre(randomNombre);
		assertThrows(NotFoundProductException.class, () -> productoService.update(productoDTO));
	}

	@Test
	void testDelete_Success() throws NotFoundProductException {
		when(productoRepository.existsById(1L)).thenReturn(true);
		productoService.delete(1);
		verify(productoRepository).deleteById(1L);
	}

	@Test
	void testDelete_NotFound() {
		assertThrows(NotFoundProductException.class, () -> productoService.delete(1));
	}
}
