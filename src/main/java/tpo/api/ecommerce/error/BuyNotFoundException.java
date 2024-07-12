package tpo.api.ecommerce.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import tpo.api.ecommerce.entity.Buy;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BuyNotFoundException extends RuntimeException {

    public BuyNotFoundException() {
        super(String.format("No se ha encontrado la compra solicitada"));
    }

}
