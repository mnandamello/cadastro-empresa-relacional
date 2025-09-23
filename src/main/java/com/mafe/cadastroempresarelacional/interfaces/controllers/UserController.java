package com.mafe.cadastroempresarelacional.interfaces.controllers;

import com.mafe.cadastroempresarelacional.application.UserService;
import com.mafe.cadastroempresarelacional.domain.User;
import com.mafe.cadastroempresarelacional.domain.enums.Role;
import com.mafe.cadastroempresarelacional.infraestructure.excepctions.InvalidAuthorizationException;
import com.mafe.cadastroempresarelacional.infraestructure.excepctions.InvalidPasswordException;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.ChangePasswordRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.LoginRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.UserRegisterRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userservice;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserController(UserService userService){
        this.userservice = userService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/basic")
    public ResponseEntity<?> createUserBasic(@RequestBody UserRegisterRequest request){
        logger.info("UC-C-01 - Requisição recebida para criar usuário básico - Email: {}", request.getEmail());
        ApiResponse response = userservice.createUser(request, Role.BASIC);
        logger.info("UC-C-02 - Usuário básico criado com sucesso - Email: {}", request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/admin")
        public ResponseEntity<?> createUserAdmin(@RequestBody UserRegisterRequest request){
        logger.info("UC-C-AD-01 - Requisição recebida para criar usuário Admin - Email: {}", request.getEmail());
        ApiResponse response = userservice.createUser(request, Role.ADMIN);
        logger.info("UC-C-AD-02 -  Usuário básico criado com sucesso - Email: {}", request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        logger.info("UC-A-01 - Requisição recebida para autenticar uauário - Email: {}", request.getEmail());
        if (request.getEmail() == null || request.getEmail().isEmpty()){
            throw new InvalidAuthorizationException("Email é obrigatório");
        }
        String token = userservice.auth(request);
        logger.info("UC-A-02 - Usuário autenticado com sucesso - Email: {}", request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(201, token));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'BASIC')")
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request){
        logger.info("UC-CP-01 - Requisição recebida para troca de senha");
        if (request.getOldPassword() == null || request.getOldPassword().isEmpty()){
            throw new InvalidPasswordException("É obrigatório passar a senha atual para que a mudança seja feita!");
        }

        if (request.getNewPassword() == null || request.getNewPassword().isEmpty()){
            throw new InvalidPasswordException("É obrigatório passar a nova senha para que a mudança seja feita!");
        }

        userservice.changePassword(request);
        logger.info("UC-CP-02 - Senha alterada com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Senha alterada com sucesso"));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'BASIC')")
    @GetMapping("/allUsers")
    public List<User> getAllUsers() {
        return userservice.getAllUsers();
    }
}
