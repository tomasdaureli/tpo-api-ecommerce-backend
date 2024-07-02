package tpo.api.ecommerce.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import tpo.api.ecommerce.entity.Discount;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DiscountNotFoundException extends RuntimeException {

    public DiscountNotFoundException() {
        super(Discount.class.getCanonicalName());
    }

}
