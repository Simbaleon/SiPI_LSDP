package com.example.insurance.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.insurance.data.entities.Role;
import com.example.insurance.data.entities.UserEntity;
import com.example.insurance.data.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.example.insurance.security.jwt.SecurityConstants.*;

/**
 * The type Jwt token provider.
 */
@Component
@AllArgsConstructor
public class JWTTokenProvider {

    private final UserRepository userRepository;

    /**
     * Create jwt token string.
     *
     * @param username    the username
     * @param authorities the authorities
     * @return the string
     */
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

    /**
     * Gets username from token.
     *
     * @param token the token
     * @return the username from token
     */
    public UserEntity getUserFromToken(String token) {
        String userName = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
        return userRepository.getByEmail(userName);
    }

    /**
     * Gets role from token.
     *
     * @param token the token
     * @return the role from token
     */
    public String getRoleFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getClaim("role").asString();
    }

    /**
     * Decode jwt string.
     *
     * @param jwt the jwt
     * @return the string
     */
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
