package com.example.insurance.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.insurance.Data.Entities.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

import static com.example.insurance.Security.SecurityConstants.*;

@Component
public class JWTTokenProvider {

    public static String createJWTToken(Authentication authResult) {
        String username = ((User) authResult.getPrincipal()).getUsername();
        Collection<GrantedAuthority> authorities = ((User) authResult.getPrincipal()).getAuthorities();
        String userRole;
        if (authorities.contains(Role.ADMIN))
            userRole = Role.ADMIN.getAuthority();
        else
            userRole = Role.USER.getAuthority();
        String token = JWT.create()
                .withSubject(username)
                .withClaim("role", userRole)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
        return token;
    }

    public static String getUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
    }

    public static String getRoleFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getClaim("role").asString();
    }
}
