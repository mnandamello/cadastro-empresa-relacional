package com.mafe.cadastroempresarelacional.interfaces.controllers;

import com.mafe.cadastroempresarelacional.application.CompanyService;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.CreateCompanyRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
