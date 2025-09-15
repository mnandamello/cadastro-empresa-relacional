package com.mafe.cadastroempresarelacional.infraestructure.repositorys;

import com.mafe.cadastroempresarelacional.domain.Adress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdressRepository extends JpaRepository<Adress, Long> {
}
