package com.mafe.cadastroempresarelacional.interfaces.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyFilter {
    private String companySituation;
    private String city;

    private String sortBy = "businessName";
    private String sortDirection = "asc";
    private Integer page = 0;
    private Integer size = 10;
}
