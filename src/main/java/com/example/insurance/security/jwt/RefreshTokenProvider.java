package com.example.insurance.security.jwt;

import com.example.insurance.data.entities.RefreshToken;
import com.example.insurance.data.entities.UserEntity;
import com.example.insurance.data.repositories.RefreshTokenRepository;
import com.example.insurance.data.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.insurance.security.jwt.SecurityConstants.*;

/**
 * The type Refresh token provider.
 */
@Component
@AllArgsConstructor
public class RefreshTokenProvider {

    private UserRepository userRepository;

    private RefreshTokenRepository refreshTokenRepository;

    private JWTTokenProvider jwtTokenProvider;

    /**
     * Create refresh token.
     *
     * @param email the email
     * @return the refresh token
     */
    @Transactional
    public RefreshToken createRefreshToken(String email) {
        UserEntity userEntity = userRepository.getByEmail(email);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userEntity);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpirationDate(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME));
        refreshTokenRepository.deleteAllByUser(userEntity);
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    /**
     * Verify token expiration date refresh token.
     *
     * @param refreshToken the refresh token
     * @return the refresh token
     */
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

    /**
     * Update tokens string.
     *
     * @param request  the request
     * @param response the response
     * @return the string
     */
    @Transactional
    public String updateTokens(HttpServletRequest request, HttpServletResponse response) {
        String oldAccessToken = request.getHeader(HEADER_ACCESS_TOKEN);
        String oldRefreshToken = request.getHeader(HEADER_REFRESH_TOKEN);
        if (oldAccessToken != null && oldRefreshToken != null) {
            RefreshToken refreshToken = refreshTokenRepository.findByToken(oldRefreshToken);
            if (refreshToken == null) {
                String email = jwtTokenProvider.decodeJWT(oldAccessToken);
                UserEntity userEntity = userRepository.getByEmail(email);
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
            response.addHeader("Access-Control-Expose-Headers", HEADER_ACCESS_TOKEN + "," + HEADER_REFRESH_TOKEN);
            return token;
        }
        return null;
    }
}
