package tpo.api.ecommerce.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import tpo.api.ecommerce.entity.Product;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super(Product.class.getCanonicalName());
    }

}
