package tpo.api.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tpo.api.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
