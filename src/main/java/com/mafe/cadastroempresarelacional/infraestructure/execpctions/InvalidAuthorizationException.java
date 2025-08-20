package com.mafe.cadastroempresarelacional.infraestructure.execpctions;

public class InvalidAuthorizationException extends  RuntimeException{
    public InvalidAuthorizationException(String message){
        super(message);
    }
}
