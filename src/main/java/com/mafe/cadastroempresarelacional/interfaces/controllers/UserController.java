package com.mafe.cadastroempresarelacional.interfaces.controllers;

import com.mafe.cadastroempresarelacional.application.UserService;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.UserRegisterRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.response.UserRegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userservice;

    @Autowired
    public UserController(UserService userService){
        this.userservice = userService;
    }

    @PostMapping
    private ResponseEntity<?> createUser(@RequestBody UserRegisterRequest request){
        UserRegisterResponse response = userservice.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
