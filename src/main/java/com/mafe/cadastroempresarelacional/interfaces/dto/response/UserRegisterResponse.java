package com.mafe.cadastroempresarelacional.interfaces.dto.response;

import lombok.Data;

@Data
public class UserRegisterResponse {
    private int statusCode;
    private String message;

    public UserRegisterResponse(int statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }
}
