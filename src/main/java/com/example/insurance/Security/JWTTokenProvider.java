package com.example.insurance.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.insurance.Data.Entities.Role;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.example.insurance.Security.SecurityConstants.*;

@Component
public class JWTTokenProvider {

    public String createJWTToken(String username, Collection<GrantedAuthority> authorities) {
        String userRole;
        if (authorities.contains(Role.ADMIN))
            userRole = Role.ADMIN.getAuthority();
        else
            userRole = Role.USER.getAuthority();
        String token = JWT.create()
                .withSubject(username)
                .withClaim("role", userRole)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
        return token;
    }

    public String getUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
    }

    public String getRoleFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getClaim("role").asString();
    }

    public String decodeJWT(String jwt){
        Map<String, String> userData = new HashMap<>();
        String[] chunks = jwt.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String json = new String(decoder.decode(chunks[1]));
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getString("sub");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
