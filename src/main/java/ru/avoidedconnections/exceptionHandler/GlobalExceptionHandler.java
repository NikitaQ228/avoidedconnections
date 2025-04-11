package ru.avoidedconnections.exceptionHandler;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.avoidedconnections.dto.ErrorResponse;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ObjectNotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse("NOT_FOUND", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Authentication Failed " + ex.getMessage());
    }

    @ExceptionHandler(javax.naming.AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleGenericException(AuthenticationException ex) {
        ErrorResponse errorResponse = new ErrorResponse("UNAUTHORIZED", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

//    @ExceptionHandler(IOException.class)
//    public ResponseEntity<ErrorResponse> handleGenericException(IOException ex) {
//        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}