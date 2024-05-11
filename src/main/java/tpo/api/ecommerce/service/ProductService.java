package tpo.api.ecommerce.service;

import java.util.List;

import tpo.api.ecommerce.domain.ProductDTO;
import tpo.api.ecommerce.entity.Product;

public interface ProductService {

    List<ProductDTO> getProducts();

}
