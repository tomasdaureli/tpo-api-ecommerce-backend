package tpo.api.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tpo.api.ecommerce.entity.ItemProduct;
import tpo.api.ecommerce.entity.ItemProductId;
import tpo.api.ecommerce.entity.Product;

public interface ItemProductRepository extends JpaRepository<ItemProduct, ItemProductId> {

    Integer countByProduct(Product product);

}
