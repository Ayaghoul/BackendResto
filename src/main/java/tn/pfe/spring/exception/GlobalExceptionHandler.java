package tn.pfe.spring.exception;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleTokenExpiredException(TokenExpiredException ex) {
        // Create a custom error response object with the necessary information
        ErrorResponse errorResponse = new ErrorResponse("Token expired", ex.getMessage());

        // Return the error response with an appropriate HTTP status code
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}