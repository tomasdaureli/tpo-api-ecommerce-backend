package tpo.api.ecommerce.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import tpo.api.ecommerce.domain.CategoryProductDTO;
import tpo.api.ecommerce.domain.ProductDTO;
import tpo.api.ecommerce.domain.SubcategoryProductDTO;
import tpo.api.ecommerce.entity.CategoryProduct;
import tpo.api.ecommerce.entity.Product;
import tpo.api.ecommerce.entity.SubcategoryProduct;
import tpo.api.ecommerce.entity.User;
import tpo.api.ecommerce.error.ProductNotFoundException;
import tpo.api.ecommerce.mapper.ProductMapper;
import tpo.api.ecommerce.mapper.UserMapper;
import tpo.api.ecommerce.repository.ItemProductRepository;
import tpo.api.ecommerce.repository.ProductRepository;
import tpo.api.ecommerce.service.ProductService;
import tpo.api.ecommerce.service.UserService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository repository;

	private final ProductMapper mapper;

	private final UserService userService;

	private final UserMapper userMapper;

	private final ItemProductRepository itemsRepository;

	@Override
	public List<ProductDTO> getProducts(String category, String subcategory, String productName) {

		if (subcategory != null) {
			return getProductsBySubcategory(
					SubcategoryProduct.valueOf(subcategory));
		}

		if (category != null) {
			return getProductsByCategory(
					CategoryProduct.valueOf(category));
		}

		if (productName != null) {
			return repository.findByProductNameContainingIgnoreCaseAndStockGreaterThan(productName, 0)
					.stream()
					.map(this::convertProductResponse)
					.toList();
		}

		return repository.findByStockGreaterThan(0).stream()
				.map(this::convertProductResponse)
				.toList();
	}

	private List<ProductDTO> getProductsByCategory(CategoryProduct category) {
		return repository.findByCategoryAndStockGreaterThan(category, 0).stream()
				.map(this::convertProductResponse)
				.toList();
	}

	private List<ProductDTO> getProductsBySubcategory(SubcategoryProduct subcategory) {
		return repository.findBySubcategoryAndStockGreaterThan(subcategory, 0).stream()
				.map(this::convertProductResponse)
				.toList();
	}

	@Override
	public ProductDTO getProductById(Long productId) {
		Product product = repository.findById(productId)
				.orElseThrow(ProductNotFoundException::new);
		return convertProductResponse(product);
	}

	@Transactional(rollbackFor = Throwable.class)
	@Override
	public ProductDTO createProduct(ProductDTO dto) {
		Product product = mapper.toProduct(dto);
		product.setSeller(userMapper.toUser(userService.getAuthenticatedUser()));

		return mapper.toProductDTO(
				repository.save(product));
	}

	@Transactional(rollbackFor = Throwable.class)
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

	@Transactional(rollbackFor = Throwable.class)
	@Override
	public void deleteProduct(Long productId) {
		Product product = repository.findById(productId)
				.orElseThrow(ProductNotFoundException::new);
		repository.delete(product);
	}

	@Override
	public List<ProductDTO> getProductsBySeller(Long sellerId) {
		User seller = userMapper.toUser(
				userService.getUserById(sellerId));

		return repository.findBySeller(seller).stream()
				.map(this::convertProductResponse)
				.toList();
	}

	private ProductDTO convertProductResponse(Product product) {
		ProductDTO dto = mapper.toProductDTO(product);
		dto.setSold(getSoldQuantity(product));
		return dto;
	}

	private Integer getSoldQuantity(Product product) {
		return itemsRepository.countByProduct(product);
	}

}
