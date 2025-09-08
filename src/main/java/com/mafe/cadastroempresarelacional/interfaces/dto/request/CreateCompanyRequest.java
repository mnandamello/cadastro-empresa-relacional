package com.mafe.cadastroempresarelacional.interfaces.dto.request;

import com.mafe.cadastroempresarelacional.domain.Adress;
import com.mafe.cadastroempresarelacional.domain.enums.CompanySituation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateCompanyRequest {

    @NotBlank(message = "O campo CNPJ não pode ser nulo")
    private String cnpj;

    @NotBlank(message = "O campo Business Name não pode ser nulo")
    private String businessName;

    @NotBlank(message = "O campo Trade Name Name não pode ser nulo")
    private String tradeName;

    @NotBlank(message = "O campo Phone não pode ser nulo")
    private String phone;

    @NotBlank(message = "O campo Contact Email não pode ser nulo")
    private String contactEmail;

    @NotNull(message = "O campo Foundation Data não pode ser nulo")
    private LocalDate foundationData;

    @NotNull(message = "O campo Company Situation não pode ser nulo")
    private String companySituation;

    @NotEmpty(message = "É necessário ter no mínimo 1 endereço")
    @Valid
    private List<Adress> adresses;

}
