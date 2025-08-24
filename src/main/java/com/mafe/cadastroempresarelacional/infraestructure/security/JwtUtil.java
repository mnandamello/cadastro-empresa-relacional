package com.mafe.cadastroempresarelacional.infraestructure.security;

import com.mafe.cadastroempresarelacional.infraestructure.excepctions.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key;
    private static final String SECRET_KEY = "sua-chave-super-secreta-muito-longa-com-minimo-64-caracteres-1234567890-abcdef"; /*lembrar de salvar no env.*/

    public JwtUtil() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email, String role){
        long expirationTime = 1000 * 60 * 60 * 10; // =10hrs

        return Jwts.builder()
                .setSubject(email)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Jws<Claims> valideToken(String token) throws JwtException{
        try {
            return Jwts.parserBuilder()//cria um validador de tokens
                    .setSigningKey(key) //informa a chave secreta usada para verificar a assinatura do token.
                    .build()//finaliza a construção do parser
                    .parseClaimsJws(token); //Claims é basicamente um Map<String, Object> com os dados (as informações que você armazenou no JWT)
        }catch (JwtException e){
            throw new InvalidTokenException("Invalid or expired token");
        }
    }
}
