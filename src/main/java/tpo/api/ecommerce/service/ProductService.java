package tpo.api.ecommerce.service;

import java.util.List;

import tpo.api.ecommerce.domain.CategoryProductResponseDTO;
import tpo.api.ecommerce.domain.ProductDTO;
import tpo.api.ecommerce.domain.SubcategoryProductResponseDTO;

public interface ProductService {

    List<ProductDTO> getProducts(String category, String subcategory, String productName, Boolean sortPriceAsc);

    ProductDTO getProductById(Long productId);

    ProductDTO createProduct(ProductDTO dto);

    ProductDTO updateProduct(Long productId, ProductDTO dto);

    List<CategoryProductResponseDTO> getCategories();

    List<SubcategoryProductResponseDTO> getSubcategories();

    void deleteProduct(Long productId);

    void deactivateProduct(Long productId);

    List<ProductDTO> getProductsBySeller(Long sellerId, String category, String subcategory, String productName,
            Boolean actives);

}
