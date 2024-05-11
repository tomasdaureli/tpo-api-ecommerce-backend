package tpo.api.ecommerce.utils;

import java.math.BigDecimal;

import tpo.api.ecommerce.domain.ProductDTO;

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

}
