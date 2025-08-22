package com.mafe.cadastroempresarelacional.infraestructure.excepctions;

public class InvalidAuthorizationException extends  RuntimeException{
    public InvalidAuthorizationException(String message){
        super(message);
    }
}
