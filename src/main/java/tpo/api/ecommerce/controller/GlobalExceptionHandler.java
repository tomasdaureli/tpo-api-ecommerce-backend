package tpo.api.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tpo.api.ecommerce.error.BuyAlreadyProcessedException;
import tpo.api.ecommerce.error.BuyNotFoundException;
import tpo.api.ecommerce.error.DiscountCodeAlreadyExistsException;
import tpo.api.ecommerce.error.DiscountExpiredException;
import tpo.api.ecommerce.error.DiscountNotFoundException;
import tpo.api.ecommerce.error.InvalidCredentialsException;
import tpo.api.ecommerce.error.InvalidPermissionException;
import tpo.api.ecommerce.error.ProductNotFoundException;
import tpo.api.ecommerce.error.ProductWithoutStockException;
import tpo.api.ecommerce.error.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getClass().getSimpleName(), ex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return new ErrorResponse(HttpStatus.UNAUTHORIZED, ex.getClass().getSimpleName(), ex.getMessage());
    }

    @ExceptionHandler(InvalidPermissionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleInvalidPermissionException(InvalidPermissionException ex) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, ex.getClass().getSimpleName(), ex.getMessage());
    }

    @ExceptionHandler({ BuyAlreadyProcessedException.class, ProductWithoutStockException.class,
            DiscountExpiredException.class, DiscountCodeAlreadyExistsException.class })
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleCustomException(RuntimeException ex) {
        String code = ex.getClass().getSimpleName();
        String errorMessage = ex.getMessage();
        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, code, errorMessage);
    }

    @ExceptionHandler({ BuyNotFoundException.class, ProductNotFoundException.class, UserNotFoundException.class,
            DiscountNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(RuntimeException ex) {
        String code = ex.getClass().getSimpleName();
        String errorMessage = ex.getMessage();
        return new ErrorResponse(HttpStatus.NOT_FOUND, code, errorMessage);
    }

    public static class ErrorResponse {
        private final int status;
        private final String code;
        private final String message;

        public ErrorResponse(HttpStatus status, String code, String message) {
            this.status = status.value();
            this.code = code;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
