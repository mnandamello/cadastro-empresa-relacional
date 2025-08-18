package com.mafe.cadastroempresarelacional.application;

import com.mafe.cadastroempresarelacional.domain.User;
import com.mafe.cadastroempresarelacional.domain.enums.Role;
import com.mafe.cadastroempresarelacional.infraestructure.repositorys.UserRepository;
import com.mafe.cadastroempresarelacional.infraestructure.security.JwtUtil;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.LoginRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.UserRegisterRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.response.LoginResponse;
import com.mafe.cadastroempresarelacional.interfaces.dto.response.UserRegisterResponse;
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


    public UserRegisterResponse createUser(UserRegisterRequest data, Role role){
        Optional<User> user = userRepository.findByEmail(data.getEmail());

        if (!user.isEmpty()){
            return new UserRegisterResponse(400, "Usuário já cadastrado");
        }

        User newUser = new User();
        newUser.setName(data.getName());
        newUser.setEmail(data.getEmail());
        newUser.setRole(role);
        newUser.setPasswordHash(passwordEncoder.encode(data.getPassword()));

        newUser = userRepository.save(newUser);

        //boolean matches = passwordEncoder.matches(rawPassword, encodedPassword); -> pra verificar se bate com a senha no login

        return new UserRegisterResponse(201, "Usuário cadastrado com sucesso");
    }

    public LoginResponse auth(LoginRequest request){

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (!optionalUser.isPresent()){
            return new Exception("oui");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())){
            return new Exception("oui");
        }

        String role = user.getRole().name();
        String token = jwtUtil.generateToken(user.getEmail(), role);

        return new LoginResponse(token);


    }

    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();

        return users;
    }
}
