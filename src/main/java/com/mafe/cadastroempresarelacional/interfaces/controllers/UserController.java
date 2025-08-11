package com.mafe.cadastroempresarelacional.interfaces.controllers;

import com.mafe.cadastroempresarelacional.application.UserService;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.LoginRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.UserRegisterRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.response.UserRegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userservice;

    @Autowired
    public UserController(UserService userService){
        this.userservice = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRegisterRequest request){
        UserRegisterResponse response = userservice.createUser(request);

        if (response.getStatusCode() != 200){
            throw new IllegalArgumentException("Usuário já cadastrado");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("oI");
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello User!";
    }
}
