package com.mafe.cadastroempresarelacional.infraestructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtUtil jwtUtil) throws Exception {
        http
            .httpBasic(httpbasic -> httpbasic.disable()) /* Desabilita autenticação Basic (usuário:senha no header)*/
            .formLogin(form -> form.disable()) /*desabilita os forms*/
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) /*torna a aplicação stateless, então fazz com q o token tenha q ser passado no Authorization*/
            .addFilterBefore(new JwtFilter(jwtUtil), AnonymousAuthenticationFilter.class)
            .anonymous(anonymous -> anonymous.disable()) /*Isso impede que requisições sem autenticação sejam tratadas como "usuário anônimo", sem token, sem entrada*/
            .csrf(csrf -> csrf.disable()) /*CSRF é usado para proteger formulários, mas como sua API é stateless e usa JWT, não precisa.*/
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("user/auth/**", "/user/admin").permitAll() /*falando rotas q nn precisam de token*/
                    .anyRequest().authenticated()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
