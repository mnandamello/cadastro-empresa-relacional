package com.mafe.cadastroempresarelacional.application;

import com.mafe.cadastroempresarelacional.domain.User;
import com.mafe.cadastroempresarelacional.domain.enums.Role;
import com.mafe.cadastroempresarelacional.infraestructure.excepctions.InvalidAuthorizationException;
import com.mafe.cadastroempresarelacional.infraestructure.excepctions.InvalidPasswordException;
import com.mafe.cadastroempresarelacional.infraestructure.repositorys.UserRepository;
import com.mafe.cadastroempresarelacional.infraestructure.security.JwtUtil;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.ChangePasswordRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.LoginRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.UserRegisterRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }


    public ApiResponse createUser(UserRegisterRequest data, Role role){
        Optional<User> user = userRepository.findByEmail(data.getEmail());

        if (!user.isEmpty()){
            return new ApiResponse(400, "Usuário já cadastrado");
        }

        User newUser = new User();
        newUser.setName(data.getName());
        newUser.setEmail(data.getEmail());
        newUser.setRole(role);
        newUser.setPasswordHash(passwordEncoder.encode(data.getPassword()));

        userRepository.save(newUser);

        return new ApiResponse(201, "Usuário cadastrado com sucesso");
    }

    public String auth(LoginRequest request){

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (!optionalUser.isPresent()){
            throw  new InvalidAuthorizationException("Usuário não encontrado");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())){
            throw  new InvalidAuthorizationException("Senha inválida");
        }

        String role = user.getRole().name();
        String token = jwtUtil.generateToken(user.getEmail(), role);

        return token;

    }

    public void changePassword(ChangePasswordRequest request){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = optionalUser.get();

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPasswordHash())){
            throw new InvalidPasswordException("Sua senha atual enviada não confere com a senha salva no momento");
        }

        if (request.getOldPassword().equals(request.getNewPassword())){
            throw new InvalidPasswordException("As 2 senhas devem ser diferentes");
        }

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

    }

    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();

        return users;
    }
}
