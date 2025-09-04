package com.mafe.cadastroempresarelacional.infraestructure.excepctions;

import com.mafe.cadastroempresarelacional.interfaces.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidAuthorizationException.class)
    public ResponseEntity<ApiResponse>handleInvalidAuthorizationException(InvalidAuthorizationException ex){
        ApiResponse response = new ApiResponse(401, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponse>handleInvalidTokenException(InvalidTokenException ex){
        ApiResponse response = new ApiResponse(401, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse>handleInvalidTokenException(InvalidPasswordException ex){
        ApiResponse response = new ApiResponse(404, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCompanyException.class)
    public ResponseEntity<String>handlerInvalidCompanyException(InvalidCompanyException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + ex.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(Map.of("errors", errors));
    }

}
