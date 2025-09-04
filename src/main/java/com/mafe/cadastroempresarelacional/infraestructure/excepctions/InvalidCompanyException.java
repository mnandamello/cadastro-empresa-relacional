package com.mafe.cadastroempresarelacional.infraestructure.excepctions;

public class InvalidCompanyException extends RuntimeException{
    public InvalidCompanyException(String message){
        super(message);
    }
}
