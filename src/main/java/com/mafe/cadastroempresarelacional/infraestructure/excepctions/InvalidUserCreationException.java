package com.mafe.cadastroempresarelacional.infraestructure.excepctions;

public class InvalidUserCreationException extends RuntimeException {
    public InvalidUserCreationException(String message) {
        super(message);
    }
}