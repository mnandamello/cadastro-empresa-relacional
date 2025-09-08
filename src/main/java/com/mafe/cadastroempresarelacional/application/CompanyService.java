package com.mafe.cadastroempresarelacional.application;

import com.mafe.cadastroempresarelacional.domain.Adress;
import com.mafe.cadastroempresarelacional.domain.Company;
import com.mafe.cadastroempresarelacional.domain.enums.CompanySituation;
import com.mafe.cadastroempresarelacional.infraestructure.excepctions.InvalidCompanyException;
import com.mafe.cadastroempresarelacional.infraestructure.repositorys.CompanyRepository;
import com.mafe.cadastroempresarelacional.interfaces.dto.mapper.CompanyMapper;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.CreateCompanyRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.response.ApiResponse;
import com.mafe.cadastroempresarelacional.interfaces.dto.response.CompanyResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public ApiResponse createCompany(CreateCompanyRequest request){
        Optional<Company> optionalCompany = companyRepository.findByCnpj(request.getCnpj());


        if (optionalCompany.isPresent()){
            throw new InvalidCompanyException("Empresa já cadastrada com esse cnpj");
        }

        CompanySituation companySituation;
        try {
            companySituation = CompanySituation.valueOf(request.getCompanySituation().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCompanyException("A situação da empresa deve ser ACTIVE ou INACTIVE");
        }

        Company newCompany = new Company();
        newCompany.setCnpj(request.getCnpj());
        newCompany.setBusinessName(request.getBusinessName());
        newCompany.setTradeName(request.getTradeName());
        newCompany.setPhone(request.getPhone());
        newCompany.setContactEmail(request.getContactEmail());
        newCompany.setFoundationData(request.getFoundationData());
        newCompany.setCompanySituation(companySituation);

        List<Adress> adresses = request.getAdresses().stream().map(addReq -> {
            Adress adress = new Adress();
            adress.setStreet(addReq.getStreet());
            adress.setNumber(addReq.getNumber());
            adress.setComplement(addReq.getComplement());
            adress.setNeighborhood(addReq.getNeighborhood());
            adress.setCity(addReq.getCity());
            adress.setState(addReq.getState());
            adress.setPostalCode(addReq.getPostalCode());

            adress.setCompany(newCompany);
            return adress;
        }).toList();

        newCompany.setAdresses(adresses);

        companyRepository.save(newCompany);

        return new ApiResponse(201, "Empresa cadastrada com sucesso");
    }

    public CompanyResponse getByCnpj (String cnpj) {
        Company optionalCompany = companyRepository.findByCnpj(cnpj).orElseThrow(() -> new InvalidCompanyException("Empresa não encontrada"));

        return CompanyMapper.toDTO(optionalCompany);
    }
}
