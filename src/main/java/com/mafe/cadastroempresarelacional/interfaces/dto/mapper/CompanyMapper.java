package com.mafe.cadastroempresarelacional.interfaces.dto.mapper;

import com.mafe.cadastroempresarelacional.domain.Company;
import com.mafe.cadastroempresarelacional.interfaces.dto.response.AddresResponse;
import com.mafe.cadastroempresarelacional.interfaces.dto.response.CompanyResponse;

import java.util.List;

public class CompanyMapper {

    public static CompanyResponse toDTO(Company company){
        List<AddresResponse> addressDTOs = company.getAdresses().stream().map( adress -> new AddresResponse(
                adress.getStreet(),
                adress.getNumber(),
                adress.getComplement(),
                adress.getNeighborhood(),
                adress.getCity(),
                adress.getState(),
                adress.getPostalCode()
        )).toList();

        return new CompanyResponse(
                company.getId(),
                company.getCnpj(),
                company.getBusinessName(),
                company.getTradeName(),
                company.getPhone(),
                company.getContactEmail(),
                company.getFoundationData(),
                company.getCompanySituation().name(),
                addressDTOs
        );
    }
}
