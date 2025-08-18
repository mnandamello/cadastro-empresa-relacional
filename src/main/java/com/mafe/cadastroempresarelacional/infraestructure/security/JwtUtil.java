package com.mafe.cadastroempresarelacional.infraestructure.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private final Key key;

    public JwtUtil() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512); /*lembrar de salvar no env.*/
    }

    public String generateToken(String email, String role){
        long expirationTime = 1000 * 60 * 60 * 10; // =10hrs

        return Jwts.builder()
                .setSubject(email)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }

    public Jws<Claims> valideToken(String token) throws JwtException{
        try {
            return Jwts.parserBuilder()//cria um validador de tokens
                    .setSigningKey(key) //informa a chave secreta usada para verificar a assinatura do token.
                    .build()//finaliza a construção do parser
                    .parseClaimsJws(token); //Claims é basicamente um Map<String, Object> com os dados (as informações que você armazenou no JWT)
        }catch (JwtException e){
            throw new JwtException("Invalid or expired token");
        }
    }
}
