package com.mafe.cadastroempresarelacional.infraestructure.execpctions;

import com.mafe.cadastroempresarelacional.interfaces.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidAuthorizationException.class)
    public ResponseEntity<ApiResponse>handleInvalidAuthorizationException(InvalidAuthorizationException ex){
        ApiResponse response = new ApiResponse(401, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
