package tpo.api.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tpo.api.ecommerce.entity.CategoryProduct;
import tpo.api.ecommerce.entity.Product;
import tpo.api.ecommerce.entity.SubcategoryProduct;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByStockGreaterThan(Integer quantity);

    List<Product> findByCategoryAndStockGreaterThan(CategoryProduct category, Integer quantity);

    List<Product> findBySubcategoryAndStockGreaterThan(SubcategoryProduct subcategory, Integer quantity);

}
