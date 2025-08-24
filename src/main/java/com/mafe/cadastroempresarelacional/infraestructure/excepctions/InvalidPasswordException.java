package com.mafe.cadastroempresarelacional.infraestructure.excepctions;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String message){
        super(message);
    }
}
