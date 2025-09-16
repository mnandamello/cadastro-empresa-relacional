package com.mafe.cadastroempresarelacional.infraestructure.repositorys;

import com.mafe.cadastroempresarelacional.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {
    Optional<Company> findByCnpj(String cnpj);
    Page<Company> findAll(Specification<Company> spec, Pageable pageable);
}
