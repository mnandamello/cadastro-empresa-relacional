package com.mafe.cadastroempresarelacional.infraestructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter { // o extends garante que o filtro será executado apenas uma vez por requisição

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    protected void doFilterInternal(HttpServletRequest request, //contém os dados da requisição (headers, parâmetros, corpo, etc
                                    HttpServletResponse response, //permite manipular a resposta (status, corpo, headers)
                                    FilterChain filterChain) throws SecurityException, IOException{ //permite passar a requisição adiante

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null & !authHeader.isEmpty()){
            String token = authHeader;

            try {

                Jws<Claims> claimsJws = jwtUtil.valideToken(token);
                Claims claims = claimsJws.getBody();

                String email = claims.getSubject();
                String role = claims.get("role", String.class);

                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role)); //transforma a role em uma autoridade que o Spring Security entende
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, authorities); //objeto de autenticação que guarda usuário + permissões.
                SecurityContextHolder.getContext().setAuthentication(auth); //é onde o Spring guarda a autenticação do usuário logado.

            }catch (JwtException e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido ou expirado");
                return;
            }
        }

    }
}
