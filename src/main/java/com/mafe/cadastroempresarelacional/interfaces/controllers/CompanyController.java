package com.mafe.cadastroempresarelacional.interfaces.controllers;

import com.mafe.cadastroempresarelacional.application.CompanyService;
import com.mafe.cadastroempresarelacional.domain.Company;
import com.mafe.cadastroempresarelacional.domain.User;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.CreateCompanyRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.response.ApiResponse;
import com.mafe.cadastroempresarelacional.interfaces.dto.response.CompanyResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/api/v1/companies")
    public ResponseEntity<?> createCompany(@Valid @RequestBody CreateCompanyRequest request){
        ApiResponse apiResponse = companyService.createCompany(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'BASIC')")
    @GetMapping("/api/v1/companies/{cnpj}")
    public ResponseEntity<CompanyResponse> getCompanyByCnpj(@PathVariable String cnpj){
        CompanyResponse company = companyService.getByCnpj(cnpj);

        return ResponseEntity.status(HttpStatus.OK).body(company);
    }
}
