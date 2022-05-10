package com.example.insurance.security.filters;

import com.example.insurance.data.entities.UserEntity;
import com.example.insurance.data.repositories.UserRepository;
import com.example.insurance.exceptions.AuthenticationFailedException;
import com.example.insurance.security.jwt.JWTTokenProvider;
import com.example.insurance.security.jwt.RefreshTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.example.insurance.security.jwt.SecurityConstants.*;

/**
 * The type Jwt authentication filter.
 */
@AllArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private JWTTokenProvider jwtTokenProvider;

    private RefreshTokenProvider refreshTokenProvider;

    private UserRepository userRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            UserEntity user = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
            return authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getRoles())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String username = ((User) authResult.getPrincipal()).getUsername(); //в качестве username используется адрес эл.почты
        Collection<GrantedAuthority> authorities = ((User) authResult.getPrincipal()).getAuthorities();
        String accessToken = jwtTokenProvider.createJWTToken(username, authorities);
        String refreshToken = refreshTokenProvider.createRefreshToken(username).getToken();
        response.addHeader(HEADER_ACCESS_TOKEN, TOKEN_PREFIX + accessToken);
        response.addHeader(HEADER_REFRESH_TOKEN, refreshToken);
        response.addHeader("Access-Control-Expose-Headers", HEADER_ACCESS_TOKEN + "," + HEADER_REFRESH_TOKEN);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> body = new HashMap<>();
        body.put("role", ((User) authResult.getPrincipal()).getAuthorities());
        body.put("username", username);
        try {
            objectMapper.writeValue(response.getOutputStream(), body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        super.unsuccessfulAuthentication(request, response, failed);
        throw new AuthenticationFailedException();
    }
}
