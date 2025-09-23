package com.mafe.cadastroempresarelacional.application;

import com.mafe.cadastroempresarelacional.domain.User;
import com.mafe.cadastroempresarelacional.domain.enums.Role;
import com.mafe.cadastroempresarelacional.infraestructure.excepctions.InvalidAuthorizationException;
import com.mafe.cadastroempresarelacional.infraestructure.excepctions.InvalidPasswordException;
import com.mafe.cadastroempresarelacional.infraestructure.excepctions.InvalidUserCreationException;
import com.mafe.cadastroempresarelacional.infraestructure.repositorys.UserRepository;
import com.mafe.cadastroempresarelacional.infraestructure.security.JwtUtil;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.ChangePasswordRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.LoginRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.request.UserRegisterRequest;
import com.mafe.cadastroempresarelacional.interfaces.dto.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }


    public ApiResponse createUser(UserRegisterRequest data, Role role){
        logger.info("US-C-00 - Iniciando processo de criação de usuário no banco - Email: {}", data.getEmail());
        Optional<User> user = userRepository.findByEmail(data.getEmail());

        if (user.isPresent()){
            logger.warn("US-C-WARN-00 - Tentativa de criar usuário com email já existente: {}", data.getEmail());
            return new ApiResponse(400, "Usuário já cadastrado");
        }

        try {
            User newUser = new User();
            newUser.setName(data.getName());
            newUser.setEmail(data.getEmail());
            newUser.setRole(role);
            newUser.setPasswordHash(passwordEncoder.encode(data.getPassword()));

            userRepository.save(newUser);
        }catch (Exception e){
            logger.error("US-C-ERR-00 - Erro inespeado ao criar usuário - Email: {} - Erro: {}", data.getEmail(), e.getMessage());
            throw new InvalidUserCreationException("Erro ao criar usuário");
        }

        logger.info("US-C-01 - Usuário criando com sucesso - Email: {}", data.getEmail());
        return new ApiResponse(201, "Usuário cadastrado com sucesso");
    }

    public String auth(LoginRequest request){
        logger.info("US-A-00 - Iniciando processo de autenticação");

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isEmpty()){
            logger.warn("US-A-WARN-00 - Tentativa de autenticar um usuário inexistente - Email: {}", request.getEmail());
            throw  new InvalidAuthorizationException("Usuário não encontrado");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())){
            logger.warn("US-A-WARN-00 - Tentativa de autenticar um usuário com a senha inválida - Senha: {}", request.getPassword());
            throw  new InvalidAuthorizationException("Senha inválida");
        }

        String role = user.getRole().name();
        String token = jwtUtil.generateToken(user.getEmail(), role);

        logger.info("US-A-01 - Autenticação feita com sucesso - Token: {}", token);
        return token;

    }

    public void changePassword(ChangePasswordRequest request){
        logger.info("US-CP-00 - Iniciando processo de alteração de senha no banco");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = optionalUser.get();

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPasswordHash())){
            logger.warn("US-CP-WARN-00 - Tentativa de cadastrar uma nova senha mandando senha atual incorreta - Senha Antiga: {}", request.getOldPassword());
            throw new InvalidPasswordException("Sua senha atual enviada não confere com a senha salva no momento");
        }

        if (request.getOldPassword().equals(request.getNewPassword())){
            logger.warn("US-CP-WARN-01 - Tentativa de cadastrar uma nova senha mandando senhas semelhantes - Senha Antiga: {} e senha nova: {}", request.getOldPassword(), request.getNewPassword());
            throw new InvalidPasswordException("As 2 senhas devem ser diferentes");
        }

        try {
            user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
            logger.info("US-CP-01 - Senha alterada com sucesso");
        }catch (Exception e){
            logger.error("US-CP-ERR-00 - Erro inesperado ao alterar senha - Erro: {}", e.getMessage());
            throw new InvalidPasswordException("Erro ao alterar senha");
        }

    }

    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();

        return users;
    }
}
