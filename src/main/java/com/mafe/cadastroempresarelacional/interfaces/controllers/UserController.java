package com.mafe.cadastroempresarelacional.interfaces.controllers;

import com.mafe.cadastroempresarelacional.application.UserService;
import com.mafe.cadastroempresarelacional.domain.User;
import com.mafe.cadastroempresarelacional.domain.enums.Role;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.LoginRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.UserRegisterRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.response.UserRegisterResponse;
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

    @Autowired
    public UserController(UserService userService){
        this.userservice = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/basic")
    public ResponseEntity<?> createUserBasic(@RequestBody UserRegisterRequest request){
        UserRegisterResponse response = userservice.createUser(request, Role.BASIC);

        if (response.getStatusCode() != 201){
            throw new IllegalArgumentException("Usuário já cadastrado");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/admin")
        public ResponseEntity<?> createUserAdmin(@RequestBody UserRegisterRequest request){
        UserRegisterResponse response = userservice.createUser(request, Role.ADMIN);

        if (response.getStatusCode() != 200){
            throw new IllegalArgumentException("Usuário já cadastrado");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        if (request.getEmail() == null || request.getEmail().isEmpty()){
            throw new IllegalArgumentException();
        }


    }

    @PreAuthorize("hasRole('BASIC')")
    @GetMapping("/allUsers")
    public List<User> getAllUsers() {
        return userservice.getAllUsers();
    }
}
