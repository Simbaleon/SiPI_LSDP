package com.example.insurance.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.insurance.Data.Entities.RefreshToken;
import com.example.insurance.Data.Entities.UserEntity;
import com.example.insurance.Data.Repositories.RefreshTokenRepository;
import com.example.insurance.Data.Repositories.UserRepository;
import com.example.insurance.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.insurance.Security.SecurityConstants.*;

@Component
@AllArgsConstructor
public class RefreshTokenProvider {

    private UserRepository userRepository;

    private RefreshTokenRepository refreshTokenRepository;

    private JWTTokenProvider jwtTokenProvider;

    public RefreshToken createRefreshToken(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userEntity);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpirationDate(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME));
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyTokenExpirationDate(RefreshToken refreshToken) {
        Date now = new Date();
        if (now.after(refreshToken.getExpirationDate())) {
            refreshTokenRepository.delete(refreshToken);
            return null;
        }
        refreshToken.setExpirationDate(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Transactional
    public void updateTokens(HttpServletRequest request, HttpServletResponse response) {
        String oldAccessToken = request.getHeader(HEADER_ACCESS_TOKEN);
        String oldRefreshToken = request.getHeader(HEADER_REFRESH_TOKEN);
        if (oldAccessToken != null && oldRefreshToken != null) {
            RefreshToken refreshToken = refreshTokenRepository.findByToken(oldRefreshToken);
            if (refreshToken == null) {
                String email = jwtTokenProvider.decodeJWT(oldAccessToken);
                UserEntity userEntity = userRepository.findByEmail(email);
                if (userEntity != null)
                    refreshTokenRepository.deleteAllByUser(userEntity);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no such token in the database");
            }
            refreshToken = verifyTokenExpirationDate(refreshToken);
            if (refreshToken == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Refresh token was expired");
            UserEntity userEntity = refreshToken.getUser();
            List<GrantedAuthority> authorities = userEntity.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                    .collect(Collectors.toList());
            String token = jwtTokenProvider.createJWTToken(userEntity.getEmail(), authorities);
            response.addHeader(HEADER_ACCESS_TOKEN, TOKEN_PREFIX + token);
            response.addHeader(HEADER_REFRESH_TOKEN, refreshToken.getToken());
        }
    }
}
