package com.mafe.cadastroempresarelacional.infraestructure.excepctions;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String message){
        super(message);
    }
}
