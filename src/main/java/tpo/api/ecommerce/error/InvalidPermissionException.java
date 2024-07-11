package tpo.api.ecommerce.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@ResponseStatus(HttpStatus.FORBIDDEN)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties({ "trace", "cause" })
public class InvalidPermissionException extends RuntimeException {

    public InvalidPermissionException() {
        super("No tienes permisos para realizar esta accion.");
    }

}
