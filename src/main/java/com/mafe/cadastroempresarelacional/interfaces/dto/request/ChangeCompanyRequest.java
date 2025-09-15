package com.mafe.cadastroempresarelacional.interfaces.dto.request;

import java.time.LocalDate;
import java.util.List;

public record ChangeCompanyRequest (
        String cnpj,
        String businessName,
        String tradeName,
        String phone,
        String contactEmail,
        LocalDate foundationData,
        String companySituation,
        List<ChangeAdressRequest> adresses
) {}
