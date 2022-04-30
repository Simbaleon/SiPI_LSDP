package com.example.insurance.Security;

import com.example.insurance.Data.Repositories.UserRepository;
import com.example.insurance.Security.Filters.JWTAuthenticationFilter;
import com.example.insurance.Security.Filters.JWTAuthorizationFilter;
import com.example.insurance.Security.JWT.JWTTokenProvider;
import com.example.insurance.Security.JWT.RefreshTokenProvider;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Value("${cross.origin.front.host}")
    private final String crossOriginFrontHost;

    private UserRepository userRepository;

    private final UserDetailsService userDetailsService;

    /**
     * The Bcrypt password encoder.
     */
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    private JWTTokenProvider jwtTokenProvider;

    private RefreshTokenProvider refreshTokenProvider;

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
