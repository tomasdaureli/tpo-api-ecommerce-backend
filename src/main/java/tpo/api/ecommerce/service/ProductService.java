package tpo.api.ecommerce.service;

import java.util.List;

import tpo.api.ecommerce.domain.CategoryProductDTO;
import tpo.api.ecommerce.domain.ProductDTO;
import tpo.api.ecommerce.domain.SubcategoryProductDTO;

public interface ProductService {

    List<ProductDTO> getProducts(String category, String subcategory);

    ProductDTO getProductById(Long productId);

    ProductDTO createProduct(ProductDTO dto);

    ProductDTO updateProduct(Long productId, ProductDTO dto);

    List<CategoryProductDTO> getCategories();

    List<SubcategoryProductDTO> getSubcategories();

}
