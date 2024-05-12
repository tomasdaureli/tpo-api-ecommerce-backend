package tpo.api.ecommerce.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tpo.api.ecommerce.domain.CategoryProductDTO;
import tpo.api.ecommerce.domain.ProductDTO;
import tpo.api.ecommerce.domain.SubcategoryProductDTO;
import tpo.api.ecommerce.entity.CategoryProduct;
import tpo.api.ecommerce.entity.Product;
import tpo.api.ecommerce.entity.SubcategoryProduct;
import tpo.api.ecommerce.error.ProductNotFoundException;
import tpo.api.ecommerce.mapper.ProductMapper;
import tpo.api.ecommerce.repository.ProductRepository;
import tpo.api.ecommerce.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository repository;

	private final ProductMapper mapper;

	@Override
	public List<ProductDTO> getProducts(String category, String subcategory) {

		if (subcategory != null) {
			return getProductsBySubcategory(
					SubcategoryProduct.valueOf(subcategory));
		}

		if (category != null) {
			return getProductsByCategory(
					CategoryProduct.valueOf(category));
		}

		return repository.findAll().stream()
				.map(mapper::toProductDTO).toList();
	}

	private List<ProductDTO> getProductsBySubcategory(SubcategoryProduct subcategory) {
		return repository.findBySubcategory(subcategory).stream()
				.map(mapper::toProductDTO).toList();
	}

	private List<ProductDTO> getProductsByCategory(CategoryProduct category) {
		return repository.findByCategory(category).stream()
				.map(mapper::toProductDTO).toList();
	}

	@Override
	public ProductDTO getProductById(Long productId) {
		Product product = repository.findById(productId)
				.orElseThrow(ProductNotFoundException::new);
		return mapper.toProductDTO(product);
	}

	@Override
	public ProductDTO createProduct(ProductDTO dto) {
		return mapper.toProductDTO(
				repository.save(mapper.toProduct(dto)));
	}

	@Override
	public ProductDTO updateProduct(Long productId, ProductDTO dto) {
		Product product = repository.findById(productId)
				.orElseThrow(ProductNotFoundException::new);
		return mapper.toProductDTO(
				repository.save(mapper.toUpdateProduct(dto, product)));
	}

	@Override
	public List<CategoryProductDTO> getCategories() {
		return Arrays.asList(CategoryProduct.values()).stream()
				.map(mapper::toCategoryProductDTO).toList();
	}

	@Override
	public List<SubcategoryProductDTO> getSubcategories() {
		return Arrays.asList(SubcategoryProduct.values()).stream()
				.map(mapper::toSubcategoryProductDTO).toList();
	}

}
