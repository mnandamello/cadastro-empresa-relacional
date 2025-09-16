package com.mafe.cadastroempresarelacional.application;

import com.mafe.cadastroempresarelacional.domain.Adress;
import com.mafe.cadastroempresarelacional.domain.Company;
import com.mafe.cadastroempresarelacional.domain.enums.CompanySituation;
import com.mafe.cadastroempresarelacional.infraestructure.excepctions.InvalidCompanyException;
import com.mafe.cadastroempresarelacional.infraestructure.repositorys.CompanyRepository;
import com.mafe.cadastroempresarelacional.infraestructure.specifications.CompanySpecification;
import com.mafe.cadastroempresarelacional.interfaces.dto.mapper.CompanyMapper;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.ChangeCompanyRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.CompanyFilter;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.CreateCompanyRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.response.ApiResponse;
import com.mafe.cadastroempresarelacional.interfaces.dto.response.CompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void alterCompanyInfos(Long id, ChangeCompanyRequest request) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new InvalidCompanyException("Empresa não encontrada"));

        if (!company.getCnpj().equals(request.cnpj())){
            throw new InvalidCompanyException("O CNPJ não pode ser alterado!!");
        }

        CompanySituation companySituation;
        try {
            companySituation = CompanySituation.valueOf(request.companySituation().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidCompanyException("A situação da empresa deve ser ACTIVE ou INACTIVE");
        } //em uma refatoração quero criar uma annotation de validação especifica para esse caso, pois esta sendo repetida em 2 metodos

            company.setCnpj(request.cnpj());
            company.setBusinessName(request.businessName());
            company.setTradeName(request.tradeName());
            company.setPhone(request.phone());
            company.setContactEmail(request.contactEmail());
            company.setFoundationData(request.foundationData());
            company.setCompanySituation(companySituation);
            company.getAdresses().clear();

            List<Adress> existingAdresses = company.getAdresses();
            existingAdresses.clear();

            request.adresses().forEach(addReq -> {
                        Adress adress = new Adress();
                        adress.setStreet(addReq.street());
                        adress.setNumber(addReq.number());
                        adress.setComplement(addReq.complement());
                        adress.setNeighborhood(addReq.neighborhood());
                        adress.setCity(addReq.city());
                        adress.setState(addReq.state());
                        adress.setPostalCode(addReq.postalCode());
                        adress.setCompany(company);
                        existingAdresses.add(adress);
            });

            companyRepository.save(company);
    }

    public void deleteCompany(String cnpj){
        Company company = companyRepository.findByCnpj(cnpj).orElseThrow(() -> new InvalidCompanyException("Empresa não encontrada"));

        companyRepository.delete(company);
    }

    public Page<CompanyResponse> getAllCompanies (CompanyFilter filter, Pageable pageable) {
        Specification<Company> spec = CompanySpecification.withFilters(filter);

        Pageable sortedPageable = getSortedPageable(pageable, filter);

        Page<Company> companiesPage = companyRepository.findAll(spec, sortedPageable);

        return companiesPage.map(CompanyMapper::toDTO);
    }

    private Pageable getSortedPageable(Pageable pageable, CompanyFilter filter) {
        Sort sort = Sort.by(
                filter.getSortDirection().equalsIgnoreCase("desc") ?
                        Sort.Direction.DESC : Sort.Direction.ASC,
                filter.getSortBy()
        );
        return PageRequest.of(
                filter.getPage() != null ? filter.getPage() : pageable.getPageNumber(),
                filter.getSize() != null ? filter.getSize() : pageable.getPageSize(),
                sort
        );
    }
}
