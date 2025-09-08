package com.mafe.cadastroempresarelacional.interfaces.dto.response;

import java.time.LocalDate;
import java.util.List;

public record CompanyResponse (
        Long id,
        String cnpj,
        String businessName,
        String tradeName,
        String phone,
        String contactEmail,
        LocalDate foundationData,
        String companySituation,
        List<AddresResponse> adresses
){}
