package com.mafe.cadastroempresarelacional.infraestructure.repositorys;

import com.mafe.cadastroempresarelacional.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
