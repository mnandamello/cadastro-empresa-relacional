package com.mafe.cadastroempresarelacional.infraestructure.excepctions;

import com.mafe.cadastroempresarelacional.interfaces.dto.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidAuthorizationException.class)
    public ResponseEntity<ApiResponse>handleInvalidAuthorizationException(InvalidAuthorizationException ex){
        logger.error("Erro de autorização: {}", ex.getMessage(), ex);
        ApiResponse response = new ApiResponse(401, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidUserCreationException.class)
    public ResponseEntity<ApiResponse>handleInvalidUserCreationException(InvalidAuthorizationException ex){
        logger.error("Erro ao criar usuário {}", ex.getMessage(), ex);
        ApiResponse response = new ApiResponse(409, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponse>handleInvalidTokenException(InvalidTokenException ex){
        logger.error("Token Inválido: {}", ex.getMessage(), ex);
        ApiResponse response = new ApiResponse(401, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse> handleInvalidPasswordException(InvalidPasswordException ex) {
        logger.error("Senha inválida: {}", ex.getMessage(), ex);
        ApiResponse response = new ApiResponse(404, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCompanyException.class)
    public ResponseEntity<String>handlerInvalidCompanyException(InvalidCompanyException ex){
        logger.warn("Exceção de negócio: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        logger.error("Erro inesperado: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + ex.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        logger.error("Erro de validação: {}", ex.getMessage());
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(Map.of("errors", errors));
    }

}
