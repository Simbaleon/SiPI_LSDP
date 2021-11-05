package com.example.insurance.Security.Filters;

import com.example.insurance.Data.Entities.UserEntity;
import com.example.insurance.Exceptions.AuthenticationFailedException;
import com.example.insurance.Security.JWTTokenProvider;
import com.example.insurance.Security.RefreshTokenProvider;
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

import static com.example.insurance.Security.SecurityConstants.*;

@AllArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private JWTTokenProvider jwtTokenProvider;

    private RefreshTokenProvider refreshTokenProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            UserEntity user = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), user.getRoles()
                    )
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
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        super.unsuccessfulAuthentication(request, response, failed);
        throw new AuthenticationFailedException();
    }
}
