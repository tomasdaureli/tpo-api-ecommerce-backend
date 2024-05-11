package tpo.api.ecommerce.utils;

import java.math.BigDecimal;

import tpo.api.ecommerce.domain.ProductDTO;
import tpo.api.ecommerce.entity.Product;

public class DummyDataUtils {

    public static ProductDTO buildProductDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setProductName("Air Jordan 1 Low");
        productDTO.setPrice(new BigDecimal(150));
        productDTO.setUrlImage("urlImage");
        productDTO.setQuantity(1);
        productDTO.setDescription("Descripcion Air Jordan 1.");
        return productDTO;
    }

    public static Product buildProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setProductName("Air Jordan 1 Low");
        product.setPrice(new BigDecimal(150));
        product.setUrlImage("urlImage");
        product.setQuantity(1);
        product.setDescription("Descripcion Air Jordan 1.");
        return product;
    }

}
