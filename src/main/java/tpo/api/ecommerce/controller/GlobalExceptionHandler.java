package tpo.api.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tpo.api.ecommerce.error.BuyAlreadyProcessedException;
import tpo.api.ecommerce.error.BuyNotFoundException;
import tpo.api.ecommerce.error.InvalidCredentialsException;
import tpo.api.ecommerce.error.ProductNotFoundException;
import tpo.api.ecommerce.error.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(BuyAlreadyProcessedException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleBuyAlreadyProcessedException(BuyAlreadyProcessedException ex) {
        String errorMessage = ex.getClass().getSimpleName();
        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, errorMessage);
    }

    @ExceptionHandler({ BuyNotFoundException.class, ProductNotFoundException.class, UserNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(RuntimeException ex) {
        String errorMessage = ex.getClass().getSimpleName();
        return new ErrorResponse(HttpStatus.NOT_FOUND, errorMessage);
    }

    public static class ErrorResponse {
        private final int status;
        private final String message;

        public ErrorResponse(HttpStatus status, String message) {
            this.status = status.value();
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }
}
