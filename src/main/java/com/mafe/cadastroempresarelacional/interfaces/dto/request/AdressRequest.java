package com.mafe.cadastroempresarelacional.interfaces.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdressRequest {

    @NotBlank(message = "O campo Street não pode ser vazio")
    private String street;

    @NotNull(message = "O campo number não pode ser vazio")
    private Integer number;

    private String complement;

    @NotBlank(message = "O campo neighborhood não pode ser vazio")
    private String neighborhood;

    @NotBlank(message = "O campo city não pode ser vazio")
    private String city;

    @NotBlank(message = "O campo state não pode ser vazio")
    private String state;

    @NotBlank(message = "O campo postalCode não pode ser vazio")
    private String postalCode;
}
