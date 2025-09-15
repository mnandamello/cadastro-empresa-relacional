package com.mafe.cadastroempresarelacional.interfaces.dto.request;

public record ChangeAdressRequest(
        String street,
        Integer number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String postalCode
) {}