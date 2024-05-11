package tpo.api.ecommerce.service;

import java.util.List;

import tpo.api.ecommerce.domain.ProductDTO;

public interface ProductService {

    List<ProductDTO> getProducts();

    ProductDTO getProductById(Long productId);

    ProductDTO createProduct(ProductDTO dto);

    ProductDTO updateProduct(Long productId, ProductDTO dto);

}
