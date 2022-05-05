package com.example.insurance.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The type Password encoder bean.
 */
@Configuration
public class PasswordEncoderBean {

    /**
     * Bcrypt password encoder
     * @see BCryptPasswordEncoder
     * @return the bcrypt password encoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
