package com.mafe.cadastroempresarelacional.application;

import com.mafe.cadastroempresarelacional.domain.User;
import com.mafe.cadastroempresarelacional.domain.enums.Role;
import com.mafe.cadastroempresarelacional.infraestructure.execpctions.InvalidAuthorizationException;
import com.mafe.cadastroempresarelacional.infraestructure.repositorys.UserRepository;
import com.mafe.cadastroempresarelacional.infraestructure.security.JwtUtil;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.LoginRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.UserRegisterRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();

        return users;
    }
}
