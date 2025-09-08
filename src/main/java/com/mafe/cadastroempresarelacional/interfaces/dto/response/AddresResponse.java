package com.mafe.cadastroempresarelacional.interfaces.dto.response;

public record AddresResponse (
    String street,
    Integer number,
    String complement,
    String neighborhood,
    String city,
    String state,
    String postalCode
    ){}
