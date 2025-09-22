package com.mafe.cadastroempresarelacional.infraestructure.repositorys;

import com.mafe.cadastroempresarelacional.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByCnpj(String cnpj);
}
