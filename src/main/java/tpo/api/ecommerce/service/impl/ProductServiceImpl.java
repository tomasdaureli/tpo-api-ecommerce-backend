package tpo.api.ecommerce.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tpo.api.ecommerce.domain.ProductDTO;
import tpo.api.ecommerce.mapper.ProductMapper;
import tpo.api.ecommerce.repository.ProductRepository;
import tpo.api.ecommerce.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository repository;

	private final ProductMapper mapper;

	@Override
	public List<ProductDTO> getProducts() {
		return repository.findAll().stream()
				.map(mapper::toProductDTO).toList();
	}

	@Override
	public ProductDTO createProduct(ProductDTO dto) {
		return mapper.toProductDTO(
				repository.save(mapper.toProduct(dto)));
	}

}
