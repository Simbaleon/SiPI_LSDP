package com.example.insurance.security;

import com.example.insurance.data.repositories.UserRepository;
import com.example.insurance.security.filters.JWTAuthenticationFilter;
import com.example.insurance.security.filters.JWTAuthorizationFilter;
import com.example.insurance.security.jwt.JWTTokenProvider;
import com.example.insurance.security.jwt.RefreshTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The type Web security.
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Value("${cross.origin.front.host:http://localhost:3000}")
    private String crossOriginFrontHost;

    private final UserRepository userRepository;

    private final UserDetailsService userDetailsService;

    /**
     * The Bcrypt password encoder.
     */
    public final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JWTTokenProvider jwtTokenProvider;

    private final RefreshTokenProvider refreshTokenProvider;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/clients/registration").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtTokenProvider, refreshTokenProvider, userRepository))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtTokenProvider, refreshTokenProvider))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(crossOriginFrontHost)
                .allowedMethods("*");
    }

}
