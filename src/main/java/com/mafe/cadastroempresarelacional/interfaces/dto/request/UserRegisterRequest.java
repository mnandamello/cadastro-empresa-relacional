package com.mafe.cadastroempresarelacional.interfaces.dto.request;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String name;
    private String email;
    private String password;
}
