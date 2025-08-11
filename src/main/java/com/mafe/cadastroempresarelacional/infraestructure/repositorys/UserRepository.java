package com.mafe.cadastroempresarelacional.infraestructure.repositorys;

import com.mafe.cadastroempresarelacional.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
}
