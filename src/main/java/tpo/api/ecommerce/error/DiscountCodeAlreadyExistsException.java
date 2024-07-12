package tpo.api.ecommerce.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties({ "trace", "cause" })
public class DiscountCodeAlreadyExistsException extends RuntimeException {

    public DiscountCodeAlreadyExistsException(String code) {
        super(String.format("Ya existe un cupon de descuento con el codigo %s", code));
    }

}
