package com.example.insurance.security.filters;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.insurance.data.entities.UserEntity;
import com.example.insurance.security.jwt.JWTTokenProvider;
import com.example.insurance.security.jwt.RefreshTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.insurance.security.jwt.SecurityConstants.*;

/**
 * The type Jwt authorization filter.
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JWTTokenProvider jwtTokenProvider;

    private RefreshTokenProvider refreshTokenProvider;

    /**
     * Instantiates a new Jwt authorization filter.
     *
     * @param authenticationManager the authentication manager
     * @param jwtTokenProvider      the jwt token provider
     * @param refreshTokenProvider  the refresh token provider
     */
    @Autowired
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JWTTokenProvider jwtTokenProvider,
                                  RefreshTokenProvider refreshTokenProvider) {
         super(authenticationManager);
         this.jwtTokenProvider = jwtTokenProvider;
         this.refreshTokenProvider = refreshTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String accessToken = request.getHeader(HEADER_ACCESS_TOKEN);
        String refreshToken = request.getHeader(HEADER_REFRESH_TOKEN);
        if ((accessToken == null || !accessToken.startsWith(TOKEN_PREFIX)) || refreshToken == null) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request, response);
        if (authenticationToken == null)
            return;
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = request.getHeader(HEADER_ACCESS_TOKEN);
        String refreshToken = request.getHeader(HEADER_REFRESH_TOKEN);
        if (accessToken != null && refreshToken != null) {
            try {
                UserEntity user = jwtTokenProvider.getUserFromToken(accessToken);
                String role = jwtTokenProvider.getRoleFromToken(accessToken);
                List<GrantedAuthority> roles = new ArrayList<>();
                roles.add(new SimpleGrantedAuthority(role));
                if (user != null)
                    return new UsernamePasswordAuthenticationToken(user, null, roles);
            } catch (JWTDecodeException e) {
                return null;
            }
            catch (TokenExpiredException e) {
                String newAccessToken = refreshTokenProvider.updateTokens(request, response);
                if (newAccessToken != null) {
                    UserEntity user = jwtTokenProvider.getUserFromToken(newAccessToken);
                    String roleFromNewToken = jwtTokenProvider.getRoleFromToken(newAccessToken);
                    List<GrantedAuthority> rolesFromNewToken = new ArrayList<>();
                    rolesFromNewToken.add(new SimpleGrantedAuthority(roleFromNewToken));
                    if (user != null)
                        return new UsernamePasswordAuthenticationToken(user, null, rolesFromNewToken);
                }
            }
        }
        return null;
    }
}
