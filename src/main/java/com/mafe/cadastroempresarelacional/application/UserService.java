package com.mafe.cadastroempresarelacional.application;

import com.mafe.cadastroempresarelacional.domain.User;
import com.mafe.cadastroempresarelacional.infraestructure.UserRepository;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.UserRegisterRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.response.UserRegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public UserRegisterResponse createUser(UserRegisterRequest data){
        List<User> user = userRepository.findByEmail(data.getEmail());

        if (!user.isEmpty()){
            return new UserRegisterResponse(500, "Usuário já cadastrado");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User newUser = new User();
        newUser.setName(data.getName());
        newUser.setEmail(data.getEmail());
        newUser.setPasswordHash(passwordEncoder.encode(data.getPassword()));

        newUser = userRepository.save(newUser);

        //boolean matches = passwordEncoder.matches(rawPassword, encodedPassword); -> pra verificar se bate com a senha no login

        return new UserRegisterResponse(200, "Usuário cadastrado com sucesso");
    }
}
