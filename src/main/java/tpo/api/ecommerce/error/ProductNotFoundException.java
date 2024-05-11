package tpo.api.ecommerce.error;

import jakarta.persistence.EntityNotFoundException;
import tpo.api.ecommerce.entity.Product;

@SuppressWarnings("serial")
public class ProductNotFoundException extends EntityNotFoundException {

    public ProductNotFoundException() {
        super(Product.class.getSimpleName());
    }

}
